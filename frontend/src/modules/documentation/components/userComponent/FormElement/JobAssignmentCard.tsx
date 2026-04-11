import { useEffect, useRef, type ChangeEvent } from 'react';
import { TrashIcon } from 'lucide-react';
import { Switch } from '../../shared/Switch';
import ManualCustomCombobox from '@/components/input-elements/ManualCustomCombobox';
import { useDepartmentForForm } from '@/modules/documentation/hookQueries/useDepartmentForForm';
import { Controller, useForm } from 'react-hook-form';
import { Button } from '@/components/ui/button';
import TextInputField from '@/components/input-elements/TextInputField';
import { WorkProfile } from '@/modules/documentation/validations/userSchema';
import { zodResolver } from '@hookform/resolvers/zod';

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
  isEditing: boolean;
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
  canRemove,
  isEditing,
  disableActions,
  onStartEdit,
  onCancelEdit,
  onSave,
  onRemove,
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

    // Keep initial positionCode from user data; reset only when department is changed by user.
    if (
      previousDepartmentId !== undefined &&
      previousDepartmentId !== departmentId
    ) {
      setValue('positionCode', '');
    }

    previousDepartmentIdRef.current = departmentId;
  }, [departmentId, setValue]);

  const handleSave = handleSubmit((data) => {
    onSave({
      ...assignment,
      ...data,
      isPrimary: Boolean(data.isPrimary),
      isMainPosition: Boolean(data.isPrimary),
      positionLevel: Number(data.positionLevel) || 0,
      buAllowedList: data.buAllowedList ?? [],
      isNew: false,
    });
  });

  const handleCancel = () => {
    reset(assignment);
    onCancelEdit(assignment.id);
  };

  console.log('Local Errors: ', localErrors);

  return (
    <div className='bg-white rounded-xl shadow-sm border border-gray-200 p-8 transition-all hover:shadow-md'>
      <div className='flex flex-col sm:flex-row sm:items-center justify-between gap-4 mb-6 pb-4 border-b border-gray-100'>
        <div className='flex flex-col sm:flex-row items-center gap-3'>
          <h3 className='text-lg font-semibold text-gray-900 my-auto'>
            Assignment #{index + 1}
          </h3>
          {isPrimary && (
            <span className='px-2.5 py-0.5 h-full rounded-full bg-red-100 text-[#e31f20] text-xs font-medium'>
              Primary
            </span>
          )}
        </div>

        <div className='flex items-center gap-3'>
          {!isEditing && (
            <Button
              type='button'
              variant='outline'
              onClick={() => onStartEdit(assignment.id)}
              disabled={disableActions}
            >
              Edit
            </Button>
          )}

          {canRemove && !isEditing && (
            <button
              type='button'
              onClick={() => onRemove(assignment.id)}
              className='text-gray-400 hover:text-red-600 p-2 rounded-lg hover:bg-red-50 transition-colors focus:outline-none focus:ring-2 focus:ring-red-500/20'
              aria-label='Remove assignment'
              disabled={disableActions}
            >
              <TrashIcon className='w-5 h-5' />
            </button>
          )}
        </div>
      </div>

      <Switch
        id={`primary-toggle-${assignment.id}`}
        checked={Boolean(isPrimary)}
        onChange={(checked) => setValue('isPrimary', checked)}
        label='Set as Primary Position (Vị trí chính)'
      />

      <div className='grid grid-cols-1 md:grid-cols-3 gap-6 mb-6'>
        <Controller
          name='departmentId'
          control={methods.control}
          render={({ field }) => (
            <ManualCustomCombobox
              {...field}
              id={`departmentId`}
              label='Bộ phận làm việc'
              required
              disabled={isLoading || !isEditing}
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
              disabled={!departmentId || isLoading || !isEditing}
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
            disabled={!departmentId || isLoading || !isEditing}
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
              disabled={!departmentId || isLoading || !isEditing}
            />
          )}
        />
      </div>

      {isEditing && (
        <div className='mt-6 flex items-center justify-end gap-3'>
          <Button type='button' variant='outline' onClick={handleCancel}>
            Cancel
          </Button>
          <Button type='button' variant='positive' onClick={handleSave}>
            Save Assignment
          </Button>
        </div>
      )}
    </div>
  );
}
