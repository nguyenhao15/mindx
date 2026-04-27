import { maintenanceWorkFlowApi } from '../api/maintenanceWorkFlowApi';
import type {
  CreateProposalRequestDTO,
  ProposalNestObject,
} from '../schema/proposalSchema';

export const createNewProposals = async (
  id: number,
  data: CreateProposalRequestDTO[],
) => {
  const res = await maintenanceWorkFlowApi.createNewProposals(id, data);
  return res.data;
};

export const updateProposals = async (id: number, data: CreateProposalRequestDTO) => {
  const res = await maintenanceWorkFlowApi.updateProposal(id, data);
  return res.data;
};
