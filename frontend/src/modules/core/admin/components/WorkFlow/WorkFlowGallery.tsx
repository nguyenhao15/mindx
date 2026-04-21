import { DataTable, type Column } from '@/components/shared/DataTable';
import Loader from '@/components/shared/Loader';
import Status from '@/components/shared/Status';
import { safeString, toArray } from '@/utils/formatValue';
import { useMemo } from 'react';

interface WorkFlowGalleryProps {
  isLoading?: boolean;
  data: unknown[];
  pagination: Record<string, unknown>;
  onPageChange: (page: number) => void;
}

const WorkFlowGallery = ({
  isLoading,
  data,
  pagination,
  onPageChange,
}: WorkFlowGalleryProps) => {
  const rows = useMemo(() => {
    const source = toArray(data);
    return source.map((item) => {
      const record = item as Record<string, unknown>;

      return {
        id: safeString(record.id),
        module: safeString(record.module),
        labelName: safeString(record.labelName),
        fromStatus: safeString(record.fromStatus),
        toStatus: safeString(record.toStatus),
        enabled: record.enabled ? 'Active' : 'Inactive',
      };
    });
  }, [data]);

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
      key: 'enabled',
      label: 'Status',
      render: (item) => <Status status={item.enabled} />,
    },
  ];

  if (isLoading) {
    return <Loader />;
  }
  return (
    <div>
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

export default WorkFlowGallery;
