import DatePickerComponent from '@/components/input-elements/DatePickerComponent';
import TextInputField from '@/components/input-elements/TextInputField';
import MaintanceCategoryOptions from '@/modules/assetmanagement/dimension/components/MaintanceCateogryOptions';
import MaintanceFixItemOptions from '@/modules/assetmanagement/dimension/components/MaintanceFixItemOptions';
import AttachmentControl from '@/modules/core/attachments/components/AttachmentControlComponent';
import BasementOptions from '@/modules/core/basement/components/BasementOptions';
import { Controller, useFormContext } from 'react-hook-form';

interface MaintanceFormProps {
  isLoading: boolean;
}

const MaintanceForm = ({ isLoading }: MaintanceFormProps) => {
  const {
    watch,
    register,
    control,
    formState: { errors },
  } = useFormContext();

  console.log('Errors: ', errors);

  return (
    <div className='w-full flex flex-col gap-4 shadow-xl rounded-lg'>
      <div className='flex flex-col gap-2 p-5 bg-white'>
        <div className='w-full grid grid-cols-1 md:grid-cols-2 gap-2'>
          <MaintanceCategoryOptions
            disabled={isLoading}
            label='Loại bảo trì'
            required
            name='maintenanceCategoryId'
            defaultValue={watch('maintenanceCategoryId') || null}
          />
          <MaintanceFixItemOptions
            label='Hạng mục bảo trì'
            disabled={isLoading}
            required
            name='maintenanceItemId'
            categoryId={watch('maintenanceCategoryId')}
            defaultValue={watch('maintenanceItemId') || null}
          />
        </div>
        <Controller
          name='locationId'
          control={control}
          render={({ field: { value, onChange, ...rest } }) => (
            <BasementOptions
              label='Cơ sở'
              isLoading={isLoading}
              required
              disabled={isLoading}
              value={value}
              onChange={onChange}
              {...rest}
            />
          )}
        />

        <TextInputField
          register={register}
          control={control}
          labelSize='lg'
          type='textarea'
          isLoading={isLoading}
          label='Mô tả sự cố'
          id='description'
          errors={errors}
        />

        <Controller
          name='issueDate'
          control={control}
          render={({ field: { onChange, ...field } }) => (
            <DatePickerComponent
              {...field}
              onChange={onChange}
              label='Ngày phát sinh sự cố'
              disabled={isLoading}
              required
              errors={errors.issueDate}
            />
          )}
        />
        <Controller
          name='attachments'
          control={control}
          render={({
            field: { onChange, value, ...field },
            formState: { errors },
          }) => (
            <AttachmentControl
              rest={field}
              {...field}
              title='Đính kèm tệp'
              attachedFile={value}
              isLoading={isLoading}
              onFileAttach={onChange}
              isMultiFile
              supportedFileTypes={['PDF', 'PNG', 'JPG', 'JPEG']}
              maxFileSize={10 * 1024 * 1024} // 10MB
              errorMessage={
                errors.attachments?.message
                  ? 'Vui lòng chọn tệp hợp lệ'
                  : undefined
              }
            />
          )}
        />
      </div>
    </div>
  );
};

export default MaintanceForm;
