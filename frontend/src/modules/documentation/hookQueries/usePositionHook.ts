import {
  createPositionAction,
  getPositionByActiveAction,
  getPositionsAction,
  updatePositionAction,
} from '@/modules/documentation/actions/positionAction';
import { queryClient } from '@/lib/queryClient';
import type { FilterWithPaginationInput } from '@/modules/documentation/validations/filterWithPagination';
import type { PositionFormData } from '@/modules/documentation/validations/positionSchema';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';

export const useGetPositions = (
  payload: FilterWithPaginationInput,
  opts = {},
) => {
  return useQuery({
    queryKey: ['positions', payload],
    queryFn: async () => {
      const response = await getPositionsAction(payload);
      return response;
    },
    ...opts,
  });
};

export const useCreatePosition = () => {
  return useMutation({
    mutationFn: async (data: PositionFormData) => {
      const response = await createPositionAction(data);
      return response;
    },
    onSuccess: (variance) => {
      queryClient.setQueryData(['active-positions'], (oldData: any) => {
        return [...oldData, variance];
      });
    },
  });
};

export const useGetActivePositions = () => {
  return useQuery({
    queryKey: ['active-positions'],
    queryFn: async () => {
      const response = await getPositionByActiveAction(true);
      return response;
    },
  });
};

export const useUpdatePosition = (id: string) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (data: PositionFormData) => {
      const response = await updatePositionAction(id, data);
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['positions'] });
      queryClient.invalidateQueries({ queryKey: ['positions', id] });
      queryClient.invalidateQueries({ queryKey: ['active-positions'] });
    },
  });
};
