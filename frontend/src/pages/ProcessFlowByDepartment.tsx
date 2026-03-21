import HeaderPage from '@/components/shared/HeaderPage';
import Loader from '@/components/shared/Loader';
import TagFlowGallery from '@/components/processFlow/TagFlowGallery';
import { useGetProcessFlowByDepartmentById } from '@/hookQueries/useProcessFlowHooks';
import { useTypeQueryState } from '@/hooks/useTypeQueryState';

import { useLocation, useParams } from 'react-router-dom';

const ProcessFlowByDepartment = ({}) => {
  const { id } = useParams();
  const location = useLocation();
  const { state, updateState } = useTypeQueryState();
  const { data, isLoading, error } = useGetProcessFlowByDepartmentById({
    id: id as string,
    filter: state,
  });
  const { departmentItem } = location.state || {};

  const { content, ...rest } = data || {
    content: [],
    pagination: { page: 1, pageSize: 12, total: 0 },
  };

  return (
    <div className='p-4'>
      <HeaderPage
        title={`${departmentItem.departmentName}`}
        subtitle='Danh sách quy trình thuộc phòng ban này.'
        svgLink={departmentItem.iconSvg}
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
  );
};

export default ProcessFlowByDepartment;
