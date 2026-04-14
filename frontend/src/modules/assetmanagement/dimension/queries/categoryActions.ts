import { maintenanceCategoryApi } from '../api/maintanaceCategoryApi';
import { MaintenanceCategoryInfoWithItemsArray } from '../schema/maintanceCategory';

export const getCategoryOptions = async () => {
  const res = await maintenanceCategoryApi.getProviderCategoryOptions();
  const results = MaintenanceCategoryInfoWithItemsArray.safeParse(res.data);

  if (!results.success) {
    console.warn('API Data Mismatch:', results.error.format());
    return res.data;
  }
  return results.data;
};
