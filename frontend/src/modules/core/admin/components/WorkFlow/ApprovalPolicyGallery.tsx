import { DataTable, type Column } from '@/components/shared/DataTable';
import Loader from '@/components/shared/Loader';
import Status from '@/components/shared/Status';
import { safeString, toArray } from '@/utils/formatValue';
import React, { useMemo } from 'react';

interface ApprovalPolicyGalleryProps {
  isLoading?: boolean;
  data: unknown[];
  pagination: Record<string, unknown>;
  onPageChange: (page: number) => void;
}

const ApprovalPolicyGallery = ({
  isLoading,
  data,
  pagination,
  onPageChange,
}: ApprovalPolicyGalleryProps) => {
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
        labelName: safeString(record.targetStatus),
        fromStatus: safeString(record.allowType),
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
  if (isLoading) {
    return <Loader />;
  }
  return (
    <div className='p-2'>
      <DataTable
        columns={columns}
        data={rows}
        actionLabel='Update'
        pagination={pagination}
        handlePageChange={onPageChange}
      />
    </div>
  );
};

export default ApprovalPolicyGallery;
