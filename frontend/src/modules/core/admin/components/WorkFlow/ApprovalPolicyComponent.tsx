import React, { useState } from 'react';
import { useGetApprovalPolicyPage } from '../../hooks/useApprovalPolicy';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';
import ApprovalPolicyGallery from './ApprovalPolicyGallery';
import ModalComponent from '@/components/shared/ModalComponent';
import { Button } from '@/components/ui/button';
import ApprovalPolicyForm from './Form/ApprovalPolicyForm';

const ApprovalPolicyComponent = () => {
  const [openModal, setOpenModal] = useState(false);
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
            property: 'targetStatus',
            direction: 'ASC',
          },
        ],
      },
    });
  const { data, isLoading } = useGetApprovalPolicyPage(filterWithPagination);

  const handlePageChange = (page: number) => {
    setFilterWithPagination((prev) => ({
      ...prev,
      pagination: {
        ...prev.pagination,
        page,
      },
    }));
  };

  const handleOpenModal = () => {
    setOpenModal(true);
  };

  const handleCloseModal = () => {
    setOpenModal(false);
  };

  const { content, ...rest } = data || {};

  return (
    <div>
      <div className='flex justify-between items-center'>
        <Button
          onClick={handleOpenModal}
          variant='outline'
          size='sm'
          className='ml-auto cursor-pointer'
        >
          Thêm mới
        </Button>
      </div>
      <ApprovalPolicyGallery
        isLoading={isLoading}
        data={content || []}
        pagination={rest}
        onPageChange={handlePageChange}
      />
      <ModalComponent open={openModal} onClose={handleCloseModal}>
        <ApprovalPolicyForm afterSubmit={handleCloseModal} />
      </ModalComponent>
    </div>
  );
};

export default ApprovalPolicyComponent;
