import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import {
  createNewApprovalPolicy,
  deleteApprovalPolicy,
  getApprovalPolicyById,
  getApprovalPolicyPage,
  updateApprovalPolicy,
} from '../queries/approvalPolicyAction';

export const useCreateNewApprovalPolicy = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (data: any) => {
      const response = await createNewApprovalPolicy(data);
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ['admin', 'approvalPolicies'],
      });
    },
    onError: (error) => {
      console.error('Error creating approval policy:', error);
      throw error; // Rethrow the error to be handled by the caller
    },
  });
};

export const useUpdateApprovalPolicy = (id: string) => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (data: any) => {
      const response = await updateApprovalPolicy(id, data);
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ['admin', 'approvalPolicies'],
      });
    },
    onError: (error) => {
      console.error('Error updating approval policy:', error);
      throw error; // Rethrow the error to be handled by the caller
    },
  });
};

export const useDeleteApprovalPolicy = (id: string) => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async () => {
      const response = await deleteApprovalPolicy(id);
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ['admin', 'approvalPolicies'],
      });
    },
    onError: (error) => {
      console.error('Error deleting approval policy:', error);
      throw error; // Rethrow the error to be handled by the caller
    },
  });
};

export const useGetApprovalPolicyById = (id: string) => {
  return useQuery({
    queryKey: ['admin', 'approvalPolicies', id],
    queryFn: async () => {
      const response = await getApprovalPolicyById(id);
      return response;
    },
  });
};

export const useGetApprovalPolicyPage = (filterWithPagination: any) => {
  return useQuery({
    queryKey: ['admin', 'approvalPolicies', filterWithPagination],
    queryFn: async () => {
      const response = await getApprovalPolicyPage(filterWithPagination);
      return response;
    },
  });
};
