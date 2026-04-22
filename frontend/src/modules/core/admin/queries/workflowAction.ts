import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';
import { workflowApi } from '../api/workFlow';

export const createWorkflow = async (data: any) => {
  const response = await workflowApi.createWorkflow(data);
  return response.data;
};

export const updateWorkFlow = async (id: string, data: any) => {
  const response = await workflowApi.updateWorkFlow(id, data);
  return response.data;
};

export const getWorkFlowTransitionPage = async (
  filterWithPagination: FilterWithPaginationInput,
) => {
  const response =
    await workflowApi.getWorkFlowTransitionPage(filterWithPagination);
  return response.data;
};

export const getWorkFlowByModule = async (module: string) => {
  const response = await workflowApi.getWorkFlowByModule(module);
  return response.data;
};

export const getWorkFlowTransitionById = async (id: string) => {
  const response = await workflowApi.getWorkFlowTransitionById(id);
  return response.data;
};

export const deleteWorkFlowTransition = async (id: string) => {
  const response = await workflowApi.deleteWorkFlowTransition(id);
  return response.data;
};
