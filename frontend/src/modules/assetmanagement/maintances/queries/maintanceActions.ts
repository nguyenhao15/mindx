import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';
import { mainteanceApi } from '../api/maintenanceApi';
import {
  MaintenanceSumarySchemaArray,
  type UpdateMaintenanceRequestDTO,
} from '../schema/maintenaceSchema';

export const createMaintanceAction = (data: FormData) => {
  return mainteanceApi.createMaintenance(data);
};

export const getMaintanceAction = async (
  filterInput: FilterWithPaginationInput,
) => {
  const response = await mainteanceApi.getMaintenance(filterInput);
  const { content, ...rest } = response.data;
  const results = MaintenanceSumarySchemaArray.safeParse(content);
  if (!results.success) {
    console.warn('API Data Mismatch:', results.error.format());
    return response.data;
  }
  const data = { ...rest, content: results.data };

  return data;
};

export const getMaintanceDetailById = async (assetId: number) => {
  const res = await mainteanceApi.getMaintenances(assetId);
  return res.data;
};

export const updateMaintanceAction = (
  id: string,
  data: UpdateMaintenanceRequestDTO,
) => {
  return mainteanceApi.updateMaintenance(id, data);
};

export const deleteMaintanceAction = (id: number) => {
  return mainteanceApi.deleteMaintenance(id);
};
