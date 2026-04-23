import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';
import type { WorkProfileType } from '../../auth/schemas/userSchema';
import { hrApi } from '../api/hrApi';

export const updateStaffProfileAction = async (
  staffId: string,
  data: Partial<WorkProfileType>,
) => {
  const res = await hrApi.updateStaffProfile(staffId, data);
  return res.data;
};

export const getStaffProfileByDepartmentAction = async (
  departmentId: string,
) => {
  const res = await hrApi.getStaffProfileByDepartment(departmentId);
  return res.data;
};

export const getPaginationStaffProfiles = async (
  payload: FilterWithPaginationInput,
) => {
  const res = await hrApi.getStaffProfilePagination(payload);
  return res.data;
};
