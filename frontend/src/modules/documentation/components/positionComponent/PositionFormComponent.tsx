import {
  useCreatePosition,
  useUpdatePosition,
} from '@/modules/documentation/hookQueries/usePositionHook';
import {
  positionSchema,
  type PositionResponse,
} from '@/modules/documentation/validations/positionSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import React, { useMemo } from 'react';
import { Controller, useForm } from 'react-hook-form';
import toast from 'react-hot-toast';
import TextInputField from '../input-elements/TextInputField';
import ManualCustomCombobox from '../input-elements/ManualCustomCombobox';
import { Button } from '../ui/button';
import { useGetActiveDepartments } from '@/modules/documentation/hookQueries/useDepartmentHook';
import { toArray } from '@/utils/formatValue';

interface PositionFormComponentProps {
  initialData?: PositionResponse | undefined;
  onUpdate?: () => void;
}

const PositionFormComponent = ({
  initialData,
  onUpdate,
}: PositionFormComponentProps) => {
  const methods = useForm({
    mode: 'onBlur',
    resolver: zodResolver(positionSchema),
    defaultValues: initialData,
  });

  const { mutateAsync: createPosition, isPending: creating } =
    useCreatePosition();

  const { mutateAsync: updatePosition, isPending: updating } =
    useUpdatePosition(initialData?.id || '');

  const { data: departmentData, isLoading: departmentLoading } =
    useGetActiveDepartments();

  const departmentOptions = useMemo(() => {
    return toArray(departmentData).map((item: any) => ({
      label: item.departmentName,
      value: item.departmentCode,
    }));
  }, [departmentData]);

  const {
    handleSubmit,
    register,
    getValues,
    control,
    reset,
    formState: { errors },
  } = methods;

  const onSubmit = async (data: any) => {
    try {
      if (initialData) {
        const response = await updatePosition(data);
        reset();
        onUpdate && onUpdate();
        toast.success('Position updated successfully');
        return response;
      }
      const response = await createPosition(data);
      reset();
      onUpdate && onUpdate();
      toast.success('Position created successfully');
      return response;
    } catch (error) {
      toast.error('Failed to create position');
    }
  };

  console.log('form value: ', getValues());

  return (
    <div className='bg-white rounded p-10 w-150'>
      <form onSubmit={handleSubmit(onSubmit)}>
        <div className='flex flex-col gap-2 '>
          <TextInputField
            label='Position Name'
            register={register}
            id='positionName'
            errors={errors}
            placeholder='Nhập position name'
          />
          <TextInputField
            label='Position Code'
            register={register}
            id='positionCode'
            errors={errors}
            placeholder='Nhập position code'
          />
          <TextInputField
            label='Position Level'
            register={register}
            id='positionLevel'
            max={5}
            min={0}
            control={control}
            type='number'
            errors={errors}
            placeholder='Nhập position level'
          />
          <TextInputField
            label='Description'
            register={register}
            id='description'
            type='textarea'
            errors={errors}
            placeholder='Nhập description'
          />
          <Controller
            name='departmentCode'
            control={control}
            render={({ field }) => (
              <ManualCustomCombobox
                {...field}
                id='departmentCode'
                label='Department'
                isLoading={departmentLoading}
                placeholder='Select department'
                options={departmentOptions}
                updateType={{ type: 'value' }}
                errors={errors?.departmentCode?.message as string | null}
              />
            )}
          />
        </div>
        <Button
          disabled={updating || creating}
          type='submit'
          className='mt-4'
          variant={'positive'}
        >
          {initialData ? 'Update Position' : 'Create Position'}
        </Button>
      </form>
    </div>
  );
};

export default PositionFormComponent;
