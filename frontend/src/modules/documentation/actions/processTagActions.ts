import { processTagApi } from '@/modules/documentation/api/processTag';
import type { FilterWithPaginationInput } from '@/modules/documentation/validations/filterWithPagination';
import {
  TagResponseSchema,
  type TagDTO,
} from '@/modules/documentation/validations/tagSchema';

export const getActiveProcessTag = async () => {
  const response = await processTagApi.getActiveProcessTag();
  const result = TagResponseSchema.safeParse(response.data);
  if (!result.success) {
    console.warn('API Data Mismatch:', result.error.format());
    return response.data;
  }
  return result.data;
};

export const getProcessTag = async (payload: FilterWithPaginationInput) => {
  const response = await processTagApi.getAllProcessTag(payload);
  const result = TagResponseSchema.safeParse(response.data.content);
  if (!result.success) {
    console.warn('API Data Mismatch:', result.error.format());
    return response.data;
  }
  const finalPayload = {
    ...response.data,
    content: result.data,
  };
  return finalPayload;
};

export const createProcessTag = async (data: TagDTO) => {
  const response = await processTagApi.createNewTag(data);
  return response.data;
};

export const updateProcessTag = async (id: string, data: TagDTO) => {
  const response = await processTagApi.updateTag(id, data);
  return response.data;
};
