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
  const results = MaintenanceSumarySchemaArray.safeParse(response.data.content);
  if (!results.success) {
    console.warn('API Data Mismatch:', results.error.format());
    return response.data;
  }
  const data = { ...response.data, content: results.data };
  return data;
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
