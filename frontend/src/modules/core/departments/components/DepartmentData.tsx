import React from 'react';
import DepartmentCard from './DepartmentCard';
import { EmptyState } from '@/components/shared/EmtyState';
import { Laptop } from 'lucide-react';
import Pagination from '@/components/shared/Pagination';
import Loader from '@/components/shared/Loader';

interface DepartmentDataProps {
  data: any;
  onPageChange: (page: number) => void;
  isLoading: boolean;
  paginationData: {
    pageNumber: number;
    pageSize: number;
    totalElements: number;
    totalPages: number;
    lastPage: boolean;
  };
  onView: (data: any) => void;
}

const DepartmentData: React.FC<DepartmentDataProps> = ({
  data,
  onPageChange,
  isLoading,
  paginationData,
  onView,
}) => {
  return (
    <div>
      <div className='grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4'>
        {isLoading ? (
          <Loader text='Đang tải dữ liệu' />
        ) : data && data.length > 0 ? (
          data.map((dept: any) => (
            <DepartmentCard
              key={dept.id}
              data={dept}
              onView={(data) => onView(data)}
            />
          ))
        ) : (
          <div className='col-span-4'>
            <EmptyState
              title='Danh sách bộ phận'
              icon={Laptop}
              description='Không có bộ phận nào.'
            />
          </div>
        )}
      </div>
      <Pagination onPageChange={onPageChange} paginationData={paginationData} />
    </div>
  );
};

export default DepartmentData;
