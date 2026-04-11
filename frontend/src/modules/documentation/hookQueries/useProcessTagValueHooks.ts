import {
  createProcessTagValue,
  getActiveTagValueOptions,
  processTagValueActions,
  updateProcessTagValue,
} from '@/modules/documentation/actions/processTagValueActions';
import type { FilterWithPaginationInput } from '@/modules/documentation/validations/filterWithPagination';
import type { TagValueDTO } from '@/modules/documentation/validations/tagValueSchema';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import toast from 'react-hot-toast';

export const useGetProcessTagValues = (payload: FilterWithPaginationInput) => {
  return useQuery({
    queryKey: ['processTagValues', payload],
    queryFn: async () => {
      const response = await processTagValueActions(payload);
      return response;
    },
    staleTime: 1000 * 60 * 15, // 15 minutes
  });
};

export const useCreateTagValue = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationKey: ['createProcessTagValue'],
    mutationFn: async (data: TagValueDTO) => {
      const response = await createProcessTagValue(data);
      return response;
    },
    onSuccess: () => {
      // Invalidate and refetch process tag values after successful creation
      queryClient.invalidateQueries({ queryKey: ['processTagValues'] });
    },
    onError: (error) => {
      const errorMessage =
        error?.response?.data?.message || 'Có lỗi xảy ra khi tạo tag';
      toast.error(errorMessage);
    },
  });
};

export const useUpdateTagValue = (id: string, options = {}) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationKey: ['updateProcessTagValue'],
    mutationFn: async (data: TagValueDTO) => {
      const response = await updateProcessTagValue(id, data);
      return response;
    },
    ...options,
    onSuccess: () => {
      // Invalidate and refetch process tag values after successful update
      queryClient.invalidateQueries({ queryKey: ['processTagValues'] });
    },
    onError: (error) => {
      const errorMessage =
        error?.response?.data?.message || 'Có lỗi xảy ra khi cập nhật tag';
      toast.error(errorMessage);
    },
  });
};

export const useGetActiveTagValueOptions = () => {
  return useQuery({
    queryKey: ['activeTagValueOptions'],
    queryFn: async () => {
      const response = await getActiveTagValueOptions();
      return response;
    },
    staleTime: 1000 * 60 * 15, // 15 minutes
    refetchOnMount: false,
    refetchOnWindowFocus: false,
    refetchOnReconnect: false,
  });
};
