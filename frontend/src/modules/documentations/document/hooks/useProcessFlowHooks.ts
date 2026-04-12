import {
  activateDocumentActions,
  createNewProcessFlowActions,
  getActivePorcesses,
  getAllPorcesses,
  getDocumentByProcessingActions,
  getProcessFlowByDepartmentByIdActions,
  getProcessFlowWithFullInfo,
  searchDocumentActions,
  updateProcessFlowActions,
} from '@/modules/documentations/document/queries/processFlowActions';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import toast from 'react-hot-toast';

export const useCreateProcessFlow = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (data: FormData) => {
      const response = await createNewProcessFlowActions(data);
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['processFlows'] });
    },
    onError: (error) => {
      console.error('Error creating process flow:', error);
      toast.error('Failed to create process flow');
    },
  });
};

export const useGetActiveProcessFlows = (params: any, active: boolean) => {
  return useQuery({
    queryKey: ['processFlows', params, active],
    queryFn: async () => {
      const response = await getActivePorcesses(params, active);
      return response;
    },
    enabled: !!params,
    staleTime: 1000 * 60 * 15, // 15 minutes
    refetchOnMount: false,
    refetchOnWindowFocus: false,
    refetchOnReconnect: false,
  });
};

export const useGetProcessFlowWithFullInfo = (id: string) => {
  return useQuery({
    queryKey: ['processFlowFullInfo', id],
    queryFn: async () => {
      const response = await getProcessFlowWithFullInfo(id);
      return response;
    },
    enabled: !!id,
  });
};

export const useUpdateProcessFlow = (id: string) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (data: FormData) => {
      const response = await updateProcessFlowActions(id, data);
      return response;
    },
    onSuccess: (_data) => {
      queryClient.invalidateQueries({ queryKey: ['processFlowFullInfo', id] });
    },
    onError: (error) => {
      console.error('Error updating process flow:', error);
      toast.error('Failed to update process flow');
    },
  });
};

export const useGetProcessFlowByDepartmentById = (
  params: { id: string; filter: FilterWithPaginationInput },
  options = {},
) => {
  return useQuery({
    queryKey: ['processFlowsByDepartment', params],
    queryFn: async () => {
      const response = await getProcessFlowByDepartmentByIdActions(
        params.id,
        params.filter,
      );
      return response;
    },
    enabled: !!params.id && !!params.filter,
    ...options,
  });
};

export const useGetDocumentByProcessing = (
  filter: FilterWithPaginationInput,
  options = {},
) => {
  return useQuery({
    queryKey: ['documentsByProcessing', filter],
    queryFn: async () => {
      const response = await getDocumentByProcessingActions(filter);
      return response;
    },
    enabled: !!filter,
    ...options,
  });
};

export const useSearchDocument = (keyword: string, options = {}) => {
  return useQuery({
    queryKey: ['searchDocument', keyword],
    queryFn: async () => {
      const response = await searchDocumentActions(keyword);
      return response;
    },
    enabled: !!keyword,
    ...options,
  });
};

export const useGetAllProcessFlows = (payload: FilterWithPaginationInput) => {
  return useQuery({
    queryKey: ['allProcessFlows', payload],
    queryFn: async () => {
      const response = await getAllPorcesses(payload);
      return response;
    },
    enabled: !!payload,
    staleTime: 1000 * 60 * 15, // 15 minutes
    refetchOnMount: false,
    refetchOnWindowFocus: false,
    refetchOnReconnect: false,
  });
};

export const useActivateProcessFlow = (id: string) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async () => {
      const response = await activateDocumentActions(id);
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['processFlows'] });
      queryClient.invalidateQueries({ queryKey: ['allProcessFlows'] });
      queryClient.invalidateQueries({ queryKey: ['processFlowFullInfo', id] });
    },
    onError: (error) => {
      console.error('Error activating process flow:', error);
      toast.error('Failed to activate process flow');
    },
  });
};
