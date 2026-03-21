import { ADMIN_ENDPOINT } from '@/constants/api-endpoint';
import axiosClient from '@/lib/axiosClient';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';
import type { UserDTO } from '@/validations/userSchema';

export const adminApi = {
  getAllUsers: (payload: FilterWithPaginationInput) => {
    return axiosClient.post(`${ADMIN_ENDPOINT}/get-users`, payload);
  },

  addUser: (userData: UserDTO) => {
    return axiosClient.post(`${ADMIN_ENDPOINT}/add`, userData);
  },

  getUserById: (userId: string) => {
    return axiosClient.get(`${ADMIN_ENDPOINT}/user/${userId}`);
  },

  searchUser: (keyword: string) => {
    return axiosClient.get(`${ADMIN_ENDPOINT}/search/${keyword}`);
  },
};
