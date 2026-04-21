import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import {
  createWorkflow,
  getWorkFlowTransitionPage,
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

export const useUpdateWorkflow = (id: string) => {
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
      console.error('Error updating workflow:', error);
      throw error; // Rethrow the error to be handled by the caller
    },
  });
};

export const useDeleteWorkflow = (id: string) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async () => {
      const response = await createWorkflow(id);
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
      const response = await createWorkflow(id);
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
