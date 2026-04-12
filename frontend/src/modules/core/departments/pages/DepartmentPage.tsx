import FormSection from '@/components/shared/FormSection';
import HeaderPage from '@/components/shared/HeaderPage';
import DepartmentGallery from '@/modules/core/departments/components/DepartmentGallery';
import { Laptop, List } from 'lucide-react';

const DepartmentPage = () => {
  return (
    <div className='p-2 h-full'>
      <div className='flex flex-col h-full px-3 md:px-10 lg:px-30 py-2 '>
        <HeaderPage
          title='Phòng ban'
          subtitle='Danh sách các phòng ban trong công ty.'
          Icon={Laptop}
        />
        <div className='flex-1 flex flex-col mt-5 p-4'>
          <FormSection headerLabel='Phòng ban hiện có' icon={List}>
            <DepartmentGallery />
          </FormSection>
        </div>
      </div>
    </div>
  );
};

export default DepartmentPage;
