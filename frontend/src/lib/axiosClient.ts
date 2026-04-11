import { authApi } from '@/modules/documentation/api/authApi';

import { handleLogout, useAuthStore } from '@/stores/AuthStore';
import axios, { AxiosHeaders } from 'axios';
import type { AxiosError, InternalAxiosRequestConfig } from 'axios';

type QueueItem = {
  resolve: (token: string | null) => void;
  reject: (error: unknown) => void;
};

type RetryRequestConfig = InternalAxiosRequestConfig & {
  _retry?: boolean;
};

const ensureHeaders = (config: InternalAxiosRequestConfig) => {
  if (config.headers instanceof AxiosHeaders) {
    return config.headers;
  }

  config.headers = AxiosHeaders.from(config.headers);
  return config.headers;
};

const axiosClient = axios.create({
  baseURL: `${import.meta.env.VITE_BACK_END_URL}/api`,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
});

axiosClient.interceptors.request.use((config) => {
  const headers = ensureHeaders(config);

  if (!headers.get('Content-Type') && !(config.data instanceof FormData)) {
    headers.set('Content-Type', 'application/json');
  }

  const token = useAuthStore.getState().accessToken;
  if (token) {
    headers.set('Authorization', `Bearer ${token}`);
  }

  return config;
});

let isRefreshing = false; // Flag kiểm soát quá trình refresh
let failedQueue: QueueItem[] = []; // Hàng đợi các request bị 401

const processQueue = (error: unknown, token: string | null = null) => {
  failedQueue.forEach((prom) => {
    if (error) {
      prom.reject(error);
    } else {
      prom.resolve(token);
    }
  });
  failedQueue = [];
};

axiosClient.interceptors.response.use(
  (res) => res,
  async (err: AxiosError) => {
    const originalRequest = err.config as RetryRequestConfig | undefined;

    if (!originalRequest || !originalRequest.url) {
      return Promise.reject(err);
    }

    const originalHeaders = ensureHeaders(originalRequest);

    if (
      originalRequest.url.includes('/login') ||
      originalRequest.url.includes('/log-out') ||
      originalRequest.url.includes('/refresh-token')
    ) {
      return Promise.reject(err);
    }

    if (err.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;

      if (isRefreshing) {
        return new Promise<string | null>(function (resolve, reject) {
          failedQueue.push({ resolve, reject });
        })
          .then((token) => {
            if (token) {
              originalHeaders.set('Authorization', `Bearer ${token}`);
            }
            return axiosClient(originalRequest);
          })
          .catch((err) => {
            return Promise.reject(err);
          });
      }

      isRefreshing = true;
      try {
        const { data } = await authApi.refreshToken();

        useAuthStore.getState().setToken(data);

        isRefreshing = false;
        processQueue(null, data);

        originalHeaders.set('Authorization', `Bearer ${data}`);

        return axiosClient(originalRequest);
      } catch (error) {
        isRefreshing = false;
        handleLogout();
        processQueue(error, null);
        return Promise.reject(error);
      }
    }
    return Promise.reject(err);
  },
);

export default axiosClient;
