import { MAINTANANCE_ENDPOINT } from '@/constants/api-endpoint';
import axiosClient from '@/lib/axiosClient';
import type { UpdateMaintenanceRequestDTO } from '../schema/maintenaceSchema';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';

export const mainteanceApi = {
  getMaintenances: (assetId: number) => {
    return axiosClient.get(`${MAINTANANCE_ENDPOINT}/${assetId}`);
  },

  createMaintenance: (data: FormData) => {
    return axiosClient.post(`${MAINTANANCE_ENDPOINT}/create`, data, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  },

  getMaintenance: (filterInput: FilterWithPaginationInput) => {
    return axiosClient.post(`${MAINTANANCE_ENDPOINT}/get/page`, filterInput);
  },

  updateMaintenance: (id: string, data: UpdateMaintenanceRequestDTO) => {
    return axiosClient.put(`${MAINTANANCE_ENDPOINT}/${id}`, data);
  },

  deleteMaintenance: (id: number) => {
    return axiosClient.delete(`${MAINTANANCE_ENDPOINT}/${id}`);
  },

  getAvailableActionUpdate: (id: number) => {
    return axiosClient.get(`${MAINTANANCE_ENDPOINT}/available-actions/${id}`);
  },
};
