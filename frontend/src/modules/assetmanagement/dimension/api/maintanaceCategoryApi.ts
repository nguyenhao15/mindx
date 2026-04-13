import { MAINTENANCE_CATEGORY_ENDPOINT } from '@/constants/api-endpoint';
import axiosClient from '@/lib/axiosClient';

export const maintenanceCategoryApi = {
  getProviderCategoryOptions: () => {
    return axiosClient.get(
      `${MAINTENANCE_CATEGORY_ENDPOINT}/category/provider/form`,
    );
  },
};
