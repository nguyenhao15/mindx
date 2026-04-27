import { ComboboxComponent } from '@/components/input-elements/ComboboxComponent';

import { Controller, useFormContext } from 'react-hook-form';
import WorkFlowComponentControl from './WorkFlowComponentControl';
import TextInputField from '@/components/input-elements/TextInputField';
import { useEffect } from 'react';
import DepartmentOptionsControl from '@/modules/core/departments/components/DepartmentOptionsControl';
import PositionComponentOptionControl from '@/modules/core/positions/components/PositionComponentOptionControl';

interface ApprovalFormElementsProps {
  isLoading?: boolean;
  control: any;
  register: any;
  errors: any;
}

const ApprovalFormElements = ({
  isLoading,
  control,
  register,
  errors,
}: ApprovalFormElementsProps) => {
  const { watch, setValue } = useFormContext();

  const [module, allowType] = watch(['module', 'allowType']);

  useEffect(() => {
    setValue('workFlowId', '');
  }, [module]);

  useEffect(() => {
    setValue('allowValue', '');
    if (allowType === 'AUTHOR') {
      setValue('allowValue', 'author');
    }
  }, [allowType]);

  return (
    <div className=' flex flex-col gap-5 p-4'>
      <Controller
        control={control}
        name='module'
        render={({ field: { onChange, value, ...rest } }) => (
          <ComboboxComponent
            label='Module'
            onChange={onChange}
            {...rest}
            defaultValue={value}
            isLoading={isLoading}
            options={[
              { label: 'Quy trình', value: 'DOCUMENTS' },
              { label: 'Sửa chữa', value: 'MAINTENANCE' },
              { label: 'Hợp đồng', value: 'CONTRACT' },
            ]}
            errors={errors?.module?.message as string}
          />
        )}
      />
      <Controller
        control={control}
        name='workFlowId'
        render={({ field: { onChange, value, ...rest } }) => (
          <WorkFlowComponentControl
            {...rest}
            isLoading={isLoading}
            module={module}
            value={value}
            onChange={onChange}
            errors={errors?.workFlowId?.message as string}
          />
        )}
      />
      <Controller
        control={control}
        name='allowType'
        render={({ field: { onChange, value, ...rest } }) => (
          <ComboboxComponent
            label='Quy tắc phê duyệt'
            onChange={onChange}
            required
            {...rest}
            defaultValue={value}
            isLoading={isLoading}
            options={[
              { label: 'Theo bộ phận', value: 'DEPARTMENT' },
              { label: 'Theo vị trí', value: 'POSITION' },
              { label: 'Theo người gửi', value: 'AUTHOR' },
              { label: 'Mọi người', value: 'EVERYONE' },
            ]}
            errors={errors?.allowType?.message as string}
          />
        )}
      />
      <Controller
        control={control}
        name={allowType === 'DEPARTMENT' ? 'allowValue' : 'departmentId'}
        render={({ field: { onChange, value, ...rest } }) => (
          <DepartmentOptionsControl
            id={allowType === 'DEPARTMENT' ? 'allowValue' : 'departmentId'}
            onChange={onChange}
            value={value}
            {...rest}
            label='Chọn bộ phận nhận phê duyệt'
            required={allowType !== 'AUTHOR' && allowType !== 'EVERYONE'}
            disabled={
              allowType === 'AUTHOR' || allowType === 'EVERYONE' || isLoading
            }
            isLoading={isLoading}
            placeholder='Chọn giá trị phê duyệt...'
            errorMessage={errors?.allowValue?.message as string}
          />
        )}
      />

      <Controller
        control={control}
        name={allowType === 'POSITION' ? 'allowValue' : 'positionId'}
        render={({ field: { onChange, value, ...rest } }) => (
          <PositionComponentOptionControl
            departmentId={watch('departmentId')}
            id={allowType === 'POSITION' ? 'allowValue' : 'positionId'}
            onChange={onChange}
            value={value}
            {...rest}
            label='Chọn vị trí nhận phê duyệt'
            required={allowType === 'POSITION'}
            isLoading={isLoading}
            placeholder='Chọn giá trị phê duyệt...'
            errorMessage={errors?.allowValue?.message as string}
            disabled={
              watch('allowType') !== 'POSITION' ||
              isLoading ||
              !watch('departmentId')
            }
          />
        )}
      />
      <Controller
        control={control}
        name='requesterPosition'
        render={({ field: { onChange, value, ...rest } }) => (
          <DepartmentOptionsControl
            id={'requesterPosition'}
            onChange={onChange}
            value={value}
            {...rest}
            label='Chọn bộ phận yêu cầu phê duyệt'
            isLoading={isLoading}
            description='Xác định luồng duyệt áp dụng cụ thể với bộ phận nào. Nếu để trống, luồng duyệt sẽ áp dụng cho tất cả bộ phận.'
            placeholder='Chọn giá trị yêu cầu phê duyệt...'
            errorMessage={errors?.allowValue?.message as string}
          />
        )}
      />

      <TextInputField
        id='description'
        type='textarea'
        labelSize='lg'
        control={control}
        label='Mô tả'
        isLoading={isLoading}
        register={register}
        errors={errors}
        placeholder='Mô tả...'
      />
      <TextInputField
        id='priority'
        type='number'
        labelSize='lg'
        control={control}
        label='Mức độ ưu tiên'
        isLoading={isLoading}
        register={register}
        errors={errors}
        placeholder='Mức độ ưu tiên...'
      />
    </div>
  );
};

export default ApprovalFormElements;
