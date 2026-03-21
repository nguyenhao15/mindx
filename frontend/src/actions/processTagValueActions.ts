import { processTagValueApi } from '@/api/processTagValue';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';

import {
  TagArrayResponseSchema,
  TagItemResponseSchema,
  type TagValueDTO,
} from '@/validations/tagValueSchema';

export const processTagValueActions = async (
  payload: FilterWithPaginationInput,
) => {
  const res = await processTagValueApi.getProcessTagValue(payload);
  const result = TagArrayResponseSchema.safeParse(res.data.content);
  if (!result.success) {
    console.warn('API Data Mismatch:', result.error.format());
    return res.data;
  }

  const finalPayload = {
    ...res.data,
    content: result.data,
  };

  return finalPayload;
};

export const createProcessTagValue = async (data: TagValueDTO) => {
  const response = await processTagValueApi.createProcessTagValue(data);
  const result = TagItemResponseSchema.safeParse(response.data);
  if (!result.success) {
    console.warn('API Data Mismatch:', result.error.format());
    return response.data;
  }
  return response.data;
};

export const updateProcessTagValue = async (id: string, data: TagValueDTO) => {
  const response = await processTagValueApi.updateTagValue(id, data);
  const result = TagItemResponseSchema.safeParse(response.data);
  if (!result.success) {
    console.warn('API Data Mismatch:', result.error.format());
    return response.data;
  }
  return response.data;
};

export const getActiveTagValueOptions = async () => {
  const response = await processTagValueApi.getActiveTagValueOptions();
  const result = TagArrayResponseSchema.safeParse(response.data);
  if (!result.success) {
    console.warn('API Data Mismatch:', result.error.format());
    return response.data;
  }
  return result.data;
};
