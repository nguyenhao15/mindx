import { BASEMENT_ENDPOINT } from '@/modules/documentations/constants/api-endpoint';
import axiosClient from '@/lib/axiosClient';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';

export const basementApi = {
  getAllBasements: async (payload: FilterWithPaginationInput) => {
    const response = await axiosClient.post(
      `${BASEMENT_ENDPOINT}/get-all`,
      payload,
    );
    return response;
  },

  getActiveBasements: async () => {
    const response = await axiosClient.get(`${BASEMENT_ENDPOINT}/active-bus`);
    return response;
  },

  getBasementById: async (id: string) => {
    const response = await axiosClient.get(`${BASEMENT_ENDPOINT}/${id}`);
    return response;
  },
  createBasement: async (data: any) => {
    const response = await axiosClient.post(BASEMENT_ENDPOINT, data);
    return response;
  },
  updateBasement: async (id: string, data: any) => {
    const response = await axiosClient.patch(
      `${BASEMENT_ENDPOINT}/${id}`,
      data,
    );
    return response;
  },
  deleteBasement: async (id: string) => {
    const response = await axiosClient.delete(`${BASEMENT_ENDPOINT}/${id}`);
    return response;
  },
};
