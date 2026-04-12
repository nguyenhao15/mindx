import { departmentApi } from '@/modules/core/departments/api/departmentApi';
import {
  DepartmentListObj,
  type DepartmentFormData,
} from '@/validations/departmentSchema';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';

export const createNewDepartmentAction = async (data: DepartmentFormData) => {
  const response = await departmentApi.createNewDepartment(data);
  return response.data;
};

export const getAllDepartmentAction = async (
  payload: FilterWithPaginationInput,
) => {
  const response = await departmentApi.getAllDepartment(payload);
  const { content, ...rest } = response.data;
  const results = DepartmentListObj.safeParse(content);

  if (!results.success) {
    console.warn('API Data Mismatch:', results.error);
    return response.data; // Vẫn trả về data cũ nếu bạn muốn "sống chung với lũ"
  }
  results.data = {
    content: results.data,
    ...rest,
  };
  return results.data;
};

export const getCanAccessDepartmentsAction = async () => {
  const response = await departmentApi.getCanAccessDepartments();
  return response.data;
};

export const getCurrentDepartmentAction = async () => {
  const response = await departmentApi.getCurrentDepartment();
  return response.data;
};

export const getDepartmentByIdAction = async (id: string) => {
  const response = await departmentApi.getDepartmentById(id);
  return response.data;
};

export const getActiveDepartmentAction = async () => {
  const response = await departmentApi.getActiveDepartment();
  const results = DepartmentListObj.safeParse(response.data);
  if (!results.success) {
    console.warn('API Data Mismatch:', results.error);
    return response.data; // Vẫn trả về data cũ nếu bạn muốn "sống chung với lũ"
  }

  return results.data;
};

export const getInSecureDepartmentAction = async (
  filterWithPagination: FilterWithPaginationInput,
) => {
  const response =
    await departmentApi.getInSecureDepartment(filterWithPagination);
  return response.data;
};

export const updateDepartmentAction = async (
  id: string,
  data: DepartmentFormData,
) => {
  const response = await departmentApi.updateDepartment(id, data);
  return response.data;
};

export const deleteDepartmentAction = async (id: string) => {
  const response = await departmentApi.deleteDepartment(id);
  return response.data;
};
