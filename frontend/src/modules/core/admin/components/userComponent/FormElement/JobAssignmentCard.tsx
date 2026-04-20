import { Controller, useFormContext } from 'react-hook-form';
import TextInputField from '@/components/input-elements/TextInputField';
import DepartmentOptionsControl from '@/modules/core/departments/components/DepartmentOptionsControl';
import PositionComponentOptionControl from '@/modules/core/positions/components/PositionComponentOptionControl';
import BasementOptions from '@/modules/core/basement/components/BasementOptions';

interface JobAssignmentCardProps {
  control: any;
}

export function JobAssignmentCard({ control }: JobAssignmentCardProps) {
  const {
    register,
    watch,
    formState: { errors: localErrors },
  } = useFormContext();

  const departmentId = watch('departmentId');

  console.log('Errors: ', localErrors);

  return (
    <div className='bg-white rounded-xl shadow-sm border border-gray-200 p-8 transition-all hover:shadow-md'>
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
              isLoading={false}
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
              isLoading={false}
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
          control={control}
          label='Cấp bậc vị trí'
          required
          register={register}
          errors={localErrors}
          refValue={watch('positionLevel')}
          placeholder='Cấp bậc vị trí...'
          disabled={!departmentId}
        />
        <div className='col-span-3'>
          <BasementOptions
            label='Trụ sở làm việc'
            multiple={true}
            name='buAllowedList'
            required
            errors={localErrors?.buAllowedList?.message as string}
          />
        </div>
      </div>
    </div>
  );
}
