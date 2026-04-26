import { ComboboxComponent } from '@/components/input-elements/ComboboxComponent';
import TextInputField from '@/components/input-elements/TextInputField';

import { Controller } from 'react-hook-form';

interface WorkFlowFormElementsProps {
  control?: any;
  register?: any;
  errors?: any;
  isLoading?: boolean;
}

const WorkFlowFormElements = ({
  control,
  register,
  errors,
  isLoading,
}: WorkFlowFormElementsProps) => {
  return (
    <div className='flex flex-col gap-5 p-4'>
      <TextInputField
        label='Trạng thái gốc'
        disabled={isLoading}
        isLoading={isLoading}
        labelSize='lg'
        id='fromStatus'
        register={register}
        control={control}
        placeholder='Nhập trạng thái gốc...'
        errors={errors}
      />
      <Controller
        control={control}
        name='module'
        render={({ field: { onChange, value, ...rest } }) => (
          <ComboboxComponent
            label='Module'
            defaultValue={value}
            onChange={onChange}
            {...rest}
            isLoading={isLoading}
            options={[
              { label: 'Quy trình', value: 'DOCUMENTS' },
              { label: 'Sửa chữa', value: 'MAINTENANCE' },
              { label: 'Hợp đồng', value: 'CONTRACT' },
            ]}
          />
        )}
      />
      <Controller
        control={control}
        name='operator'
        render={({ field: { onChange, value, ...rest } }) => (
          <ComboboxComponent
            label='Quy tắc so sánh'
            onChange={onChange}
            defaultValue={value}
            {...rest}
            isLoading={isLoading}
            options={[
              { label: 'Bằng', value: 'EQ' },
              { label: 'Không bằng', value: 'NEQ' },
            ]}
            errors={errors.operator?.message as string}
          />
        )}
      />
      <TextInputField
        label='Trạng thái đích'
        labelSize='lg'
        isLoading={isLoading}
        id='toStatus'
        register={register}
        control={control}
        placeholder='Nhập trạng thái đích...'
        errors={errors}
      />
      <TextInputField
        label='Tên nhãn'
        id='labelName'
        register={register}
        labelSize='lg'
        isLoading={isLoading}
        control={control}
        placeholder='Nhập tên nhãn...'
        errors={errors}
      />
      <TextInputField
        label='Mô tả'
        id='description'
        labelSize='lg'
        register={register}
        type='textarea'
        control={control}
        isLoading={isLoading}
        placeholder='Nhập mô tả...'
        errors={errors}
      />
      <Controller
        control={control}
        name='actionType'
        render={({ field: { onChange, value, ...rest } }) => (
          <ComboboxComponent
            label='Loại hành động'
            onChange={onChange}
            {...rest}
            defaultValue={value}
            isLoading={isLoading}
            options={[
              { label: 'Tích cực', value: 'POSITIVE' },
              { label: 'Tiêu cực', value: 'NEGATIVE' },
            ]}
            errors={errors.actionType?.message as string}
          />
        )}
      />
    </div>
  );
};

export default WorkFlowFormElements;
