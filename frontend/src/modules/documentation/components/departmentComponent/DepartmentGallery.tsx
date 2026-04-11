import { ActionHeader } from '../shared/ActionHeder';
import { useGetInSecureDepartments } from '@/modules/documentation/hookQueries/useDepartmentHook';
import DepartmentData from './DepartmentData';
import { useNavigate } from 'react-router-dom';
import { useDebouncedFilterSearch } from '@/utils/utilActions';
import { useTypeQueryState } from '@/hooks/useTypeQueryState';

const DepartmentGallery = () => {
  const { state, updateState } = useTypeQueryState();
  const navigate = useNavigate();

  const { handleSearch: handleOnSearch } = useDebouncedFilterSearch({
    updateState,
    field: 'departmentName',
    delay: 300,
    operator: 'LIKE',
  });

  const { data, isLoading } = useGetInSecureDepartments(state);
  const handlePageChange = (page: number) => {
    updateState((prev) => ({
      ...prev,
      pagination: {
        ...prev.pagination,
        page,
      },
    }));
  };

  const { content, ...rest } = data || {};

  const handleViewDepartment = (data: any) => {
    navigate(`/departments/${data.departmentCode}`, {
      state: {
        departmentItem: data,
      },
    });
  };

  return (
    <div className='px-2 h-full flex flex-col gap-5'>
      <ActionHeader
        title='Department Gallery'
        searchPlaceholder='Tìm bộ phận'
        ctaLabel='Thêm bộ phận'
        onSearch={handleOnSearch}
      />
      {data && (
        <p className='text-sm text-slate-500'>
          Tổng số bộ phận: {data.totalElements}
        </p>
      )}

      <DepartmentData
        data={content}
        onPageChange={handlePageChange}
        isLoading={isLoading}
        paginationData={rest}
        onView={handleViewDepartment}
      />
    </div>
  );
};

export default DepartmentGallery;
