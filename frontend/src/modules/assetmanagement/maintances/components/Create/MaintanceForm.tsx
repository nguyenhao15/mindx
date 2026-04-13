import ManualCustomCombobox from '@/components/input-elements/ManualCustomCombobox';
import TextInputField from '@/components/input-elements/TextInputField';
import { Controller, useFormContext } from 'react-hook-form';

interface MaintanceFormProps {
  control: any; // Replace 'any' with the appropriate type for your form control
}

const MaintanceForm = ({ control }: MaintanceFormProps) => {
  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
  } = useFormContext();

  return (
    <div className='flex flex-col gap-4'>
      <div className='flex flex-wrap max-w-2xl gap-2 mx-auto shadow-md p-5 rounded-md bg-white'>
        {/* <TextInputField
          label='Diễn giải'
          id='description'
          control={control}
          type='textarea'
          register={register}
          errors={errors}
          required  
          placeholder='Enter description'
        /> */}
        <Controller
          name='maintenanceCategoryId'
          control={control}
          render={({ field }) => (
            <ManualCustomCombobox
              {...field}
              id='maintenanceCategoryId'
              name='maintenanceCategoryId'
              isLoading={false}
              label='Loại bảo trì'
              required
              placeholder='Chọn loại bảo trì...'
              options={[
                { value: 'SCHEDULED', label: 'Định kỳ' },
                { value: 'UNSCHEDULED', label: 'Không định kỳ' },
                { value: 'EMERGENCY', label: 'Khẩn cấp' },
              ]}
              errors={
                typeof errors?.maintenanceCategoryId?.message === 'string'
                  ? errors.maintenanceCategoryId.message
                  : undefined
              }
            />
          )}
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
