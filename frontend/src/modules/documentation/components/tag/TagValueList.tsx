import { useGetProcessTagValues } from '@/modules/documentation/hookQueries/useProcessTagValueHooks';
import { safeString, toArray } from '@/utils/formatValue';
import { useMemo, useState } from 'react';
import { DataTable, type Column } from '../shared/DataTable';
import { ActionHeader } from '../shared/ActionHeder';
import { EmptyState } from '../shared/EmtyState';
import { BadgeCheckIcon } from 'lucide-react';
import Loader from '../shared/Loader';
import { DialogComponent } from '../shared/DialogComponent';
import CreateTagValueForm from './CreateTagValueForm';
import { useTypeQueryState } from '@/hooks/useTypeQueryState';
import { useDebouncedFilterSearch } from '@/utils/utilActions';

type TagValueRow = {
  id: string | number;
  tagTitle: string;
  tagValueCode: string;
  description: string;
  tagToString: string;
  tagItems: object[];
  active: boolean | string;
  status: string;
  createdBy: string;
};

const TagValueList = () => {
  const { state, updateState } = useTypeQueryState();
  const [openModal, setOpenModal] = useState(false);
  const { data, isLoading } = useGetProcessTagValues(state);
  const [selectedItem, setSelectedItem] = useState<TagValueRow | null>(null);

  const { content, ...rest } = data || {};

  const rows = useMemo<TagValueRow[]>(() => {
    const source = toArray(content);

    return source.map((item, index) => {
      const record = item as Record<string, unknown>;
      const tagItems = toArray(record.tagItems)
        .map((tag) => safeString((tag as Record<string, unknown>).tagName, ''))
        .filter(Boolean)
        .join(', ');

      const isActive = Boolean(record.active);

      return {
        id: safeString(
          record.id ?? record._id ?? record.tagTitle,
          String(index + 1),
        ),
        tagValueCode: safeString(record.tagValueCode),
        tagTitle: safeString(record.tagTitle),
        description: safeString(record.description),
        tagToString: safeString(tagItems),
        tagItems: toArray(record.tagItems),
        active: safeString(record.active),
        status: isActive ? 'ACTIVE' : 'INACTIVE',
        createdBy: safeString(record.createdBy),
      };
    });
  }, [content]);

  const columns: Column<TagValueRow>[] = [
    {
      key: 'tagTitle',
      label: 'Tag Value',
      render: (item) => (
        <span className='font-medium text-slate-900'>{item.tagTitle}</span>
      ),
    },
    {
      key: 'tagToString',
      label: 'Linked Tags',
    },
    {
      key: 'description',
      label: 'Description',
    },
    {
      key: 'createdBy',
      label: 'Created By',
    },
  ];

  const { handleSearch } = useDebouncedFilterSearch({
    updateState,
    field: 'tagTitle',
    operator: 'LIKE',
  });

  const selectItem = (item: any) => {
    const handleItem = {
      ...item,
      tagItems: item.tagItems.map((tag: any) => ({
        value: tag.id,
        ...tag,
      })),
    };

    setOpenModal(true);

    setSelectedItem(handleItem);
  };

  const handlePageChange = (page: number) => {
    updateState((prv) => ({
      ...prv,
      pagination: {
        ...prv.pagination,
        page,
      },
    }));
  };

  return (
    <div className='animate-in fade-in duration-300'>
      <ActionHeader
        title='Tag Value Management'
        searchPlaceholder='Search tag values...'
        ctaLabel='Create Tag Value'
        onSearch={handleSearch}
        onCtaClick={() => setOpenModal(true)}
      />

      {isLoading ? (
        <Loader text='Đang tải dữ liệu' />
      ) : rows.length === 0 ? (
        <EmptyState
          title='No Tag Values Found'
          description='No tag values match your keyword. Try another search or create a new value.'
          icon={BadgeCheckIcon}
        />
      ) : (
        <DataTable
          onEdit={selectItem}
          columns={columns}
          data={rows}
          pagination={rest}
          handlePageChange={handlePageChange}
        />
      )}
      <DialogComponent
        title='Tạo mới'
        open={openModal}
        onClose={() => {
          setOpenModal(false);
          setSelectedItem(null);
        }}
      >
        <CreateTagValueForm
          updateMode={!!selectedItem}
          initialData={selectedItem}
          onSubmitAction={() => {
            setOpenModal(false);
            setSelectedItem(null);
          }}
        />
      </DialogComponent>
    </div>
  );
};

export default TagValueList;
