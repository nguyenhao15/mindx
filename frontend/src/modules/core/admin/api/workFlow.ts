import { APPROVAL_WORKFLOW_ENDPOINT } from '@/constants/api-endpoint';
import axiosClient from '@/lib/axiosClient';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';

export const workflowApi = {
  createWorkflow: (data: any) => {
    return axiosClient.post(APPROVAL_WORKFLOW_ENDPOINT, data);
  },

  updateWorkFlow: (id: string, data: any) => {
    return axiosClient.put(`${APPROVAL_WORKFLOW_ENDPOINT}/${id}`, data);
  },

  getWorkFlowTransitionPage: (
    filterWithPagination: FilterWithPaginationInput,
  ) => {
    return axiosClient.post(
      `${APPROVAL_WORKFLOW_ENDPOINT}/get/page`,
      filterWithPagination,
    );
  },

  getWorkFlowTransitionById: (id: string) => {
    return axiosClient.get(`${APPROVAL_WORKFLOW_ENDPOINT}/${id}`);
  },

  deleteWorkFlowTransition: (id: string) => {
    return axiosClient.delete(`${APPROVAL_WORKFLOW_ENDPOINT}/${id}`);
  },
};
