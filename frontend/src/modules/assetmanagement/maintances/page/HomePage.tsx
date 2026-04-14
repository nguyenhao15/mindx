import { Link } from 'react-router-dom';
import { Plus, ClipboardList } from 'lucide-react';
import { useGetMaintenances } from '../hooks/useMaintenanceHooks';
import { useState } from 'react';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';

const HomePage = () => {
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

  const { data, isLoading } = useGetMaintenances(filterInput);
  const content = Array.isArray((data as any)?.content)
    ? (data as any).content
    : [];
  const hasData = content.length > 0;

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
          <Plus size={16} />
          Tạo yêu cầu mới
        </Link>
      </div>

      <div className='bg-white rounded-2xl border border-slate-100 overflow-hidden'>
        <div className='p-6 border-b border-slate-100'>
          <h2 className='font-semibold text-slate-800'>
            Danh sách yêu cầu bảo trì
          </h2>
        </div>
        <div className='flex flex-col items-center justify-center py-16 text-slate-400 gap-3'>
          <ClipboardList size={40} className='text-slate-200' />
          <p className='text-sm'>
            {isLoading
              ? 'Đang tải danh sách yêu cầu bảo trì...'
              : hasData
                ? 'Đã có dữ liệu yêu cầu bảo trì'
                : 'Chưa có yêu cầu bảo trì nào'}
          </p>
          <Link
            to='/assets/maintance/create'
            className='text-sm text-[#1d3557] font-medium hover:underline'
          >
            Tạo yêu cầu đầu tiên →
          </Link>
        </div>
      </div>
    </div>
  );
};

export default HomePage;
