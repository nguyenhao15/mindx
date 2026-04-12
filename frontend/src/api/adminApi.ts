import { ADMIN_ENDPOINT } from '@/constants/api-endpoint';
import axiosClient from '@/lib/axiosClient';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';
import type { UserManagementDTO } from '@/modules/core/auth/schemas/userSchema';

export const adminApi = {
  getAllUsers: (payload: FilterWithPaginationInput) => {
    return axiosClient.post(`${ADMIN_ENDPOINT}/get-users`, payload);
  },

  addUser: (userData: UserManagementDTO) => {
    return axiosClient.post(`${ADMIN_ENDPOINT}/add`, userData);
  },

  getUserById: (userId: string) => {
    return axiosClient.get(`${ADMIN_ENDPOINT}/user/${userId}`);
  },

  searchUser: (keyword: string) => {
    return axiosClient.get(`${ADMIN_ENDPOINT}/search/${keyword}`);
  },

  lockUser: (staffId: string, locked: boolean) => {
    return axiosClient.put(`${ADMIN_ENDPOINT}/lock-user/${staffId}`, locked);
  },

  updateUser: (userId: string, userData: UserManagementDTO) => {
    return axiosClient.put(`${ADMIN_ENDPOINT}/update-user/${userId}`, userData);
  },

  resetPassword: (userId: string) => {
    return axiosClient.put(`${ADMIN_ENDPOINT}/reset-password/${userId}`);
  },
};
