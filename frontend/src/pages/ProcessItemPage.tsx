import { ActionHeader } from '@/components/shared/ActionHeder';
import FormSection from '@/components/shared/FormSection';
import HeaderPage from '@/components/shared/HeaderPage';
import Loader from '@/components/shared/Loader';
import TagFlowGallery from '@/components/processFlow/TagFlowGallery';
import { useGetDocumentByProcessing } from '@/hookQueries/useProcessFlowHooks';
import { useTypeQueryState } from '@/hooks/useTypeQueryState';
import { useDebouncedFilterSearch } from '@/utils/utilActions';
import { Workflow } from 'lucide-react';

const ProcessItemPage = () => {
  const { state, updateState } = useTypeQueryState();
  const { data, isLoading, error } = useGetDocumentByProcessing(state);

  const { content, ...rest } = data || {
    content: [],
    pagination: { page: 1, pageSize: 12, total: 0 },
  };

  const { handleSearch: handleOnSearch } = useDebouncedFilterSearch({
    updateState,
    field: 'title',
    delay: 300,
  });

  return (
    <div className='px-3 md:px-10 lg:px-30 py-2 h-full flex flex-col'>
      <HeaderPage
        title='Quy trình'
        subtitle='Danh sách các quy trình làm việc.'
        Icon={Workflow}
      />
      <FormSection headerLabel='Danh sách quy trình' icon={Workflow}>
        <div>
          <ActionHeader
            searchPlaceholder='Tìm kiếm quy trình...'
            title=''
            onSearch={handleOnSearch}
            ctaLabel='Thêm mới'
          />
          {isLoading && <Loader text='Đang tải dữ liệu...' />}
          {!isLoading && (
            <TagFlowGallery
              data={content}
              pagination={rest}
              isLoading={isLoading}
              payload={state}
              error={error}
              onUpdate={updateState}
            />
          )}
        </div>
      </FormSection>
    </div>
  );
};

export default ProcessItemPage;
