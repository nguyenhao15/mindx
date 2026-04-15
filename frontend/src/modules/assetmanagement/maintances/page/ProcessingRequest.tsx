import { ClipboardList, Plus } from 'lucide-react';
import { useState } from 'react';
import { useGetMaintenances } from '../hooks/useMaintenanceHooks';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';
import MaintanceGallery from '../components/shared/MaintanceGallery';
import { Link } from 'react-router-dom';

const ProcessingRequest = () => {
  const [filterInput] = useState<FilterWithPaginationInput>({
    filters: [],
    pagination: {
      page: 0,
      size: 10,
      sortOrder: [
        {
          property: 'createdAt',
          direction: 'DESC',
        },
      ],
    },
  });

  const { data, isLoading, error } = useGetMaintenances(filterInput);
  return (
    <div className='flex flex-col gap-6'>
      <div className='flex items-center justify-between'>
        <div className='flex items-center gap-2 text-slate-500'>
          <ClipboardList size={18} />
          <span className='text-sm'>Tất cả yêu cầu bảo trì</span>
        </div>
        <Link
          to='/assets/maintance/create'
          className='flex items-center gap-2 bg-[#1d3557] text-white px-4 py-2.5 rounded-xl hover:bg-[#162840] transition text-sm font-medium'
        >
          Tạo yêu cầu mới
          <Plus size={16} />
        </Link>
      </div>

      <div className='bg-white rounded-2xl border border-slate-100 overflow-hidden'>
        <div className='p-6 border-b border-slate-100'>
          <h2 className='font-semibold text-slate-800'>
            Danh sách yêu cầu bảo trì
          </h2>
        </div>
        <div className='flex flex-col items-center justify-center py-2 px-4 text-slate-400 gap-3'>
          <MaintanceGallery
            data={data || []}
            isLoading={isLoading}
            error={error}
          />
        </div>
      </div>
    </div>
  );
};

export default ProcessingRequest;
