import {
  addUser,
  createNewProfile,
  getAllUsers,
  getUserById,
  lockUser,
  resetPassword,
  searchUser,
  updateUser,
} from '@/modules/core/admin/queries/adminAction';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';
import type {
  UserCreateDTO,
  UserManagementDTO,
  WorkProfileCreateType,
} from '@/modules/core/auth/schemas/userSchema';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';

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
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (userData: UserCreateDTO) => {
      const response = await addUser(userData);
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ['admin', 'users'],
      });
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

export const useLockUser = (staffId: string, options = {}) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async ({ locked }: { locked: boolean }) => {
      const response = await lockUser(staffId, locked);
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ['admin', 'user', staffId],
      });
      queryClient.invalidateQueries({
        queryKey: ['admin'],
      });
    },
    onError: (error) => {
      console.error('Error locking/unlocking user:', error);
      throw error; // Rethrow the error to be handled by the caller
    },
    ...options,
  });
};

export const useUpdateUser = (userId: string, options = {}) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (userData: UserManagementDTO) => {
      const response = await updateUser(userId, userData);
      return response;
    },
    ...options,
    onSuccess: (data) => {
      queryClient.setQueryData(['admin', 'user', userId], data);
      queryClient.invalidateQueries({
        queryKey: ['admin'],
      });
    },
    onError: (error) => {
      console.error('Error updating user:', error);
      throw error; // Rethrow the error to be handled by the caller
    },
  });
};

export const useResetPassword = (userId: string, options = {}) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async () => {
      const response = await resetPassword(userId);
      return response;
    },
    ...options,
    onError: (error) => {
      console.error('Error resetting password:', error);
      throw error; // Rethrow the error to be handled by the caller
    },
    onSuccess: (data) => {
      queryClient.invalidateQueries({
        queryKey: ['admin'],
      });
      queryClient.setQueryData(['admin', 'user', userId], (oldData: any) => {
        if (!oldData) return data;
        return data;
      });
    },
  });
};

export const useCreateNewProfile = (options = {}) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (data: WorkProfileCreateType) => {
      const response = await createNewProfile(data);
      return response;
    },
    ...options,
    onSuccess: (data) => {
      queryClient.invalidateQueries({
        queryKey: ['admin', 'user', data.staffId],
      });
    },
  });
};
