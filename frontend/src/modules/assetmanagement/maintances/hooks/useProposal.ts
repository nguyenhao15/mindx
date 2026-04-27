import { useMutation, useQueryClient } from '@tanstack/react-query';
import type {
  CreateProposalRequestDTO,
  ProposalNestObject,
} from '../schema/proposalSchema';
import { maintenanceWorkFlowApi } from '../api/maintenanceWorkFlowApi';
import type { MaintananceDetailsDTO } from '../schema/maintenaceSchema';
import { updateProposals } from '../queries/proposalAction';

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

export const useUpdateProposal = (options = {}) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async ({
      id,
      data,
    }: {
      id: number;
      data: CreateProposalRequestDTO;
    }) => {
      const res = await updateProposals(id, data);
      return res;
    },
    onSuccess: (responseItem: any) => {
      // Update cache với data mới nhất từ response (MaintenanceDetailResponse)
      queryClient.setQueryData(
        ['maintenances', responseItem?.maintenanceDetailsInfo?.id],
        responseItem,
      );
    },
    ...options,
  });
};
