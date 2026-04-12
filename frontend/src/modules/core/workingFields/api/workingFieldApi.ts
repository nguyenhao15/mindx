import { WORKING_FIELD_ENDPOINT } from '@/modules/documentations/constants/api-endpoint';
import axiosClient from '@/lib/axiosClient';
import type { WorkingFieldFormData } from '@/modules/core/workingFields/schema/workingFieldSchema';

export const workingFieldApi = {
  createWorkingField: (data: WorkingFieldFormData) => {
    return axiosClient.post(WORKING_FIELD_ENDPOINT, data);
  },

  getAllWorkingField: () => {
    return axiosClient.get(WORKING_FIELD_ENDPOINT);
  },

  getActiveWorkingField: () => {
    return axiosClient.get(`${WORKING_FIELD_ENDPOINT}/active`);
  },

  updateWorkingField: (id: string, data: WorkingFieldFormData) => {
    return axiosClient.patch(`${WORKING_FIELD_ENDPOINT}/${id}`, data);
  },
  //   Delete method is not implemented because we want to keep the history of working fields for reporting and auditing purposes. Instead of deleting, we can set the 'active' status to false to indicate that the working field is no longer in use.
};
