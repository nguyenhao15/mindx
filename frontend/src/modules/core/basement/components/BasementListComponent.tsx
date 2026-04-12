import { useGetAllBasements } from '@/modules/core/basement/hooks/useBasementHook';
import { toArray } from '@/utils/formatValue';
import type { BasementResponse } from '@/modules/core/basement/schema/basementSchema';
import { useMemo, useState } from 'react';
import { DataTable, type Column } from '@/components/shared/DataTable';
import { ActionHeader } from '@/components/shared/ActionHeder';
import Loader from '@/components/shared/Loader';
import { EmptyState } from '@/components/shared/EmtyState';
import { BriefcaseBusiness } from 'lucide-react';
import ModalComponent from '@/components/shared/ModalComponent';
import Status from '@/components/shared/Status';
import { useTypeQueryState } from '@/hooks/useTypeQueryState';
import { useDebouncedFilterSearch } from '@/utils/utilActions';
import BasementForm from './BasementForm';

const BasementListComponent = () => {
  const { state, updateState } = useTypeQueryState();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedItem, setSelectedItem] = useState<BasementResponse | null>(
    null,
  );

  const handlePageChange = (pageValue: number) => {
    updateState((prev) => ({
      ...prev,
      pagination: {
        ...prev.pagination,
        page: pageValue, // Convert to 0-based index for API
      },
    }));
  };

  const { handleSearch: handleOnSearch } = useDebouncedFilterSearch({
    updateState,
    field: 'buFullName',
    operator: 'LIKE',
  });

  const { data: basements, isLoading, error } = useGetAllBasements(state);

  const { data, ...rest } = basements || {};

  const onEditAction = (item: BasementResponse) => {
    const itemFormatted = {
      ...item,
      active: item.active ? 'true' : 'false',
    };
    setIsModalOpen(true);
    setSelectedItem(itemFormatted);
  };

  const afterUpdate = () => {
    setIsModalOpen(false);
    setSelectedItem(null);
  };

  const rows = useMemo<BasementResponse[]>(() => {
    const source = toArray(basements);

    return source.map((item) => {
      const record = item as Record<string, any>;
      return {
        id: record._id,
        ...item,
      };
    });
  }, [basements]);

  const columns: Column<BasementResponse>[] = [
    {
      key: 'buFullName',
      label: 'BU Name',
      render: (item) => (
        <span className='font-medium text-slate-900'>{item.buFullName}</span>
      ),
    },
    {
      key: 'buId',
      label: 'BU Short Name',
    },
    {
      key: 'active',
      label: 'Status',
      render: (item) => <Status status={item.active ? 'Active' : 'Inactive'} />,
    },
    {
      key: 'areaFullName',
      label: 'Khu vực',
    },
  ];

  return (
    <div className='animate-in fade-in duration-300'>
      <ActionHeader
        title='Cơ sở'
        searchPlaceholder='Search basement...'
        ctaLabel='Add Basement'
        onSearch={handleOnSearch}
        onCtaClick={() => setIsModalOpen(true)}
      />
      {isLoading ? (
        <Loader text='Đang tải dữ liệu' />
      ) : rows.length === 0 ? (
        <EmptyState
          title='No Departments Found'
          description='No departments match your keyword. Try another search to continue.'
          icon={BriefcaseBusiness}
        />
      ) : (
        <DataTable
          onEdit={onEditAction}
          columns={columns}
          data={rows}
          actionLabel='Update'
          pagination={rest}
          handlePageChange={handlePageChange}
        />
      )}
      <ModalComponent
        open={isModalOpen}
        onClose={() => {
          setIsModalOpen(false);
          setSelectedItem(null);
        }}
      >
        <BasementForm
          updateMode={!!selectedItem}
          initialValues={selectedItem || {}}
          onSubmit={afterUpdate}
        />
      </ModalComponent>
    </div>
  );
};

export default BasementListComponent;
