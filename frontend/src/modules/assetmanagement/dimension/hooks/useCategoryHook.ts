import { useQuery } from '@tanstack/react-query';
import { getCategoryOptions } from '../queries/categoryActions';

export const useGetCategoryOptions = () => {
  return useQuery({
    queryKey: ['categoryOptions'],
    queryFn: async () => {
      const res = await getCategoryOptions();
      return res;
    },
    staleTime: Infinity,
  });
};
