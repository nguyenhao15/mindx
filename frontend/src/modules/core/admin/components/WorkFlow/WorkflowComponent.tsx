import React, { useMemo, useState } from 'react';
import { useGetWorkflowPage } from '../../hooks/useWorkFlowHook';
import type {
  FilterInput,
  FilterWithPaginationInput,
} from '@/validations/filterWithPagination';
import FilterComponent from './FilterComponent';
import { FILTER_CONFIGS } from './data/data';
import ModalComponent from '@/components/shared/ModalComponent';
import WorkFlowForm from './Form/WorkFlowForm';
import { Workflow } from 'lucide-react';
import UseAdminLayout from '../content/UseAdminLayout';
import { safeString, toArray } from '@/utils/formatValue';
import type { Column } from '@/components/shared/DataTable';
import Status from '@/components/shared/Status';
import UpdateWorkFlowForm from './Form/UpdateWorkFlowForm';

const WorkflowComponent = () => {
  const [openModal, setOpenModal] = useState(false);
  const [editMode, setEditMode] = useState(false);
  const [selectedRow, setSelectedRow] = useState<any>(null);
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

  const handleOpenModal = () => {
    setOpenModal(true);
  };

  const handleCloseModal = () => {
    setOpenModal(false);
  };

  const handleSelectRow = (row: any) => {
    handleOpenModal();
    setEditMode(true);
    setSelectedRow(row);
  };

  const { content, ...rest } = data || {};

  const rows = useMemo(() => {
    const source = toArray(content);
    return source.map((item) => {
      const record = item as Record<string, unknown>;

      return {
        id: safeString(record.id),
        module: safeString(record.module),
        labelName: safeString(record.labelName),
        fromStatus: safeString(record.fromStatus),
        toStatus: safeString(record.toStatus),
        activeStatus: record.enabled ? 'Active' : 'Inactive',
        enabled: record.enabled,
        ...record,
      };
    });
  }, [content]);

  const columns: Column<any>[] = [
    {
      key: 'labelName',
      label: 'Label',
    },
    {
      key: 'module',
      label: 'Module',
    },
    {
      key: 'fromStatus',
      label: 'From Status',
    },
    {
      key: 'toStatus',
      label: 'To Status',
    },
    {
      key: 'activeStatus',
      label: 'Status',
      render: (item) => <Status status={item.activeStatus} />,
    },
  ];

  return (
    <div className='flex flex-col gap-2 '>
      <div className='flex gap-4'>
        <FilterComponent
          configs={FILTER_CONFIGS}
          onFilterChange={handleUpdateFilter}
        />
      </div>
      <UseAdminLayout
        moduleTitle='Workflow'
        moduleDescription='Quản lý quy trình phê duyệt'
        isLoading={isLoading}
        rows={rows}
        columns={columns}
        handlePageChange={handlePageChange}
        pagination={rest}
        onCtaClick={handleOpenModal}
        handleEdit={handleSelectRow}
        ModuleIcon={Workflow}
        ctaLabel='Thêm mới'
        // openAddNewModal={openModal}
        // onCloseAddNewModal={handleCloseModal}
      />
      <ModalComponent open={openModal} onClose={handleCloseModal}>
        {/* Nội dung modal */}
        {editMode && selectedRow ? (
          <UpdateWorkFlowForm
            initialValues={selectedRow}
            editMode={editMode}
            afterSubmit={handleCloseModal}
          />
        ) : (
          <WorkFlowForm afterSubmit={handleCloseModal} />
        )}
      </ModalComponent>
    </div>
  );
};

export default WorkflowComponent;
