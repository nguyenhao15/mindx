import WorkingField from '../workingFieldComponent/WorkingField';
import DepartmentComponent from '../departmentComponent/DepartmentComponent';
import PositionComponent from '../positionComponent/PositionComponent';

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
