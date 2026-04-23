import axiosClient from '@/lib/axiosClient';
import type { WorkProfileType } from '../../auth/schemas/userSchema';
import { STAFF_PROFILE_ENDPOINT } from '@/constants/api-endpoint';

export const hrApi = {
  updateStaffProfile: (staffId: string, data: Partial<WorkProfileType>) => {
    return axiosClient.put(`${STAFF_PROFILE_ENDPOINT}/update/${staffId}`, data);
  },

  getStaffProfileByDepartment: (departmentId: string) => {
    return axiosClient.get(
      `${STAFF_PROFILE_ENDPOINT}/department/${departmentId}`,
    );
  },

  deleteStaffProfile: (staffId: string) => {
    return axiosClient.delete(`${STAFF_PROFILE_ENDPOINT}/${staffId}`);
  },
};
