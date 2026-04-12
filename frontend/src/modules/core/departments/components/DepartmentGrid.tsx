import { useGetInSecureDepartments } from '@/modules/core/departments/hooks/useDepartmentHook';
import { Link, useNavigate } from 'react-router-dom';
import DepartmentCard from './DepartmentCard';

export function DepartmentGrid() {
  const navigate = useNavigate();
  const { data } = useGetInSecureDepartments({
    filters: [],
    pagination: {
      page: 0,
      size: 4,
      sortOrder: [
        {
          property: 'createdDate',
          direction: 'DESC',
        },
      ],
    },
  });

  const handleOnView = (data: any) => {
    navigate(`/departments/${data.departmentCode}`, {
      state: {
        departmentItem: data,
      },
    });
  };

  return (
    <div>
      <div className='flex items-center justify-between mb-6'>
        <h3 className='text-xl font-bold'>Departmental Categories</h3>
        <Link to='/departments'>
          <span className='text-sm font-medium text-brand-primary hover:underline'>
            View All
          </span>
        </Link>
      </div>
      <div className='grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4'>
        {data?.content.map((department: any) => (
          <DepartmentCard
            onView={() => handleOnView(department)}
            key={department.departmentCode}
            data={department}
          />
        ))}
      </div>
    </div>
  );
}
