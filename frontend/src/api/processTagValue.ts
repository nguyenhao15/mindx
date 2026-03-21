import { PROCESS_TAG_VALUES } from '@/constants/api-endpoint';
import axiosClient from '@/lib/axiosClient';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';
import type { TagValueDTO } from '@/validations/tagValueSchema';

export const processTagValueApi = {
  getProcessTagValue: (payload: FilterWithPaginationInput) => {
    return axiosClient.post(`${PROCESS_TAG_VALUES}/get-all`, payload);
    // Implement API call to fetch process tag values
  },

  getActiveTagValueOptions: () => {
    return axiosClient.get(`${PROCESS_TAG_VALUES}/active-options`);
    // Implement API call to fetch active tag value options
  },

  createProcessTagValue: (data: TagValueDTO) => {
    return axiosClient.post(PROCESS_TAG_VALUES, data);
  },

  updateTagValue: (id: string, data: TagValueDTO) => {
    return axiosClient.patch(`${PROCESS_TAG_VALUES}/${id}`, data);
  },
};
