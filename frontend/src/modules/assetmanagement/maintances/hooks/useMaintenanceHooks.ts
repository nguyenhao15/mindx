import { useMutation, useQueryClient } from '@tanstack/react-query';
import { createMaintanceAction } from '../queries/maintanceActions';

export const useCreateMaintance = (options = {}) => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (data: FormData) => {
      return createMaintanceAction(data);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['maintenances'] });
    },
    ...options,
  });
};
