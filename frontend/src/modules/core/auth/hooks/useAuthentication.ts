import {
  activateAccount,
  getUserInfo,
  login,
  logout,
} from '@/modules/core/auth/queries/authAction';

import {
  handleLogout,
  useAuthStore,
} from '@/modules/core/auth/store/AuthStore';
import { useMutation, useQuery } from '@tanstack/react-query';
import toast from 'react-hot-toast';
import { useNavigate } from 'react-router-dom';

const USER_QUERY_KEY = ['user'] as const;
const USER_STALE_TIME = 1000 * 60 * 15;

export const useLogin = () => {
  const navigate = useNavigate();
  return useMutation({
    mutationFn: async (data: { username: string; password: string }) => {
      const response = await login(data);
      localStorage.setItem('isLoggedIn', 'true');
      return response;
    },
    onSuccess: (data) => {
      const { userDTO, accessToken } = data;
      useAuthStore.getState().setUser(userDTO);
      if (userDTO.workProfileList.length === 1) {
        localStorage.setItem('profile_id', userDTO.workProfileList[0].id);
        toast.success(
          `Logged in as ${userDTO.workProfileList[0].positionName} profile`,
        );
      }
      useAuthStore.getState().setToken(accessToken);
      navigate('/', { replace: true });
    },
    onError: (error) => {
      console.error('Login failed: ', error);
    },
  });
};

export const useGetUserInfo = () => {
  const cachedUser = useAuthStore.getState().user;
  const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
  return useQuery({
    queryKey: USER_QUERY_KEY,
    queryFn: async () => {
      const data = await getUserInfo();
      useAuthStore.getState().setUser(data);
      return data;
    },
    staleTime: USER_STALE_TIME,
    retry: 1,
    enabled: !cachedUser && isLoggedIn, // Chỉ chạy query nếu chưa có user trong store và đã đăng nhập
    refetchOnWindowFocus: false,
    refetchOnReconnect: false,
    initialData: cachedUser || undefined,
  });
};

export const useLogOut = () => {
  return useMutation({
    mutationFn: async () => {
      try {
        await logout();
      } finally {
        handleLogout();
      }
    },
  });
};

export const useActivateAccount = () => {
  return useMutation({
    mutationFn: async (newPassword: string) => {
      const response = await activateAccount(newPassword);
      return response;
    },
    onSuccess: (data) => {
      useAuthStore.getState().setUser(data);
      localStorage.setItem('isLoggedIn', 'true');
    },
    onError: (error) => {
      throw error; // Let the error be handled by the calling component
    },
  });
};
