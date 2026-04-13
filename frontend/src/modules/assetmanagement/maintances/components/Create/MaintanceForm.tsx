import DatePickerComponent from '@/components/input-elements/DatePickerComponent';
import TextInputField from '@/components/input-elements/TextInputField';
import MaintanceCategory from '@/modules/assetmanagement/dimension/components/MaintanceCateogry';
import { Controller, useFormContext } from 'react-hook-form';

const MaintanceForm = () => {
  const {
    getValues,
    watch,
    control,
    formState: { errors },
  } = useFormContext();

  return (
    <div className='max-w-2xl flex flex-col gap-4'>
      <div className='flex flex-col gap-2 shadow-md p-5 rounded-md bg-white'>
        <Controller
          name='description'
          control={control}
          render={({ field }) => (
            <TextInputField
              {...field}
              labelSize='lg'
              type='textarea'
              label='Mô tả sự cố'
              id='description'
              errors={errors}
            />
          )}
        />
        <Controller
          name='issueDate'
          control={control}
          render={({ field }) => (
            <DatePickerComponent
              {...field}
              label='Ngày phát sinh sự cố'
              required
              errors={errors.issueDate}
            />
          )}
        />
        <MaintanceCategory
          label='Loại bảo trì'
          required
          name='maintenanceCategoryId'
          defaultValue={watch('maintenanceCategoryId') || null}
        />
      </div>
      <div className='w-sm p-2 items-center justify-center mx-auto '></div>
    </div>
  );
};

export default MaintanceForm;
