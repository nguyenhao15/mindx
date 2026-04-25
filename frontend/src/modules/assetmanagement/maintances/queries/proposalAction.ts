import { maintenanceWorkFlowApi } from '../api/maintenanceWorkFlowApi';
import type { CreateProposalRequestDTO } from '../schema/proposalSchema';

export const createNewProposals = async (
  id: number,
  data: CreateProposalRequestDTO[],
) => {
  const res = await maintenanceWorkFlowApi.createNewProposals(id, data);
  return res.data;
};
