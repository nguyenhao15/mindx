import { processFlowApi } from '@/modules/documentation/api/processFlowApi';
import type { FilterWithPaginationInput } from '@/modules/documentation/validations/filterWithPagination';

export const createNewProcessFlowActions = async (data: FormData) => {
  const response = await processFlowApi.createProcessFlow(data);
  return response.data;
};

export const getActivePorcesses = async (
  params: FilterWithPaginationInput,
  active: boolean,
) => {
  const response = await processFlowApi.getProcessFlows(params, active);
  return response.data;
};

export const getAllPorcesses = async (params: FilterWithPaginationInput) => {
  const response = await processFlowApi.getAllProcessFlows(params);
  return response.data;
};

export const getProcessFlowWithFullInfo = async (id: string) => {
  const response = await processFlowApi.getProcessFlowById(id);
  return response.data;
};

export const updateProcessFlowActions = async (id: string, data: FormData) => {
  const response = await processFlowApi.updateProcessFlow(id, data);
  return response.data;
};

export const getProcessFlowByDepartmentByIdActions = async (
  id: string,
  filter: FilterWithPaginationInput,
) => {
  const response = await processFlowApi.getProcessFlowByDepartmentById(
    id,
    filter,
  );
  return response.data;
};

export const getDocumentByProcessingActions = async (
  filter: FilterWithPaginationInput,
) => {
  const response = await processFlowApi.getDocumentByProcessing(filter);
  return response.data;
};

export const searchDocumentActions = async (keyword: string) => {
  const response = await processFlowApi.searchDocument(keyword);
  return response.data;
};

export const activateDocumentActions = async (id: string) => {
  const response = await processFlowApi.activateDocument(id);
  return response.data;
};
