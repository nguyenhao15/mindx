import { useGetProcessTag } from '@/modules/documentation/hookQueries/useProcessTagHooks';
import { safeString, toArray } from '@/utils/formatValue';
import type { TagInfo } from '@/modules/documentation/validations/tagSchema';
import { useMemo, useState } from 'react';
import { DataTable, type Column } from '../shared/DataTable';
import { TagsIcon } from 'lucide-react';
import { EmptyState } from '../shared/EmtyState';
import { ActionHeader } from '../shared/ActionHeder';
import Loader from '../shared/Loader';
import Status from '../shared/Status';
import CreateTagForm from './CreateTagForm';
import { DialogComponent } from '../shared/DialogComponent';
import { useTypeQueryState } from '@/hooks/useTypeQueryState';
import { useDebouncedFilterSearch } from '@/utils/utilActions';

const TagList = () => {
  const [openModal, setOpenModal] = useState(false);
  const [selectedItem, setSelectedItem] = useState<TagInfo | null>(null);
  const { state, updateState } = useTypeQueryState();
  const { data, isLoading } = useGetProcessTag(state);

  const handleUpdatePage = (page: number) => {
    updateState((prv) => ({
      ...prv,
      pagination: {
        ...prv.pagination,
        page,
      },
    }));
  };

  const { content, ...rest } = data || {};

  const rows = useMemo<TagInfo[]>(() => {
    const source = toArray(content);
    return source.map((item, index) => {
      const record = item as Record<string, unknown>;

      return {
        id: safeString(record.id, String(index + 1)),
        tagName: safeString(record.tagName),
        fullTagName: safeString(record.fullTagName),
        description: safeString(record.description),
        status: safeString(record.status),
        active: safeString(record.active),
      };
    });
  }, [content]);

  const columns: Column<TagInfo>[] = [
    {
      key: 'tagName',
      label: 'Tag Name',
      render: (item) => (
        <span className='font-medium text-slate-900'>{item.tagName}</span>
      ),
    },
    {
      key: 'fullTagName',
      label: 'Full Tag Name',
    },
    {
      key: 'description',
      label: 'Description',
    },
    {
      key: 'status',
      label: 'Status',
      render: (item) => <Status status={item.status} />,
    },
  ];

  const { handleSearch } = useDebouncedFilterSearch({
    updateState,
    field: 'tagName',
    operator: 'LIKE',
  });

  const updateItem = (item: TagInfo) => {
    setSelectedItem(item);
    setOpenModal(true);
  };

  const handleCloseModal = () => {
    setOpenModal(false);
    setSelectedItem(null);
  };

  return (
    <div className='animate-in fade-in duration-300'>
      <ActionHeader
        title='Tag Management'
        searchPlaceholder='Search tags...'
        ctaLabel='Create Tag'
        onSearch={handleSearch}
        onCtaClick={() => setOpenModal(true)}
      />

      {isLoading ? (
        <Loader text='Đang tải dữ liệu' />
      ) : rows.length === 0 ? (
        <EmptyState
          title='No Tags Found'
          description='No tags match your keyword. Try another search or create a new tag.'
          icon={TagsIcon}
        />
      ) : (
        <DataTable
          columns={columns}
          data={rows}
          actionLabel='Update'
          onEdit={updateItem}
          pagination={rest}
          handlePageChange={handleUpdatePage}
        />
      )}
      <DialogComponent
        title='Tạo mới'
        open={openModal}
        onClose={handleCloseModal}
      >
        <CreateTagForm
          updateMode={selectedItem !== null}
          initialData={selectedItem}
          onUpdateSuccess={handleCloseModal}
        />
      </DialogComponent>
    </div>
  );
};

export default TagList;
