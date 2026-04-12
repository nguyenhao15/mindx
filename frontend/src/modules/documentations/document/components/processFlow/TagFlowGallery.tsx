import ProcessCard from './ProcessCard';
import { useNavigate } from 'react-router-dom';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';
import Loader from '@/components/shared/Loader';
import Pagination from '@/components/shared/Pagination';
import { TbArrowGuideFilled } from 'react-icons/tb';
import { EmptyState } from '@/components/shared/EmtyState';

interface TagFlowGalleryProps {
  data: any;
  isLoading: boolean;
  pagination: any;
  error: any;
  payload?: FilterWithPaginationInput;
  onUpdate?: (newFilters: FilterWithPaginationInput) => void;
}

const TagFlowGallery = ({
  data,
  isLoading,
  payload,
  pagination,
  onUpdate,
}: TagFlowGalleryProps) => {
  const navigate = useNavigate();

  const handleOnPageChange = (newPage: number) => {
    onUpdate &&
      onUpdate({
        filters: payload?.filters || [],
        pagination: { ...payload?.pagination, page: newPage },
      });
  };

  const handleOnCardClick = (item: (typeof data)[number]) => {
    navigate(`/documents/tag-flow/${item.id}`, {
      state: { processItem: item },
    });
  };

  if (isLoading) {
    return (
      <div className='flex items-center justify-center h-64'>
        <Loader />
      </div>
    );
  }

  if (!data || data.length === 0) {
    return (
      <EmptyState
        title='No active process flows'
        description='No active process flows found.'
        icon={TbArrowGuideFilled}
      />
    );
  }

  return (
    <div className='flex flex-col gap-2'>
      <div className='grid grid-cols-1 lg:grid-cols-2 2xl:grid-cols-3 gap-5 p-5 rounded-lg'>
        {data?.map((opt: any) => (
          <ProcessCard
            key={opt.id}
            data={opt}
            onClick={() => handleOnCardClick(opt)}
          />
        ))}
      </div>

      <Pagination
        paginationData={pagination}
        onPageChange={handleOnPageChange}
      />
    </div>
  );
};

export default TagFlowGallery;
