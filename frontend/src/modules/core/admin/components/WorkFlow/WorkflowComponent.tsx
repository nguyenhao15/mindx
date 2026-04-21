import React, { useState } from 'react';
import { useGetWorkflowPage } from '../../hooks/useWorkFlowHook';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';
import WorkFlowGallery from './WorkFlowGallery';

import { Button } from '@/components/ui/button';
import { ComboboxComponent } from '@/components/input-elements/ComboboxComponent';

const WorkflowComponent = () => {
  const [filterWithPagination, setFilterWithPagination] =
    useState<FilterWithPaginationInput>({
      filters: [],
      pagination: {
        page: 0,
        size: 12,
        sorts: [
          {
            property: 'module',
            direction: 'ASC',
          },
          {
            property: 'fromStatus',
            direction: 'ASC',
          },
        ],
      },
    });
  const { data, isLoading } = useGetWorkflowPage(filterWithPagination);

  const handleUpdateFilter = (
    field: string,
    value: any,
    operator: string = 'LIKE',
  ) => {
    setFilterWithPagination((prev: any) => {
      // 1. Lọc bỏ filter cũ của field này (để tránh bị trùng lặp)
      const otherFilters = prev.filters.filter((f: any) => f.field !== field);

      // 2. Nếu value rỗng, ta chỉ cần giữ lại các filter khác (coi như xóa filter này)
      // Nếu có value, ta thêm filter mới vào mảng
      const newFilters = value
        ? [...otherFilters, { field, operator, value }]
        : otherFilters;

      return {
        ...prev,
        filters: newFilters,
        pagination: { ...prev.pagination, page: 0 }, // Reset về trang 0 khi lọc
      };
    });
  };

  const handlePageChange = (page: number) => {
    setFilterWithPagination((prev) => ({
      ...prev,
      pagination: {
        ...prev.pagination,
        page,
      },
    }));
  };

  const { content, ...rest } = data || {};

  return (
    <div className='flex flex-col gap-2 '>
      <div className='flex justify-between items-center'>
        <div className='flex gap-2 p-2 max-w-96'>
          <ComboboxComponent
            options={[
              { label: 'Sửa chữa', value: 'MAINTENANCE' },
              { label: 'Module B', value: 'moduleB' },
              { label: 'Module C', value: 'moduleC' },
            ]}
            placeholder='Filter by module'
            isMultiple
            label='Module'
            onChange={(value) => handleUpdateFilter('module', value, 'IN')}
            defaultValue={
              filterWithPagination.filters.find((f) => f.field === 'module')
                ?.value || ''
            }
          />
          <ComboboxComponent
            options={[
              { label: 'Đang chờ', value: 'WAITING' },
              { label: 'Đang xử lý', value: 'APPROVED' },
              { label: 'Đã sửa', value: 'FINISHED' },
            ]}
            placeholder='Filter by fromStatus'
            label='From Status'
            onChange={(value) => handleUpdateFilter('fromStatus', value)}
            defaultValue={
              filterWithPagination.filters.find((f) => f.field === 'fromStatus')
                ?.value || ''
            }
          />
        </div>
        <Button variant='outline' size='sm' className='ml-auto'>
          Thêm mới
        </Button>
      </div>
      <WorkFlowGallery
        isLoading={isLoading}
        data={content}
        pagination={rest}
        onPageChange={handlePageChange}
      />
    </div>
  );
};

export default WorkflowComponent;
