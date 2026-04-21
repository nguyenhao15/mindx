import React, { useState } from 'react';
import { useGetWorkflowPage } from '../../hooks/useWorkFlowHook';
import type {
  FilterConfig,
  FilterInput,
  FilterWithPaginationInput,
} from '@/validations/filterWithPagination';
import WorkFlowGallery from './WorkFlowGallery';
import { Button } from '@/components/ui/button';
import FilterComponent from './FilterComponent';

const FILTER_CONFIGS: FilterConfig[] = [
  {
    field: 'module',
    label: 'Module',
    operator: 'LIKE',
    type: 'SELECT',
    options: [
      { label: 'Bảo trì', value: 'MAINTENANCE' },
      { label: 'Quản lý dự án', value: 'PROJECT' },
      { label: 'Quản lý tài chính', value: 'FINANCE' },
    ],
  },
  {
    field: 'fromStatus',
    label: 'Trạng thái bắt đầu',
    operator: 'LIKE',
    type: 'SELECT',
    options: [
      { label: 'Trạng thái 1', value: 'WAITING' },
      { label: 'Trạng thái 2', value: 'APPROVED' },
      { label: 'Trạng thái 3', value: 'REJECTED' },
    ],
  },
  {
    field: 'toStatus',
    label: 'Trạng thái kết thúc',
    operator: 'LIKE',
    type: 'SELECT',
    options: [
      { label: 'Trạng thái 1', value: 'WAITING' },
      { label: 'Trạng thái 2', value: 'APPROVED' },
      { label: 'Trạng thái 3', value: 'REJECTED' },
    ],
  },
  {
    field: 'id',
    label: 'ID',
    operator: 'EQUALS',
    type: 'TEXT',
  },
];

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

  const handleUpdateFilter = (value: FilterInput[]) => {
    setFilterWithPagination((prev: any) => {
      return {
        ...prev,
        filters: value,
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
        <Button variant='outline' size='sm' className='ml-auto'>
          Thêm mới
        </Button>
      </div>
      <div className='flex gap-4'>
        <FilterComponent
          configs={FILTER_CONFIGS}
          onFilterChange={handleUpdateFilter}
        />
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
