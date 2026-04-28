import { Controller, useFormContext } from 'react-hook-form';
import TextInputField from '@/components/input-elements/TextInputField';
import DatePickerComponent from '@/components/input-elements/DatePickerComponent';
import type { MaintenanceStatus } from '../../schema/maintenaceSchema';
import LoadUserListByDepartmentControl from '@/modules/core/humanResource/components/LoadUserListByDepartmentControl';
import { ComboboxComponent } from '@/components/input-elements/ComboboxComponent';

interface UpdateFormProps {
  control: any;
  errors: any;
  register: any;
  isLoading?: boolean;
}

const UpdateForm = ({
  control,
  errors,
  register,
  isLoading = false,
}: UpdateFormProps) => {
  const { watch } = useFormContext();

  const currentStatus = watch('maintenancesStatus') as MaintenanceStatus;

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
        name='maintenanceType'
        control={control}
        render={({ field: { value, onChange, ...rest } }) => (
          <ComboboxComponent
            {...rest}
            defaultValue={value}
            label='Phân loại bảo trì'
            options={[
              { label: 'Kỹ thuật nội bộ', value: 'INTERNAL' },
              { label: 'Thuê ngoài', value: 'OUTSOURCE' },
              { label: 'Bảo hành', value: 'WARRANTY' },
            ]}
            onChange={onChange}
            disabled={isLoading}
            errors={errors.maintenanceType?.message}
          />
        )}
      />

      <Controller
        name='inspectAt'
        control={control}
        render={({ field: { value, onChange, ...rest } }) => (
          <DatePickerComponent
            {...rest}
            value={value}
            required
            onChange={onChange}
            disabled={isLoading}
            label='Ngày kiểm tra'
            errors={errors.inspectAt?.message}
          />
        )}
      />

      <Controller
        name='assignedTo'
        control={control}
        render={({ field: { value, onChange, ...rest } }) => (
          <LoadUserListByDepartmentControl
            {...rest}
            value={value}
            onChange={onChange}
            departmentId={'IM'}
            errors={errors.assignedTo?.message}
            label='Người được giao'
            isLoading={isLoading}
          />
        )}
      />

      <TextInputField
        id='description'
        label='Ghi chú'
        type='textarea'
        labelSize='lg'
        required
        register={register}
        errors={errors}
        rows={5}
        placeholder='Nhập mô tả lý do thay đổi'
      />
    </div>
  );
};

export default UpdateForm;
