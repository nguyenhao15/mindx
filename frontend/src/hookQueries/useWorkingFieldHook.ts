import {
  createWorkingFieldAction,
  getActiveWorkingFieldListAction,
  getAllWorkingFieldListAction,
  updateWorkingFieldAction,
} from '@/actions/workingFieldAction';

import type { WorkingFieldFormData } from '@/validations/workingFieldSchema';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';

export const useCreateWorkingField = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationKey: ['createWorkingField'],
    mutationFn: async (data: WorkingFieldFormData) => {
      const response = await createWorkingFieldAction(data);
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['workingFieldList'] });
      queryClient.invalidateQueries({ queryKey: ['activeWorkingFieldList'] });
    },
  });
};

export const useGetWorkingFieldList = () => {
  return useQuery({
    queryKey: ['workingFieldList'],
    queryFn: async () => {
      const response = await getAllWorkingFieldListAction();
      return response;
    },
    staleTime: Infinity,
    gcTime: Infinity,
  });
};

export const useGetActiveWorkingFieldList = () => {
  return useQuery({
    queryKey: ['activeWorkingFieldList'],
    queryFn: async () => {
      const response = await getActiveWorkingFieldListAction();
      return response;
    },
    staleTime: Infinity,
    gcTime: Infinity,
  });
};

export const useUpdateWorkingField = (id: string) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationKey: ['updateWorkingField', id],
    mutationFn: async (data: WorkingFieldFormData) => {
      const response = await updateWorkingFieldAction(id, data);
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['workingFieldList'] });
      queryClient.invalidateQueries({ queryKey: ['activeWorkingFieldList'] });
    },
  });
};
