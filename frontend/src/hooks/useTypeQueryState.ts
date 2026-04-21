import {
  FilterWithPagination,
  type FilterWithPaginationInput,
} from '@/validations/filterWithPagination';
import { useState } from 'react';

export const useTypeQueryState = () => {
  const [state, setState] = useState<FilterWithPaginationInput>(() => {
    const parsed = FilterWithPagination.parse({
      filters: [{ field: 'id', operator: 'LIKE', value: '' }],
      pagination: {
        page: 0,
        size: 12,
        sorts: [{ property: 'id', direction: 'ASC' }],
      },
    });
    return parsed;
  });

  const updateState = (
    update: (prev: FilterWithPaginationInput) => FilterWithPaginationInput,
  ) => {
    setState((prev) => {
      const nextState = update(prev);

      return FilterWithPagination.parse(nextState);
    });
  };

  return { state, updateState };
};
