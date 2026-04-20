import { Controller, useFormContext } from 'react-hook-form';
import ManualCustomCombobox from '@/components/input-elements/ManualCustomCombobox';
import RadioInputField from '@/components/shared/RadioInputField';
import TextInputField from '@/components/input-elements/TextInputField';
import { useDepartmentForForm } from '@/modules/core/utils/hook/useDepartmentForForm';

interface AccessRuleFormComponentProps {
  initialData?: {
    accessRule: {
      accessType: 'PUBLIC' | 'STRICT' | 'RESTRICTED';
      departmentCodes: string[];
      positionCodes: string[];
      minLevel: number;
      fieldIds: string[];
      buIds: string[];
    };
  };
}

const AccessRuleFormComponent = ({
  initialData,
}: AccessRuleFormComponentProps) => {
  const {
    control,
    register,
    watch,
    formState: { errors },
  } = useFormContext();

  const departmentId = watch('accessRule.departmentCodes') || [];

  const {
    departmentOptions,
    positionOptions,
    workingFieldOptions,
    buOptions,
    isLoading,
  } = useDepartmentForForm(departmentId);

  return (
    <div className='@container w-full p-2'>
      <div className='flex flex-col gap-5'>
        <div className='w-full col-span-3 m-5'>
          <Controller
            name='accessRule.accessType'
            control={control}
            render={({ field: { onChange, value, ...rest } }) => (
              <RadioInputField
                control={control}
                label='Loại truy cập'
                options={[
                  { label: 'Công khai', value: 'PUBLIC' },
                  { label: 'Nghiêm ngặt', value: 'STRICT' },
                  { label: 'Hạn chế', value: 'RESTRICTED' },
                ]}
                value={value}
                onChange={onChange}
                error={errors.accessRule?.accessType}
                {...rest}
              />
            )}
          />
        </div>

        <div className='grid grid-cols-1 @md:grid-cols-2 @xl:grid-cols-3 gap-5'>
          <Controller
            name='accessRule.departmentCodes'
            control={control}
            render={({ field }) => (
              <ManualCustomCombobox
                {...field}
                label='Phòng ban được phép truy cập'
                id='accessRule.departmentCodes'
                isLoading={isLoading}
                errors={errors.accessRule?.departmentCodes}
                multiple
                required
                options={departmentOptions}
                placeholder='Chọn loại truy cập'
              />
            )}
          />
          <Controller
            name='accessRule.positionCodes'
            control={control}
            render={({ field }) => (
              <ManualCustomCombobox
                label='Chức vụ được phép truy cập'
                id='accessRule.positionCodes'
                {...field}
                isLoading={isLoading}
                errors={errors.accessRule?.positionCodes}
                required
                multiple
                options={positionOptions}
                placeholder='Chọn loại truy cập'
              />
            )}
          />
          <TextInputField
            id='accessRule.minLevel'
            type='number'
            control={control}
            placeholder='Nhập cấp bậc tối thiểu'
            label='Cấp bậc tối thiểu được phép truy cập'
            register={register}
            defaultValue={initialData?.accessRule?.minLevel}
            errors={errors.accessRule?.minLevel}
          />
        </div>

        <div className='col-span-3 gap-5 grid grid-cols-1 md:grid-cols-2'>
          <Controller
            name='accessRule.fieldIds'
            control={control}
            render={({ field }) => (
              <ManualCustomCombobox
                label='Lĩnh vực được phép truy cập'
                id='fieldIds'
                {...field}
                isLoading={isLoading}
                errors={errors.accessRule?.fieldIds}
                required
                multiple
                options={workingFieldOptions}
                placeholder='Chọn loại truy cập'
              />
            )}
          />
          <Controller
            name='accessRule.buIds'
            control={control}
            render={({ field }) => (
              <ManualCustomCombobox
                label='Cơ sở được phép truy cập'
                id='buIds'
                {...field}
                isLoading={isLoading}
                errors={errors.accessRule?.buIds}
                required
                multiple
                options={buOptions}
                placeholder='Chọn loại truy cập'
              />
            )}
          />
        </div>
      </div>
    </div>
  );
};

export default AccessRuleFormComponent;
