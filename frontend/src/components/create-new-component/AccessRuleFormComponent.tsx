import { Controller, useFormContext } from 'react-hook-form';
import ManualCustomCombobox from '../input-elements/ManualCustomCombobox';
import RadioInputField from '../shared/RadioInputField';
import { useDepartmentForForm } from '@/hookQueries/useDepartmentForForm';
import TextInputField from '../input-elements/TextInputField';

interface AccessRuleFormComponentProps {
  initialData?: any;
}

const AccessRuleFormComponent = ({
  initialData,
}: AccessRuleFormComponentProps) => {
  const {
    control,
    register,
    formState: { errors },
  } = useFormContext();

  const {
    departmentOptions,
    positionOptions,
    workingFieldOptions,
    buOptions,
    isLoading,
  } = useDepartmentForForm();

  return (
    <div className='@container w-full p-2'>
      <div className='flex flex-col gap-5'>
        <div className='w-full col-span-3 m-5'>
          <RadioInputField
            name='accessRule.accessType'
            control={control}
            label='Loại truy cập'
            options={[
              { label: 'Công khai', value: 'PUBLIC' },
              { label: 'Nghiêm ngặt', value: 'STRICT' },
              { label: 'Hạn chế', value: 'RESTRICTED' },
            ]}
            value={initialData?.accessRule?.accessType || 'PUBLIC'}
            error={errors.accessRule?.accessType}
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
