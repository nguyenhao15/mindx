import MaintanceCategory from '@/modules/assetmanagement/dimension/components/MaintanceCateogry';
import { useFormContext } from 'react-hook-form';

const MaintanceForm = () => {
  const {
    getValues,
    watch,
    formState: { errors },
  } = useFormContext();

  return (
    <div className='w-full flex flex-col gap-4'>
      <div className='grid grid-cols-2 gap-2 mx-auto shadow-md p-5 rounded-md bg-white'>
        <MaintanceCategory
          label='Loại bảo trì'
          name='maintenanceCategoryId'
          defaultValue={watch('maintenanceCategoryId') || null}
        />
      </div>
      <div className='w-sm p-2 items-center justify-center mx-auto '></div>
    </div>
  );
};

export default MaintanceForm;
