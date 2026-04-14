import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import {
  createMaintanceAction,
  getMaintanceAction,
} from '../queries/maintanceActions';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';

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

export const useGetMaintenances = (
  filterWithPaginationInput: FilterWithPaginationInput,
  options = {},
) => {
  return useQuery({
    queryKey: ['maintenances', filterWithPaginationInput],
    queryFn: async () => {
      const response = await getMaintanceAction(filterWithPaginationInput);
      return response;
    },
    enabled: !!filterWithPaginationInput,
    staleTime: 1000 * 60 * 15, // 15 minutes
    refetchOnMount: false,
    refetchOnWindowFocus: false,
    refetchOnReconnect: false,
    ...options,
  });
};
