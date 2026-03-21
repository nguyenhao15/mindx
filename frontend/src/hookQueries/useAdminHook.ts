import {
  addUser,
  getAllUsers,
  getUserById,
  searchUser,
} from '@/actions/adminAction';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';
import type { UserDTO } from '@/validations/userSchema';
import { useMutation, useQuery } from '@tanstack/react-query';

export const useGetAllUsers = (
  payload: FilterWithPaginationInput,
  options = {},
) => {
  return useQuery({
    queryKey: ['admin', 'users', payload],
    queryFn: async () => {
      const data = await getAllUsers(payload);
      return data;
    },
    ...options,
    staleTime: 1000 * 60 * 5, // 5 minutes
  });
};

export const useGetUserById = (userId: string, options = {}) => {
  return useQuery({
    queryKey: ['admin', 'user', userId],
    queryFn: async () => {
      const data = await getUserById(userId);
      return data;
    },
    ...options,
    enabled: !!userId, // Only run the query if userId is provided
  });
};

export const useAddUser = (options = {}) => {
  return useMutation({
    mutationFn: async (userData: UserDTO) => {
      const response = await addUser(userData);
      return response.data;
    },
    ...options,
  });
};

export const useSearchUser = (keyword: string, options = {}) => {
  return useQuery({
    queryKey: ['admin', 'searchUser', keyword],
    queryFn: async () => {
      const data = await searchUser(keyword);
      return data;
    },
    ...options,
    enabled: !!keyword, // Only run the query if keyword is provided
  });
};
