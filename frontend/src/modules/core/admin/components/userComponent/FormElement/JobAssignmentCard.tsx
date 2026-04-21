import { Controller, useFormContext } from 'react-hook-form';
import TextInputField from '@/components/input-elements/TextInputField';
import DepartmentOptionsControl from '@/modules/core/departments/components/DepartmentOptionsControl';
import PositionComponentOptionControl from '@/modules/core/positions/components/PositionComponentOptionControl';
import BasementOptions from '@/modules/core/basement/components/BasementOptions';
import { Switch } from '@/components/input-elements/Switch';

interface JobAssignmentCardProps {
  control: any;
  isLoading?: boolean;
}

export function JobAssignmentCard({
  control,
  isLoading,
}: JobAssignmentCardProps) {
  const {
    register,
    watch,
    getValues,
    formState: { errors: localErrors },
  } = useFormContext();

  const departmentId = watch('departmentId');

  return (
    <div className='bg-white rounded-xl shadow-sm border border-gray-200 p-8 transition-all hover:shadow-md'>
      <Controller
        control={control}
        name='isDefault'
        render={({ field: { onChange, value, ...rest } }) => (
          <Switch
            id='isDefault'
            onChange={onChange}
            disabled={isLoading}
            checked={value}
            {...rest}
            label='Công việc chính'
          />
        )}
      />
      <div className='mt-6 grid grid-cols-1 md:grid-cols-3 gap-6 mb-6'>
        <Controller
          control={control}
          name='departmentId'
          render={({ field: { onChange, value, ...rest } }) => (
            <DepartmentOptionsControl
              id='departmentId'
              onChange={onChange}
              value={value}
              {...rest}
              label='Phòng ban'
              required
              isLoading={isLoading}
              placeholder='Chọn phòng ban...'
              errorMessage={localErrors?.departmentId?.message as string}
            />
          )}
        />

        <Controller
          control={control}
          name='positionId'
          render={({ field: { onChange, value, ...rest } }) => (
            <PositionComponentOptionControl
              id='positionId'
              onChange={onChange}
              value={value}
              {...rest}
              label='Vị trí'
              required
              isLoading={isLoading}
              placeholder='Chọn vị trí...'
              errorMessage={localErrors?.positionId?.message as string}
              disabled={!departmentId}
              departmentId={departmentId}
            />
          )}
        />
        <TextInputField
          id='positionLevel'
          type='number'
          min={0}
          max={5}
          labelSize='lg'
          control={control}
          label='Cấp bậc vị trí'
          isLoading={isLoading}
          required
          register={register}
          errors={localErrors}
          refValue={watch('positionLevel')}
          placeholder='Cấp bậc vị trí...'
        />
        <div className='col-span-3'>
          <Controller
            control={control}
            name='buAllowedList'
            render={({ field: { onChange, value, ...rest } }) => (
              <BasementOptions
                label='Trụ sở làm việc'
                multiple={true}
                value={value}
                isLoading={isLoading}
                {...rest}
                required
                errors={localErrors?.buAllowedList?.message as string}
              />
            )}
          />
        </div>
      </div>
    </div>
  );
}
