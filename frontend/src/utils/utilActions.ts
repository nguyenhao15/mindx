import { useEffect, useRef } from 'react';
import type { FilterWithPaginationInput } from '@/modules/documentation/validations/filterWithPagination';

type UpdateState = (
  updater: (prev: FilterWithPaginationInput) => FilterWithPaginationInput,
) => void;

type UseDebouncedSearchParams = {
  updateState: UpdateState;
  field: string;
  delay?: number;
  operator?: 'EQUALS' | 'IN' | 'BETWEEN' | 'GTE' | 'LTE' | 'LIKE';
};

export const useDebouncedFilterSearch = ({
  updateState,
  field,
  delay = 300,
  operator = 'LIKE',
}: UseDebouncedSearchParams) => {
  const timeoutRef = useRef<number | null>(null);

  const handleSearch = (keyword: string) => {
    if (timeoutRef.current) {
      window.clearTimeout(timeoutRef.current);
    }

    timeoutRef.current = window.setTimeout(() => {
      updateState((prev) => {
        const nextFilters = prev.filters.filter((item) => item.field !== field);
        5;

        return {
          ...prev,
          filters: [
            ...nextFilters,
            {
              field,
              operator: operator,
              value: keyword,
            },
          ],
          pagination: {
            ...prev.pagination,
            page: 0,
          },
        };
      });
    }, delay);
  };

  useEffect(() => {
    return () => {
      if (timeoutRef.current) {
        window.clearTimeout(timeoutRef.current);
      }
    };
  }, []);

  return { handleSearch };
};
