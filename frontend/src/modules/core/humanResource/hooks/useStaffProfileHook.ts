import { useMutation, useQueryClient } from '@tanstack/react-query';
import type { WorkProfileType } from '../../auth/schemas/userSchema';
import { updateStaffProfileAction } from '../queries/hrActions';

export const useUpdateStaffProfile = (staffId: string) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (data: Partial<WorkProfileType>) =>
      updateStaffProfileAction(staffId, data),
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ['admin', 'user', staffId],
      });
    },
  });
};
