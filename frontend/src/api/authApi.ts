import axiosClient from '@/lib/axiosClient';

export const authApi = {
  login: (data: { username: string; password: string }) => {
    return axiosClient.post('/login', data);
  },

  logout: () => {
    return axiosClient.post('/log-out');
  },

  getUserInfo() {
    return axiosClient.get('/staff-profile');
  },

  refreshToken() {
    return axiosClient.post('/refresh-token');
  },

  updatePassword: (data: { currentPassword: string; newPassword: string }) => {
    return axiosClient.put('/update-password', data);
  },

  activateAccount: (newPassword: string) => {
    return axiosClient.patch('/activate-account', newPassword, {
      headers: { 'Content-Type': 'text/plain' },
    });
  },
};
