import { adminApi } from '@/api/adminApi';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';
import {
  UserResponseObject,
  UserResponseSchema,
  type UserManagementDTO,
} from '@/validations/userSchema';

export const getAllUsers = async (payload: FilterWithPaginationInput) => {
  const response = await adminApi.getAllUsers(payload);
  const results = UserResponseSchema.safeParse(response.data.content);
  const finalData = {
    ...response.data,
    content: results.success ? results.data : response.data.content,
  };
  return finalData;
};

export const addUser = async (userData: UserManagementDTO) => {
  const response = await adminApi.addUser(userData);
  const reuslt = UserResponseObject.safeParse(response.data);
  if (!reuslt.success) {
    console.warn('API Data Mismatch:', reuslt.error.format());
    return response.data; // Vẫn trả về data cũ nếu bạn muốn "sống chung với lũ"
  }
  return reuslt.data;
};

export const getUserById = async (userId: string) => {
  const response = await adminApi.getUserById(userId);
  const reuslt = UserResponseObject.safeParse(response.data);
  if (!reuslt.success) {
    console.warn('API Data Mismatch:', reuslt.error.format());
    return response.data; // Vẫn trả về data cũ nếu bạn muốn "sống chung với lũ"
  }
  return reuslt.data;
};

export const searchUser = async (keyword: string) => {
  const response = await adminApi.searchUser(keyword);
  const results = UserResponseSchema.safeParse(response.data);
  if (!results.success) {
    console.warn('API Data Mismatch:', results.error.format());
    return response.data; // Vẫn trả về data cũ nếu bạn muốn "sống chung với lũ"
  }
  return results.data;
};

export const lockUser = async (staffId: string, locked: boolean) => {
  const response = await adminApi.lockUser(staffId, locked);
  return response.data;
};

export const updateUser = async (
  userId: string,
  userData: UserManagementDTO,
) => {
  const response = await adminApi.updateUser(userId, userData);
  const reuslt = UserResponseObject.safeParse(response.data);
  if (!reuslt.success) {
    console.warn('API Data Mismatch:', reuslt.error.format());
    return response.data; // Vẫn trả về data cũ nếu bạn muốn "sống chung với lũ"
  }
  return reuslt.data;
};

export const resetPassword = async (userId: string) => {
  const response = await adminApi.resetPassword(userId);
  return response.data;
};
