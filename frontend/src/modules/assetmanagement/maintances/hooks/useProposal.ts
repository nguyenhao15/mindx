import { useMutation, useQueryClient } from '@tanstack/react-query';
import type { CreateProposalRequestDTO } from '../schema/proposalSchema';
import { maintenanceWorkFlowApi } from '../api/maintenanceWorkFlowApi';

export const useCreateProposal = (id: number, options = {}) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (data: CreateProposalRequestDTO[]) => {
      const res = await maintenanceWorkFlowApi.createNewProposals(id, data);
      return res.data;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['maintenances', id] });
    },
    ...options,
  });
};
