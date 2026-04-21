import React, { useState } from 'react';
import { useGetWorkflowPage } from '../../hooks/useWorkFlowHook';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';
import WorkFlowGallery from './WorkFlowGallery';

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

  if (isLoading) {
    return <div>Loading...</div>;
  }
  return (
    <WorkFlowGallery
      data={content}
      pagination={rest}
      onPageChange={handlePageChange}
    />
  );
};

export default WorkflowComponent;
