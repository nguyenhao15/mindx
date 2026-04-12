import { PROCESS_TAG } from '@/constants/api-endpoint';
import axiosClient from '@/lib/axiosClient';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';
import type { TagDTO } from '@/modules/documentations/tag/schema/tagSchema';

export const processTagApi = {
  getAllProcessTag: (payload: FilterWithPaginationInput) => {
    return axiosClient.post(`${PROCESS_TAG}/get-all`, payload);
  },

  getActiveProcessTag: () => {
    return axiosClient.get(`${PROCESS_TAG}/active/true`);
  },

  createNewTag: (data: TagDTO) => {
    return axiosClient.post(PROCESS_TAG, data);
  },

  updateTag: (id: string, data: TagDTO) => {
    return axiosClient.patch(`${PROCESS_TAG}/${id}`, data);
  },
};
