import { WORKFLOW_ENDPOINT } from '@/constants/api-endpoint';
import axiosClient from '@/lib/axiosClient';
import type { CreateProposalRequestDTO } from '../schema/proposalSchema';

export const maintenanceWorkFlowApi = {
  createNewProposals: (id: number, data: CreateProposalRequestDTO[]) => {
    return axiosClient.post(`${WORKFLOW_ENDPOINT}/proposal/create/${id}`, data);
  },
};
