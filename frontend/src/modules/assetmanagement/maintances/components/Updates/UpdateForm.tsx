import { Controller } from 'react-hook-form';
import TextInputField from '@/components/input-elements/TextInputField';
import DatePickerComponent from '@/components/input-elements/DatePickerComponent';
import type { MaintenanceStatus } from '../../schema/maintenaceSchema';

interface UpdateFormProps {
  currentStatus: MaintenanceStatus;
  control: any;
  errors: any;
  register: any;
  isLoading?: boolean;
}

const UpdateForm = ({
  currentStatus,
  control,
  errors,
  register,
  isLoading = false,
}: UpdateFormProps) => {
  return (
    <div className='min-w-full flex flex-col gap-4 bg-white p-4'>
      {currentStatus === 'APPROVED' && (
        <TextInputField
          id='totalCost'
          label='Tổng chi phí'
          type='number'
          register={register}
          errors={errors}
          min={0}
          placeholder='Nhập tổng chi phí'
        />
      )}

      <Controller
        name='inspectAt'
        control={control}
        render={({ field }) => (
          <DatePickerComponent
            {...field}
            disabled={isLoading}
            label='Ngày kiểm tra'
            errors={errors.inspection_at}
          />
        )}
      />

      <TextInputField
        id='description'
        label='Ghi chú'
        type='textarea'
        required
        register={register}
        errors={errors}
        rows={3}
        placeholder='Nhập mô tả lý do thay đổi'
      />
    </div>
  );
};

export default UpdateForm;
