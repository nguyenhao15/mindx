import type { WorkProfileType } from '../../auth/schemas/userSchema';
import { hrApi } from '../api/hrApi';

export const updateStaffProfileAction = async (
  staffId: string,
  data: Partial<WorkProfileType>,
) => {
  const res = await hrApi.updateStaffProfile(staffId, data);
  return res.data;
};
