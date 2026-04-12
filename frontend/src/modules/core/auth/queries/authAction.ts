import { authApi } from '@/modules/core/auth/api/authApi';

export const login = async (data: { username: string; password: string }) => {
  const res = await authApi.login(data);
  return res.data;
};

export const logout = async () => {
  try {
    const res = await authApi.logout();
    return res.data;
  } catch (error) {
    console.log(error);
  }
};

export const getUserInfo = async () => {
  const res = await authApi.getUserInfo();
  return res.data;
};

export const refreshToken = async () => {
  const res = await authApi.refreshToken();
  return res.data;
};

export const updatePassword = async (data: {
  currentPassword: string;
  newPassword: string;
}) => {
  const res = await authApi.updatePassword(data);
  return res.data;
};

export const activateAccount = async (newPassword: string) => {
  const res = await authApi.activateAccount(newPassword);
  return res.data;
};
