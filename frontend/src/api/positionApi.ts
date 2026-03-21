import { POSITION_ENDPOINT } from '@/constants/api-endpoint';
import axiosClient from '@/lib/axiosClient';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';
import type { PositionFormData } from '@/validations/positionSchema';

export const positionApi = {
  createPosition: (data: PositionFormData) => {
    return axiosClient.post(POSITION_ENDPOINT, data);
  },

  getAllPositions: (payload: FilterWithPaginationInput) => {
    return axiosClient.post(`${POSITION_ENDPOINT}/get-all`, payload);
  },

  getPositionByActive: (active: boolean) => {
    return axiosClient.get(`${POSITION_ENDPOINT}/active/${active}`);
  },

  getPositionByDepartmentiD: (departmentCode: string) => {
    return axiosClient.get(`${POSITION_ENDPOINT}/department/${departmentCode}`);
  },

  getCurrentPosition: () => {
    return axiosClient.get(`${POSITION_ENDPOINT}/current`);
  },

  getPositionById: (id: string) => {
    return axiosClient.get(`${POSITION_ENDPOINT}/${id}`);
  },

  updatePosition: (id: string, data: PositionFormData) => {
    return axiosClient.patch(`${POSITION_ENDPOINT}/${id}`, data);
  },

  updatePositionById: (id: string, data: PositionFormData) => {
    return axiosClient.patch(`${POSITION_ENDPOINT}/${id}`, data);
  },

  deletePosition: (id: string) => {
    return axiosClient.delete(`${POSITION_ENDPOINT}/${id}`);
  },
};
