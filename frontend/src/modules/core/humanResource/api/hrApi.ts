import axiosClient from '@/lib/axiosClient';
import type { WorkProfileType } from '../../auth/schemas/userSchema';
import { STAFF_PROFILE_ENDPOINT } from '@/constants/api-endpoint';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';

export const hrApi = {
  updateStaffProfile: (staffId: string, data: Partial<WorkProfileType>) => {
    return axiosClient.put(`${STAFF_PROFILE_ENDPOINT}/update/${staffId}`, data);
  },

  getStaffProfileByDepartment: (departmentId: string) => {
    return axiosClient.get(
      `${STAFF_PROFILE_ENDPOINT}/department/${departmentId}`,
    );
  },

  getStaffProfilePagination: (payload: FilterWithPaginationInput) => {
    return axiosClient.post(`${STAFF_PROFILE_ENDPOINT}/get/page`, payload);
  },

  deleteStaffProfile: (staffId: string) => {
    return axiosClient.delete(`${STAFF_PROFILE_ENDPOINT}/${staffId}`);
  },
};
