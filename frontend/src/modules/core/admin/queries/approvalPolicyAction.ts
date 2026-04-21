import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';
import { approvalPolicyApi } from '../api/approvalPolicyApi';

export const createNewApprovalPolicy = async (data: any) => {
  const res = await approvalPolicyApi.createApprovalPolicy(data);
  return res.data;
};

export const updateApprovalPolicy = async (id: string, data: any) => {
  const res = await approvalPolicyApi.updateApprovalPolicy(id, data);
  return res.data;
};

export const getApprovalPolicyPage = async (
  filterWithPagination: FilterWithPaginationInput,
) => {
  const res =
    await approvalPolicyApi.getApprovalPolicyPage(filterWithPagination);
  return res.data;
};

export const getApprovalPolicyById = async (id: string) => {
  const res = await approvalPolicyApi.getApprovalPolicyById(id);
  return res.data;
};

export const deleteApprovalPolicy = async (id: string) => {
  const res = await approvalPolicyApi.deleteApprovalPolicy(id);
  return res.data;
};
