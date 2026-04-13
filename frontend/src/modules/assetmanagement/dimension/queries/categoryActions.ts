import { maintenanceCategoryApi } from '../api/maintanaceCategoryApi';

export const getCategoryOptions = async () => {
  const res = await maintenanceCategoryApi.getProviderCategoryOptions();
  return res.data;
};
