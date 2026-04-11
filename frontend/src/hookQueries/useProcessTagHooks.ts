import {
  createProcessTag,
  getActiveProcessTag,
  getProcessTag,
  updateProcessTag,
} from '@/actions/processTagActions';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';
import type { TagDTO } from '@/validations/tagSchema';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import toast from 'react-hot-toast';

export const useGetActiveProcessTag = (options = {}) => {
  return useQuery({
    queryKey: ['processTag', 'active', options],
    queryFn: async () => {
      const response = await getActiveProcessTag();
      return response;
    },
    enabled: !!options,
    staleTime: 1000 * 60 * 15, // 15 minutes
    refetchOnMount: false,
    refetchOnWindowFocus: false,
    refetchOnReconnect: false,
    ...options,
  });
};

export const useGetProcessTag = (params: FilterWithPaginationInput) => {
  return useQuery({
    queryKey: ['processTag', params],
    queryFn: async () => {
      const response = await getProcessTag(params);
      return response;
    },
    staleTime: 1000 * 60 * 15, // 15 minutes
    refetchOnMount: false,
    refetchOnWindowFocus: false,
    refetchOnReconnect: false,
    gcTime: 1000 * 60 * 30,
    retry: false,
  });
};

export const useCreateProcessTag = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (data: TagDTO) => {
      const response = await createProcessTag(data);
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['processTag'] });
    },
    onError: (error) => {
      const errorMessage =
        error?.response?.data?.message || 'Có lỗi xảy ra khi tạo tag';
      toast.error(errorMessage);
    },
  });
};

export const useUpdateProcessTag = (id: string) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async ({ data }: { data: TagDTO }) => {
      const response = await updateProcessTag(id, data);
      return response;
    },
    onSuccess: (data) => {
      queryClient.setQueriesData(
        { queryKey: ['processTag'] },
        (oldData: any) => {
          if (!oldData) return oldData;
          const updatedTag = oldData.find((tag: any) => tag.id === id);
          if (updatedTag) {
            return oldData.map((tag: any) =>
              tag.id === id ? { ...tag, ...data } : tag,
            );
          }
          return oldData;
        },
      );
    },
    onError: (error) => {
      const errorMessage =
        error?.response?.data?.message || 'Có lỗi xảy ra khi cập nhật tag';
      toast.error(errorMessage);
    },
  });
};
