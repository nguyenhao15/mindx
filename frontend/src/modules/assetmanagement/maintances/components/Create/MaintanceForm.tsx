import DatePickerComponent from '@/components/input-elements/DatePickerComponent';
import TextInputField from '@/components/input-elements/TextInputField';
import MaintanceCategoryOptions from '@/modules/assetmanagement/dimension/components/MaintanceCateogryOptions';
import MaintanceFixItemOptions from '@/modules/assetmanagement/dimension/components/MaintanceFixItemOptions';
import AttachmentControl from '@/modules/core/attachments/components/AttachmentControlComponent';
import BasementOptions from '@/modules/core/basement/components/BasementOptions';
import { Controller, useFormContext } from 'react-hook-form';

const MaintanceForm = () => {
  const {
    watch,
    control,

    formState: { errors },
  } = useFormContext();

  return (
    <div className='max-w-2xl flex flex-col gap-4 shadow-xl rounded-lg'>
      <div className='flex flex-col gap-2 p-5 bg-white'>
        <div className='grid grid-cols-1 md:grid-cols-2 gap-2'>
          <MaintanceCategoryOptions
            label='Loại bảo trì'
            required
            name='maintenanceCategoryId'
            defaultValue={watch('maintenanceCategoryId') || null}
          />
          <MaintanceFixItemOptions
            label='Hạng mục bảo trì'
            required
            name='maintenanceItemId'
            categoryId={watch('maintenanceCategoryId')}
            defaultValue={watch('maintenanceItemId') || null}
          />
        </div>
        <BasementOptions
          label='Cơ sở'
          required
          placeholder='Chọn cơ sở'
          name='locationId'
          defaultValue={watch('locationId')}
        />

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
        <Controller
          name='attachments'
          control={control}
          render={({
            field: { onChange, value, ...field },
            formState: { errors },
          }) => (
            <AttachmentControl
              rest={field}
              title='Đính kèm tệp'
              attachedFile={value}
              onFileAttach={onChange}
              isMultiFile
              supportedFileTypes={['PDF', 'DOCX', 'PNG', 'JPG', 'JPEG']}
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
