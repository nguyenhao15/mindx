import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import type { WorkProfileType } from '../../auth/schemas/userSchema';
import {
  getStaffProfileByDepartmentAction,
  updateStaffProfileAction,
} from '../queries/hrActions';

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
