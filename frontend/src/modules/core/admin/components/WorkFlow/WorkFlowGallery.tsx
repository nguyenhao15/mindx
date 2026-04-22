import { DataTable, type Column } from '@/components/shared/DataTable';
import Loader from '@/components/shared/Loader';
import Status from '@/components/shared/Status';
import { safeString, toArray } from '@/utils/formatValue';
import { useMemo } from 'react';
import { useUpdateWorkflow } from '../../hooks/useWorkFlowHook';
import toast from 'react-hot-toast';

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
  const { mutateAsync, isPending } = useUpdateWorkflow();
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
        activeStatus: record.enabled ? 'Active' : 'Inactive',
        enabled: record.enabled,
        ...record,
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
      key: 'activeStatus',
      label: 'Status',
      render: (item) => <Status status={item.activeStatus} />,
    },
  ];

  const handleOnEdit = async (item: any) => {
    const updatedData = {
      ...item,
      enabled: !item.enabled, // Toggle the enabled status
    };
    console.log('Update data: ', updatedData);

    try {
      await mutateAsync({ id: item.id, data: updatedData });
      toast.success('Workflow updated successfully');
    } catch (error: any) {
      console.error('Error updating workflow:', error);
      toast.error(
        error?.response?.data?.message || 'Failed to update workflow',
      );
    }
  };

  if (isLoading) {
    return <Loader />;
  }
  return (
    <div className='p-2'>
      <DataTable
        columns={columns}
        data={rows}
        onEdit={handleOnEdit}
        actionLabel='Update'
        pagination={pagination}
        handlePageChange={onPageChange}
      />
    </div>
  );
};

export default WorkFlowGallery;
