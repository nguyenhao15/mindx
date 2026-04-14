import { MAINTANANCE_ENDPOINT } from '@/constants/api-endpoint';
import axiosClient from '@/lib/axiosClient';
import type {
  CreateMaintenanceRequestDTO,
  UpdateMaintenanceRequestDTO,
} from '../schema/maintenaceSchema';

export const mainteanceApi = {
  getMaintenances: (assetId: string) => {
    return axiosClient.get(`${MAINTANANCE_ENDPOINT}/${assetId}`);
  },

  createMaintenance: (data: FormData) => {
    return axiosClient.post(`${MAINTANANCE_ENDPOINT}/create`, data, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  },

  updateMaintenance: (id: string, data: UpdateMaintenanceRequestDTO) => {
    return axiosClient.put(`${MAINTANANCE_ENDPOINT}/request/${id}`, data);
  },

  deleteMaintenance: (id: string) => {
    return axiosClient.delete(`${MAINTANANCE_ENDPOINT}/request/${id}`);
  },
};
