import type {
  FilterInput,
  FilterWithPaginationInput,
} from '@/validations/filterWithPagination';
import React, { use, useMemo, useState } from 'react';
import { useGetPaginationStaffProfiles } from '../../humanResource/hooks/useStaffProfileHook';
import { safeString, toArray } from '@/utils/formatValue';
import type { Column } from '@/components/shared/DataTable';
import Status from '@/components/shared/Status';
import UseAdminLayout from '../components/content/UseAdminLayout';
import { Shield } from 'lucide-react';

const HumanResourcePage = () => {
  const [openModal, setOpenModal] = useState(false);
  const [filterWithPagination, setFilterWithPagination] =
    useState<FilterWithPaginationInput>({
      filters: [],
      pagination: {
        page: 0,
        size: 12,
        sorts: [],
      },
    });

  const { data, isLoading } =
    useGetPaginationStaffProfiles(filterWithPagination);

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

  const { content, ...rest } = data || {};

  const rows = useMemo(() => {
    const source = toArray(content);
    return source.map((item) => {
      const record = item as Record<string, unknown>;

      return {
        id: safeString(record.id),
        staffId: safeString(record.staffId),
        deprtName: safeString(record.departmentName),
        posName: safeString(record.positionName),
        toStatus: safeString(record.toStatus),
        activeStatus: record.active ? 'Active' : 'Inactive',
        enabled: record.enabled,
        ...record,
      };
    });
  }, [content]);

  const columns: Column<any>[] = [
    {
      key: 'staffId',
      label: 'Mã nhân viên',
    },
    {
      key: 'deprtName',
      label: 'Department Name',
    },
    {
      key: 'posName',
      label: 'Position Name',
    },

    {
      key: 'activeStatus',
      label: 'Status',
      render: (item) => <Status status={item.activeStatus} />,
    },
  ];

  return (
    <div className='flex flex-col gap-2 '>
      {/* <div className='flex gap-4'>
        <FilterComponent
          configs={FILTER_CONFIGS}
          onFilterChange={handleUpdateFilter}
        />
      </div> */}
      <UseAdminLayout
        moduleTitle='Human Resource'
        moduleDescription='Quản lý nhân sự'
        isLoading={isLoading}
        rows={rows}
        columns={columns}
        handlePageChange={handlePageChange}
        pagination={rest}
        onCtaClick={handleOpenModal}
        handleEdit={handleOpenModal}
        ModuleIcon={Shield}
        ctaLabel='Thêm mới'
        // openAddNewModal={openModal}
        // onCloseAddNewModal={handleCloseModal}
      />
    </div>
  );
};

export default HumanResourcePage;
