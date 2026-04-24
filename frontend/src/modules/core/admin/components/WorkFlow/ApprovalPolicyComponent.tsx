import { useMemo, useState } from 'react';
import { useGetApprovalPolicyPage } from '../../hooks/useApprovalPolicy';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';

import ModalComponent from '@/components/shared/ModalComponent';

import ApprovalPolicyForm from './Form/ApprovalPolicyForm';
import Status from '@/components/shared/Status';
import { safeString, toArray } from '@/utils/formatValue';
import UseAdminLayout from '../content/UseAdminLayout';
import { Shield } from 'lucide-react';
import type { Column } from '@/components/shared/DataTable';

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

  const rows = useMemo(() => {
    const source = toArray(data);
    return source.map((item) => {
      const record = item as Record<string, unknown>;

      const requesterHandleValue = safeString(record.requesterPosition);
      const applyPositions =
        requesterHandleValue.length > 1
          ? requesterHandleValue
          : 'Áp dụng cho tất cả vị trí';

      return {
        id: safeString(record.id),
        module: safeString(record.module),
        allowType: safeString(record.allowType),
        allowValue: safeString(record.allowValue),
        applyPositions: applyPositions,
        activeStatus: record.isActive ? 'Active' : 'Inactive',
        targetStatus: safeString(record.targetStatus),
        ...record,
      };
    });
  }, [data]);

  const columns: Column<any>[] = [
    {
      key: 'module',
      label: 'Module',
    },
    {
      key: 'allowType',
      label: 'Allow Type',
    },
    {
      key: 'allowValue',
      label: 'Allow Value',
    },
    {
      key: 'targetStatus',
      label: 'Target Status',
    },
    {
      key: 'applyPositions',
      label: 'Apply Positions',
    },
    {
      key: 'activeStatus',
      label: 'Status',
      render: (item) => <Status status={item.activeStatus} />,
    },
  ];

  const handleOpenModal = () => {
    setOpenModal(true);
  };

  const handleCloseModal = () => {
    setOpenModal(false);
  };

  const { content, ...rest } = data || {};

  return (
    <div>
      <UseAdminLayout
        moduleTitle='Approval Policy'
        moduleDescription='Quản lý các chính sách phê duyệt cho các module khác nhau trong hệ thống.'
        columns={columns}
        rows={rows}
        ctaLabel='Thêm mới'
        handlePageChange={handlePageChange}
        pagination={rest.pagination as Record<string, unknown>}
        isLoading={isLoading}
        onCtaClick={handleOpenModal}
        ModuleIcon={Shield}
        handleEdit={handleOpenModal}
      />
      <ModalComponent open={openModal} onClose={handleCloseModal}>
        <ApprovalPolicyForm afterSubmit={handleCloseModal} />
      </ModalComponent>
    </div>
  );
};

export default ApprovalPolicyComponent;
