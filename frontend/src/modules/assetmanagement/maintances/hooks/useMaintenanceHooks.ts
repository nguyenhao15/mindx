import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import {
  createMaintanceAction,
  getAvailableActionUpdate,
  getMaintanceAction,
  getMaintanceDetailById,
  updateMaintanceAction,
} from '../queries/maintanceActions';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';
import type { UpdateMaintenanceRequestDTO } from '../schema/maintenaceSchema';

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

export const useUpdateMaintance = (options = {}) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async ({ id, data }: { id: string; data: FormData }) => {
      return updateMaintanceAction(id, data);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['maintenances'] });
    },
    ...options,
  });
};

export const useGetMaintanceDetailById = (itemId: number, options = {}) => {
  return useQuery({
    queryKey: ['maintenances', itemId],
    queryFn: async () => {
      const response = await getMaintanceDetailById(itemId);
      return response;
    },
    enabled: !!itemId,
    staleTime: 1000 * 60 * 15, // 15 minutes
    refetchOnMount: false,
    refetchOnWindowFocus: false,
    refetchOnReconnect: false,
    ...options,
  });
};

export const useGetAvailableActionUpdate = (itemId: number, options = {}) => {
  return useQuery({
    queryKey: ['availableActions', itemId],
    queryFn: async () => {
      const response = await getAvailableActionUpdate(itemId);
      return response;
    },
    enabled: !!itemId,
    staleTime: 1000 * 60 * 15, // 15 minutes
    refetchOnMount: false,
    refetchOnWindowFocus: false,
    refetchOnReconnect: false,
    ...options,
  });
};
