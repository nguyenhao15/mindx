import DepartmentComponent from '@/modules/core/departments/components/DepartmentComponent';
import PositionComponent from '@/modules/core/positions/components/PositionComponent';
import WorkingField from '@/modules/core/workingFields/components/WorkingField';

const InternalWorkingSystemComponent = () => {
  return (
    <div className='flex flex-col gap-5 p-2'>
      <WorkingField />
      <br />
      <DepartmentComponent />
      <br />
      <PositionComponent />
    </div>
  );
};

export default InternalWorkingSystemComponent;
