import { useState } from 'react';
import FilterArea from '../../../../../components/shared/FilterComponent';
import { Button } from '../../../../../components/ui/button';
import { useNavigate } from 'react-router-dom';
import { useTypeQueryState } from '@/hooks/useTypeQueryState';
import type { FilterInput } from '@/validations/filterWithPagination';
import { useGetActiveProcessFlows } from '@/modules/documentations/document/hooks/useProcessFlowHooks';
import { useDebouncedFilterSearch } from '@/utils/utilActions';
import TagFlowGallery from '@/modules/documentations/document/components/processFlow/TagFlowGallery';

const ACTION = [
  {
    label: 'Create New Document',
    value: 'create_new_document',
    variant: 'positive',
  },
];

const DocumentMasterPage = () => {
  const navigate = useNavigate();
  const [openDialog, setOpenDialog] = useState(false);
  const [action, setAction] = useState('');
  const { state, updateState } = useTypeQueryState();

  const handleSetFilters = (newFilters: FilterInput[]) => {
    updateState((prev) => ({ ...prev, filters: newFilters }));
  };

  const { handleSearch: handleSearchTitle } = useDebouncedFilterSearch({
    updateState,
    field: 'title',
    operator: 'LIKE',
  });

  const handleOpenDialog = (actionValue: string) => {
    setOpenDialog(true);
    switch (actionValue) {
      case 'create_tag':
        setAction('create_tag');
        break;
      case 'create_tag_value':
        setAction('create_tag_value');
        break;
      case 'create_new_document':
        // Trigger export functionality
        navigate('/create-document');
        break;
      default:
        break;
    }
  };

  const { data, isFetching, isLoading, error } = useGetActiveProcessFlows(
    state,
    true,
  );

  const { content, ...rest } = data || {
    content: [],
    pagination: { page: 1, pageSize: 12, total: 0 },
  };

  return (
    <div className='px-5 py-10 md:px-20 lg:px-40 bg-slate-50 dark:bg-slate-900 h-full'>
      <div>
        <h1 className='text-2xl font-bold mb-2 text-slate-800 dark:text-white'>
          Document Management
        </h1>
        <p className='text-slate-600 dark:text-slate-300 mb-4'>
          Manage your tags, tag values, and documents efficiently.
        </p>
      </div>

      <div className='flex flex-col gap-10 px-2 md:px-15 lg:px-40 py-2 bg-slate-50 dark:bg-slate-900 rounded  mb-4'>
        <FilterArea
          filters={state.filters}
          setFilters={handleSetFilters}
          searchKeyword={state.keyword}
          setSearchKeyword={handleSearchTitle}
          loading={false}
        />

        <div className='w-fit self-start grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4'>
          {ACTION.map((action) => (
            <Button
              key={action.value}
              variant={action.variant}
              className='w-full cursor-pointer'
              onClick={() => {
                handleOpenDialog(action.value);
              }}
            >
              {action.label}
            </Button>
          ))}
        </div>

        <TagFlowGallery
          data={content}
          pagination={rest}
          isLoading={isLoading || isFetching}
          payload={state}
          error={error}
          onUpdate={updateState}
        />
      </div>
    </div>
  );
};

export default DocumentMasterPage;
