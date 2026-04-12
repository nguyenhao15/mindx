import { mainteanceApi } from '../api/maintenanceApi';
import type {
  CreateMaintenanceRequestDTO,
  UpdateMaintenanceRequestDTO,
} from '../schema/maintenaceSchema';

export const createMaintanceAction = (data: CreateMaintenanceRequestDTO) => {
  return mainteanceApi.createMaintenance(data);
};

export const updateMaintanceAction = (
  id: string,
  data: UpdateMaintenanceRequestDTO,
) => {
  return mainteanceApi.updateMaintenance(id, data);
};
