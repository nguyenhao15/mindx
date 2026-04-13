import ManualCustomCombobox from '@/components/input-elements/ManualCustomCombobox';
import TextInputField from '@/components/input-elements/TextInputField';
import MaintanceCategory from '@/modules/assetmanagement/dimension/components/MaintanceCateogry';
import { Controller, useFormContext } from 'react-hook-form';

interface MaintanceFormProps {
  control: any; // Replace 'any' with the appropriate type for your form control
}

const MaintanceForm = ({ control }: MaintanceFormProps) => {
  const {
    register,
    handleSubmit,
    getValues,
    watch,
    formState: { errors },
  } = useFormContext();

  console.log('Get form value: ', getValues());

  return (
    <div className='flex flex-col gap-4'>
      <div className='flex flex-wrap max-w-2xl gap-2 mx-auto shadow-md p-5 rounded-md bg-white'>
        {'Mateance value: ' + watch('maintenanceCategoryId')}
        <MaintanceCategory
          label='Loại bảo trì'
          name='maintenanceCategoryId'
          control={control}
          defaultValue={watch('categoryId') || null}
        />
      </div>
      <div className='w-full items-center justify-center mx-auto '>
        {/* <Button type='submit' variant={'positive'}>
          Submit Maintenance Request
        </Button> */}
      </div>
    </div>
  );
};

export default MaintanceForm;
