import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import {
  createMaintanceAction,
  getAvailableActionUpdate,
  getMaintanceAction,
  getMaintanceDetailById,
  updateMaintanceAction,
} from '../queries/maintanceActions';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';
import type {
  MaintenanceStatus,
  UpdateMaintenanceRequestDTO,
} from '../schema/maintenaceSchema';

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
    mutationFn: async ({
      id,
      data,
    }: {
      id: string;
      data: UpdateMaintenanceRequestDTO;
    }) => {
      return updateMaintanceAction(id, data);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['maintenances'] });
    },
    ...options,
  });
};

export const useGetMaintanceDetailById = (assetId: number, options = {}) => {
  return useQuery({
    queryKey: ['maintenances', assetId],
    queryFn: async () => {
      const response = await getMaintanceDetailById(assetId);
      return response;
    },
    enabled: !!assetId,
    staleTime: 1000 * 60 * 15, // 15 minutes
    refetchOnMount: false,
    refetchOnWindowFocus: false,
    refetchOnReconnect: false,
    ...options,
  });
};

export const useGetAvailableActionUpdate = (
  currentStatus: MaintenanceStatus,
  options = {},
) => {
  return useQuery({
    queryKey: ['availableActions', currentStatus],
    queryFn: async () => {
      const response = await getAvailableActionUpdate(currentStatus);
      return response;
    },
    enabled: !!currentStatus,
    staleTime: 1000 * 60 * 15, // 15 minutes
    refetchOnMount: false,
    refetchOnWindowFocus: false,
    refetchOnReconnect: false,
    ...options,
  });
};
