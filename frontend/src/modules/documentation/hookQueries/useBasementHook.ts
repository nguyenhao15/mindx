import {
  createBasement,
  getActiveBuItems,
  getAllBasements,
  updateBasement,
} from '@/modules/documentation/actions/basementAction';
import type { FilterWithPaginationInput } from '@/modules/documentation/validations/filterWithPagination';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';

export const useActiveBuItems = () => {
  return useQuery({
    queryKey: ['active-bu-items'],
    queryFn: async () => {
      const data = await getActiveBuItems();
      return data;
    },
    staleTime: Infinity,
    gcTime: Infinity,
  });
};

export const useGetAllBasements = (payload: FilterWithPaginationInput) => {
  return useQuery({
    queryKey: ['all-basements', payload],
    queryFn: async () => {
      const data = await getAllBasements(payload);
      return data;
    },
    staleTime: Infinity,
    gcTime: Infinity,
  });
};

export const useCreateBasement = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (data: any) => {
      const response = await createBasement(data);
      return response;
    },
    onSuccess: (variance) => {
      queryClient.setQueryData(['all-basements'], (oldData: any) => {
        return [...oldData, variance];
      });
      queryClient.invalidateQueries({ queryKey: ['active-bu-items'] });
    },
  });
};

export const useUpdateBasement = (id: string) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (data: any) => {
      const response = await updateBasement(id, data);
      return response;
    },
    onSuccess: (updatedItem) => {
      queryClient.setQueriesData(
        { queryKey: ['all-basements'] },
        (oldData: any) => {
          if (!oldData) return oldData;

          const list = Array.isArray(oldData?.data) ? oldData.data : [];
          const nextList = list.map((item: any) => {
            const itemId = item?.id ?? item?._id;
            const updatedId = updatedItem?.id ?? updatedItem?._id;
            return itemId === updatedId ? { ...item, ...updatedItem } : item;
          });

          return {
            ...oldData,
            data: nextList,
          };
        },
      );
      queryClient.invalidateQueries({ queryKey: ['active-bu-items'] });
    },
  });
};
