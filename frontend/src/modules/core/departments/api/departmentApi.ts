import { DEPARTMENT_ENDPOINT } from '@/modules/documentations/constants/api-endpoint';
import axiosClient from '@/lib/axiosClient';
import type { DepartmentFormData } from '@/modules/core/departments/schemas/departmentSchema';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';

export const departmentApi = {
  createNewDepartment: (data: DepartmentFormData) => {
    return axiosClient.post(DEPARTMENT_ENDPOINT, data);
  },
  getAllDepartment: (payload: FilterWithPaginationInput) => {
    return axiosClient.post(`${DEPARTMENT_ENDPOINT}/all-departments`, payload);
  },

  getCurrentDepartment: () => {
    return axiosClient.get(`${DEPARTMENT_ENDPOINT}/current-working-department`);
  },

  getCanAccessDepartments: () => {
    return axiosClient.get(`${DEPARTMENT_ENDPOINT}/can-access-departments`);
  },

  getDepartmentById: (id: string) => {
    return axiosClient.get(`${DEPARTMENT_ENDPOINT}/${id}`);
  },

  getActiveDepartment: () => {
    return axiosClient.get(`${DEPARTMENT_ENDPOINT}/active-departments`);
  },

  getInSecureDepartment: (filterWithPagination: FilterWithPaginationInput) => {
    return axiosClient.post(
      `${DEPARTMENT_ENDPOINT}/insecure-departments`,
      filterWithPagination,
    );
  },

  updateDepartment: (id: string, data: DepartmentFormData) => {
    return axiosClient.patch(`${DEPARTMENT_ENDPOINT}/${id}`, data);
  },

  deleteDepartment: (id: string) => {
    return axiosClient.delete(`${DEPARTMENT_ENDPOINT}/${id}`);
  },
};
