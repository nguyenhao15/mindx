import { useEffect, useRef, type ChangeEvent } from 'react';
import ManualCustomCombobox from '@/components/input-elements/ManualCustomCombobox';
import { Controller, useForm } from 'react-hook-form';
import TextInputField from '@/components/input-elements/TextInputField';
import { WorkProfile } from '@/modules/core/auth/schemas/userSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { Switch } from '@/components/input-elements/Switch';
import { useDepartmentForForm } from '@/modules/core/utils/hook/useDepartmentForForm';

export interface Assignment {
  id: string;
  isPrimary: boolean;
  departmentId: string;
  positionCode: string;
  positionLevel: number;
  isMainPosition: boolean;
  buAllowedList: string[];
  isNew?: boolean;
}

interface JobAssignmentCardProps {
  errors: any;
  assignment: Assignment;
  index: number;
  canRemove: boolean;

  disableActions?: boolean;
  onStartEdit: (id: string) => void;
  onCancelEdit: (id: string) => void;
  onSave: (data: Assignment) => void;
  onRemove: (id: string) => void;
}

export function JobAssignmentCard({
  errors,
  assignment,
  index,

  onCancelEdit,
  onSave,
}: JobAssignmentCardProps) {
  const methods = useForm<Assignment>({
    mode: 'onBlur',
    resolver: zodResolver(WorkProfile),
    defaultValues: assignment,
  });

  const {
    watch,
    setValue,
    control,
    register,
    handleSubmit,
    reset,
    formState: { errors: localErrors },
  } = methods;

  const departmentId = watch('departmentId');
  const isPrimary = watch('isPrimary');

  const previousDepartmentIdRef = useRef<string | undefined>(
    assignment.departmentId,
  );
  const didMountRef = useRef(false);
  const { buOptions, positionOptions, departmentOptions, isLoading } =
    useDepartmentForForm(departmentId);

  useEffect(() => {
    reset(assignment);
    previousDepartmentIdRef.current = assignment.departmentId;
  }, [assignment, reset]);

  useEffect(() => {
    if (!didMountRef.current) {
      didMountRef.current = true;
      previousDepartmentIdRef.current = departmentId;
      return;
    }

    const previousDepartmentId = previousDepartmentIdRef.current;

    if (
      previousDepartmentId !== undefined &&
      previousDepartmentId !== departmentId
    ) {
      setValue('positionCode', '');
    }

    previousDepartmentIdRef.current = departmentId;
  }, [departmentId, setValue]);

  return (
    <div className='bg-white rounded-xl shadow-sm border border-gray-200 p-8 transition-all hover:shadow-md'>
      <div className='flex flex-col sm:flex-row m-4 items-center gap-3'>
        <h3 className='text-lg font-semibold text-gray-900 my-auto'>
          Assignment #{index + 1}
        </h3>
        {isPrimary && (
          <span className='px-2.5 py-0.5 h-full rounded-full bg-red-100 text-[#e31f20] text-xs font-medium'>
            Primary
          </span>
        )}
      </div>

      <Switch
        id={`primary-toggle-${assignment.id}`}
        checked={Boolean(isPrimary)}
        onChange={(checked) => setValue('isPrimary', checked)}
        label='Set as Primary Position (Vị trí chính)'
      />

      <div className='mt-6 grid grid-cols-1 md:grid-cols-3 gap-6 mb-6'>
        <Controller
          name='departmentId'
          control={methods.control}
          render={({ field }) => (
            <ManualCustomCombobox
              {...field}
              id={`departmentId`}
              label='Bộ phận làm việc'
              required
              disabled={isLoading}
              isLoading={isLoading}
              placeholder='Chọn mã phòng ban...'
              options={departmentOptions || []}
              errors={
                localErrors?.departmentId?.message ??
                errors?.departmentId?.message
              }
            />
          )}
        />

        <Controller
          name='positionCode'
          control={methods.control}
          render={({ field }) => (
            <ManualCustomCombobox
              {...field}
              id={`positionCode`}
              label='Vị trí làm việc'
              required
              disabled={!departmentId || isLoading}
              isLoading={isLoading}
              options={positionOptions || []}
              placeholder='Mã vị trí...'
              errors={
                localErrors?.positionCode?.message ??
                errors?.positionCode?.message
              }
            />
          )}
        />

        <div className='flex flex-col gap-1'>
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
            disabled={!departmentId || isLoading}
          />
        </div>
      </div>

      <div className='w-full items-center justify-center mx-auto '>
        <Controller
          name='buAllowedList'
          control={control}
          render={({ field }) => (
            <ManualCustomCombobox
              {...field}
              id={`buAllowedList`}
              label='Danh sách BU được phép'
              required
              multiple
              isLoading={isLoading}
              options={buOptions || []}
              placeholder='Chọn danh sách BU được phép...'
              errors={
                localErrors?.buAllowedList?.message ??
                errors?.buAllowedList?.message
              }
              disabled={!departmentId || isLoading}
            />
          )}
        />
      </div>
    </div>
  );
}
