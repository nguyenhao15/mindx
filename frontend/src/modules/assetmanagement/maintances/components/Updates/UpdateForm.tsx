import { Controller } from 'react-hook-form';
import TextInputField from '@/components/input-elements/TextInputField';
import ManualCustomCombobox from '@/components/input-elements/ManualCustomCombobox';
import { Switch } from '@/components/input-elements/Switch';
import {
  MAINTENANCE_STATUS_VALUES,
  type UpdateMaintenanceRequestDTO,
} from '../../schema/maintenaceSchema';
import { CHANGE_TYPE, MODULENUM } from '@/constants/module-const';

interface UpdateFormProps {
  control: any;
  errors: any;
  register: any;

  isLoading?: boolean;
}

const statusOptions = MAINTENANCE_STATUS_VALUES.map((s) => ({
  label: s,
  value: s,
}));

const changeTypeOptions = CHANGE_TYPE.map((c) => ({ label: c, value: c }));

const moduleOptions = MODULENUM.map((m) => ({ label: m, value: m }));

const UpdateForm = ({
  control,
  errors,
  register,
  isLoading = false,
}: UpdateFormProps) => {
  return (
    <div className='min-w-full'>
      {/* ── Maintenance fields ── */}

      <TextInputField
        id='totalCost'
        label='Tổng chi phí'
        type='number'
        register={register}
        errors={errors}
        min={0}
        placeholder='Nhập tổng chi phí'
      />

      {/* ── Audit fields ── */}
      <div className='border-t border-slate-100 pt-4 flex flex-col gap-4'>
        <TextInputField
          id='updateValue'
          label='Giá trị cập nhật'
          required
          register={register}
          errors={errors}
          placeholder='Nhập giá trị cần cập nhật'
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
    </div>
  );
};

export default UpdateForm;
