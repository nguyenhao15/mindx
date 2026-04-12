import { useMutation, useQueryClient } from '@tanstack/react-query';
import { createMaintanceAction } from '../queries/maintanceActions';
import type { CreateMaintenanceRequestDTO } from '../schema/maintenaceSchema';

export const useCreateMaintance = (options = {}) => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (data: CreateMaintenanceRequestDTO) => {
      return createMaintanceAction(data);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['maintenances'] });
    },
    ...options,
  });
};
