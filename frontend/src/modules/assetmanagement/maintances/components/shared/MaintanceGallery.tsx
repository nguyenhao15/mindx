import { EmptyState } from '@/components/shared/EmtyState';
import ErrorCatchComponent from '@/components/shared/ErrorCatchComponent';
import Loader from '@/components/shared/Loader';
import { ClipboardList } from 'lucide-react';
import MaintanceCard from './MaintanceCard';

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
      {data?.content?.map((item: any) => (
        <MaintanceCard key={item.id} item={item} />
      ))}
    </div>
  );
};

export default MaintanceGallery;
