import WorkingField from '../workingFieldComponent/WorkingField';
import PositionComponent from '../positionComponent/PositionComponent';
import DepartmentComponent from '@/modules/core/departments/components/DepartmentComponent';

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
