import { basementApi } from '@/modules/core/basement/api/basementApi';
import { BasementResponseArray } from '@/modules/core/basement/schema/basementSchema';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';

export const getActiveBuItems = async () => {
  const response = await basementApi.getActiveBasements();
  const results = BasementResponseArray.safeParse(response.data);
  if (!results.success) {
    console.warn('API Data Mismatch:', results.error.format());
    return response.data;
  }
  return results;
};

export const getAllBasements = async (payload: FilterWithPaginationInput) => {
  const response = await basementApi.getAllBasements(payload);
  const { content, ...rest } = response.data;
  const results = BasementResponseArray.safeParse(content);
  if (!results.success) {
    console.warn('API Data Mismatch:', results.error.format());
    return response.data;
  }
  const data = { ...rest, data: results.data };
  return data;
};

export const createBasement = async (data: any) => {
  const response = await basementApi.createBasement(data);
  return response.data;
};

export const updateBasement = async (id: string, data: any) => {
  const response = await basementApi.updateBasement(id, data);
  return response.data;
};
