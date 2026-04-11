import { PROCESS_FLOW } from '@/modules/documentation/constants/api-endpoint';
import axiosClient from '@/lib/axiosClient';
import type { FilterWithPaginationInput } from '@/modules/documentation/validations/filterWithPagination';

export const processFlowApi = {
  createProcessFlow: (data: FormData) => {
    return axiosClient.post(PROCESS_FLOW, data, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  },

  getAllProcessFlows: (payload: FilterWithPaginationInput) => {
    return axiosClient.post(`${PROCESS_FLOW}/all`, payload);
  },

  getProcessFlows: (payload: FilterWithPaginationInput, active: boolean) => {
    return axiosClient.post(`${PROCESS_FLOW}/active/${active}`, payload);
  },

  getProcessFlowById: (id: string) => {
    return axiosClient.get(`${PROCESS_FLOW}/${id}/full-info`);
  },

  getProcessFlowByDepartmentById: (
    id: string,
    filter: FilterWithPaginationInput,
  ) => {
    return axiosClient.post(`${PROCESS_FLOW}/by-department/${id}`, filter);
  },

  getDocumentByProcessing: (filter: FilterWithPaginationInput) => {
    return axiosClient.post(`${PROCESS_FLOW}/by-processing`, filter);
  },

  updateProcessFlow: (id: string, data: FormData) => {
    return axiosClient.patch(`${PROCESS_FLOW}/${id}`, data, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  },

  activateDocument: (id: string) => {
    return axiosClient.patch(`${PROCESS_FLOW}/activate-document/${id}`);
  },

  searchDocument: (keyword: string) => {
    return axiosClient.get(
      `${PROCESS_FLOW}/search-documents?keyword=${keyword}`,
    );
  },
};
