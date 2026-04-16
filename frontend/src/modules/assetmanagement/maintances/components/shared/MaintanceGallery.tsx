import { EmptyState } from '@/components/shared/EmtyState';
import ErrorCatchComponent from '@/components/shared/ErrorCatchComponent';
import Loader from '@/components/shared/Loader';
import { ClipboardList } from 'lucide-react';

import { useMemo } from 'react';
import type { MaintenanceSumaryResponse } from '../../schema/maintenaceSchema';
import {
  formatDateTime,
  formatPrice,
  safeString,
  toArray,
} from '@/utils/formatValue';
import { DataTable, type Column } from '@/components/shared/DataTable';
import Status from '@/components/shared/Status';
import { useNavigate } from 'react-router-dom';

interface MaintanceGalleryProps {
  data: any;
  isLoading: boolean;
  error: any;
}

const MaintanceGallery = ({
  data,
  isLoading,
  error,
}: MaintanceGalleryProps) => {
  const navigation = useNavigate();
  const { content, ...rest } = data || {};

  const handleOnEdit = (item: any) => {
    // Implement edit functionality here
    navigation(`/assets/maintance/detail/${item.id}`);
  };

  const rows = useMemo<MaintenanceSumaryResponse[]>(() => {
    const source = toArray<MaintenanceSumaryResponse>(content);
    return source.map((item: MaintenanceSumaryResponse, index: number) => {
      return {
        ...item,
        id: item.id || index, // Ensure there's an ID for each item
        description: safeString(item.description), // Handle null/undefined descriptions
      };
    });
  }, [content]);

  const columns: Column<MaintenanceSumaryResponse>[] = [
    {
      key: 'fixCategory.categoryTitle',
      label: 'Hạng mục bảo trì',
      render: (item) => (
        <span className='font-medium text-slate-900'>
          {item.fixCategory?.categoryTitle || 'N/A'}
        </span>
      ),
    },
    {
      key: 'locationId',
      label: 'Cơ sở',
    },
    {
      key: 'createdDate',
      label: 'Ngày tạo',
      render: (item) => formatDateTime(item.createdDate),
    },
    {
      key: 'totalCost',
      label: 'Tổng chi phí',
      render: (item) => formatPrice(item.totalCost),
    },
    {
      key: 'maintenancesStatus',
      label: 'Trạng thái',
      render: (item) => <Status status={item.maintenancesStatus} />,
    },
    {
      key: 'totalProposals',
      label: 'Tổng số phương án',
      render: (item) => item.totalProposals,
    },
  ];

  if (isLoading) {
    return <Loader text={'Loading maintenances...'} />;
  }

  if (error) {
    return <ErrorCatchComponent error={error} />;
  }

  if (!data || !data.content || data.content.length === 0) {
    return (
      <EmptyState
        title='No maintenances found'
        description='There are no maintenances available at the moment.'
        icon={ClipboardList}
      />
    );
  }

  return (
    <div className='bg-input-background p-2 shadow m-3 rounded-lg flex flex-row gap-4 overflow-x-auto w-full'>
      <DataTable
        columns={columns}
        data={rows}
        pagination={rest}
        onEdit={handleOnEdit}
      />
    </div>
  );
};

export default MaintanceGallery;
