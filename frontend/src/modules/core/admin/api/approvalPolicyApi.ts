import { APPROVAL_POLICY_ENDPOINT } from '@/constants/api-endpoint';
import axiosClient from '@/lib/axiosClient';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';

export const approvalPolicyApi = {
  createApprovalPolicy: (data: any) => {
    return axiosClient.post(APPROVAL_POLICY_ENDPOINT, data);
  },

  updateApprovalPolicy: (id: string, data: any) => {
    return axiosClient.put(`${APPROVAL_POLICY_ENDPOINT}/${id}`, data);
  },

  getApprovalPolicyPage: (filterWithPagination: FilterWithPaginationInput) => {
    return axiosClient.post(
      `${APPROVAL_POLICY_ENDPOINT}/get/page`,
      filterWithPagination,
    );
  },

  getApprovalPolicyById: (id: string) => {
    return axiosClient.get(`${APPROVAL_POLICY_ENDPOINT}/${id}`);
  },

  deleteApprovalPolicy: (id: string) => {
    return axiosClient.delete(`${APPROVAL_POLICY_ENDPOINT}/${id}`);
  },
};
