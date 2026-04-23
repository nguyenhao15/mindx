import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import type { WorkProfileType } from '../../auth/schemas/userSchema';
import {
  getPaginationStaffProfiles,
  getStaffProfileByDepartmentAction,
  updateStaffProfileAction,
} from '../queries/hrActions';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';

export const useUpdateStaffProfile = (staffId: string) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (data: Partial<WorkProfileType>) =>
      updateStaffProfileAction(staffId, data),
    onSuccess: (value) => {
      queryClient.invalidateQueries({
        queryKey: ['admin', 'user', value.staffId],
      });
    },
  });
};

export const useGetStaffProfileByDepartment = (departmentId: string) => {
  return useQuery({
    queryKey: ['hr', 'staffProfile', departmentId],
    queryFn: () => getStaffProfileByDepartmentAction(departmentId),
    enabled: !!departmentId,
  });
};

export const useGetPaginationStaffProfiles = (
  payload: FilterWithPaginationInput,
  options = {},
) => {
  return useQuery({
    queryKey: ['hr', 'staffProfiles', payload],
    queryFn: () => getPaginationStaffProfiles(payload),
    enabled: !!payload,
    ...options,
  });
};
