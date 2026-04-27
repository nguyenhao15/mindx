import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import {
  createWorkflow,
  deleteWorkFlowTransition,
  getWorkFlowByModule,
  getWorkFlowTransitionById,
  getWorkFlowTransitionPage,
  updateWorkFlow,
} from '../queries/workflowAction';

export const useCreateWorkflow = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (data: any) => {
      const response = await createWorkflow(data);
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['admin', 'workflows'] });
    },
    onError: (error) => {
      console.error('Error creating workflow:', error);
      throw error; // Rethrow the error to be handled by the caller
    },
  });
};

export const useUpdateWorkflow = (id: string, options = {}) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (data: any) => {
      const response = await updateWorkFlow(id, data);
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['admin', 'workflows'] });
    },
    ...options,
  });
};

export const useDeleteWorkflow = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (id: string) => {
      const response = await deleteWorkFlowTransition(id);
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['admin', 'workflows'] });
    },
    onError: (error) => {
      console.error('Error deleting workflow:', error);
      throw error; // Rethrow the error to be handled by the caller
    },
  });
};

export const useGetWorkflowById = (id: string) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async () => {
      const response = await getWorkFlowTransitionById(id);
      return response;
    },
    onSuccess: (data) => {
      queryClient.setQueryData(['admin', 'workflow', id], data);
    },
    onError: (error) => {
      console.error('Error fetching workflow by ID:', error);
      throw error; // Rethrow the error to be handled by the caller
    },
  });
};

export const useGetWorkflowPage = (filterWithPagination: any) => {
  return useQuery({
    queryKey: ['admin', 'workflows', 'page', filterWithPagination],
    queryFn: async () => {
      const response = await getWorkFlowTransitionPage(filterWithPagination);
      return response;
    },
  });
};

export const useGetWorkflowByModule = (module: string) => {
  return useQuery({
    queryKey: ['admin', 'workflows', 'module', module],
    queryFn: async () => {
      const response = await getWorkFlowByModule(module);
      return response;
    },
    enabled: !!module, // Chỉ chạy query khi module có giá trị hợp lệ
  });
};
