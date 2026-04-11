import { workingFieldApi } from '@/modules/documentation/api/workingFieldApi';
import {
  WorkingFieldResponseSchema,
  type WorkingFieldFormData,
} from '@/modules/documentation/validations/workingFieldSchema';

export const createWorkingFieldAction = async (data: WorkingFieldFormData) => {
  const response = await workingFieldApi.createWorkingField(data);
  return response.data;
};

export const getAllWorkingFieldListAction = async () => {
  const response = await workingFieldApi.getAllWorkingField();
  const results = WorkingFieldResponseSchema.safeParse(response.data);
  return results.success ? results.data : console.log('Is errrors: ');
};

export const getActiveWorkingFieldListAction = async () => {
  const response = await workingFieldApi.getActiveWorkingField();
  const results = WorkingFieldResponseSchema.safeParse(response.data);
  return results.success ? results.data : console.log('Is errrors: ');
};

export const updateWorkingFieldAction = async (
  id: string,
  data: WorkingFieldFormData,
) => {
  const response = await workingFieldApi.updateWorkingField(id, data);
  return response.data;
};

// Note: Delete method is not implemented because we want to keep the history of working fields for reporting and auditing purposes. Instead of deleting, we can set the 'active' status to false to indicate that the working field is no longer in use.
