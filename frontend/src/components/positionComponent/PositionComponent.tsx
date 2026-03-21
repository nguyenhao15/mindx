import { useGetPositions } from '@/hookQueries/usePositionHook';
import type { PositionResponse } from '@/validations/positionSchema';
import { useMemo, useState } from 'react';
import { safeString, toArray } from '@/utils/formatValue';
import { DataTable, type Column } from '../shared/DataTable';
import Status from '../shared/Status';
import { ActionHeader } from '../shared/ActionHeder';
import { BriefcaseBusiness } from 'lucide-react';
import { EmptyState } from '../shared/EmtyState';
import ModalComponent from '../shared/ModalComponent';
import Loader from '../shared/Loader';
import PositionFormComponent from './PositionFormComponent';
import { useTypeQueryState } from '@/hooks/useTypeQueryState';
import { useDebouncedFilterSearch } from '@/utils/utilActions';

const PositionComponent = () => {
  const { state, updateState } = useTypeQueryState();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const { data, isLoading } = useGetPositions(state);
  const { handleSearch } = useDebouncedFilterSearch({
    updateState,
    field: 'positionName',
    operator: 'LIKE',
  });

  const { content, ...pagination } = data || {};
  const [selectedItem, setSelectedItem] = useState<PositionResponse | null>(
    null,
  );

  const handlePageChange = (page: number) => {
    updateState((prv) => ({ ...prv, pagination: { ...prv.pagination, page } }));
  };

  const onEditAction = (item: PositionResponse) => {
    setIsModalOpen(true);
    setSelectedItem(item);
  };

  const afterUpdate = () => {
    setIsModalOpen(false);
    setSelectedItem(null);
  };

  const rows = useMemo<PositionResponse[]>(() => {
    const source = toArray<Record<string, any>>(content);
    return source.map((item) => ({
      ...item,
      id: safeString(item.id),
      positionName: safeString(item.positionName),
      positionCode: safeString(item.positionCode),
      description: safeString(item.description),
      departmentCode: safeString(item.departmentCode),
      positionLevel: Number(item.positionLevel),
      active: Boolean(item.active),
    }));
  }, [content]);

  const columns: Column<PositionResponse>[] = [
    {
      key: 'positionName',
      label: 'Position Name',
      render: (item) => (
        <span className='font-medium text-slate-900'>{item.positionName}</span>
      ),
    },
    {
      key: 'positionCode',
      label: 'Position Code',
    },
    {
      key: 'active',
      label: 'Status',
      render: (item) => <Status status={item.active ? 'ACTIVE' : 'INACTIVE'} />,
    },
    {
      key: 'description',
      label: 'Description',
    },
  ];

  return (
    <div className='animate-in fade-in duration-300'>
      <ActionHeader
        title='Positions'
        searchPlaceholder='Search positions...'
        ctaLabel='Add Position'
        onSearch={handleSearch}
        onCtaClick={() => setIsModalOpen(true)}
      />
      {isLoading ? (
        <Loader text='Đang tải dữ liệu' />
      ) : rows.length === 0 ? (
        <EmptyState
          title='No Positions Found'
          description='No positions match your keyword. Try another search to continue.'
          icon={BriefcaseBusiness}
        />
      ) : (
        <DataTable
          onEdit={onEditAction}
          columns={columns}
          data={rows}
          pagination={pagination}
          handlePageChange={handlePageChange}
          actionLabel='Update'
        />
      )}
      <ModalComponent
        open={isModalOpen}
        onClose={() => {
          setIsModalOpen(false);
          setSelectedItem(null);
        }}
      >
        <PositionFormComponent
          initialData={selectedItem || undefined}
          onUpdate={afterUpdate}
        />
      </ModalComponent>
    </div>
  );
};

export default PositionComponent;
