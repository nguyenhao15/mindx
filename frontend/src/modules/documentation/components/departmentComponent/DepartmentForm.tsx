import {
  departmentSchema,
  type DepartmentObjType,
} from '@/modules/documentation/validations/departmentSchema';
import { zodResolver } from '@hookform/resolvers/zod';

import { Controller, useForm } from 'react-hook-form';
import TextInputField from '../input-elements/TextInputField';
import { Button } from '../ui/button';
import ManualCustomCombobox from '../input-elements/ManualCustomCombobox';
import { useGetActiveWorkingFieldList } from '@/modules/documentation/hookQueries/useWorkingFieldHook';
import { safeString, toArray } from '@/utils/formatValue';
import {
  useCreateDepartment,
  useUpdateDepartment,
} from '@/modules/documentation/hookQueries/useDepartmentHook';
import type { WorkingFieldObject } from '@/modules/documentation/validations/workingFieldSchema';
import { useMemo } from 'react';
import toast from 'react-hot-toast';
import RadioInputField from '../shared/RadioInputField';

interface DepartmentFormProps {
  initialData?: DepartmentObjType | undefined;
  onUpdate?: () => void;
}

const DepartmentForm = ({ initialData, onUpdate }: DepartmentFormProps) => {
  const methods = useForm({
    mode: 'onBlur',
    resolver: zodResolver(departmentSchema),
    defaultValues: initialData,
  });

  const { data, isLoading } = useGetActiveWorkingFieldList();

  const { mutateAsync: createDepartment, isPending: creating } =
    useCreateDepartment();

  const { mutateAsync: updateDepartment, isPending: updating } =
    useUpdateDepartment(initialData?.id || '');

  const initialWorkingField =
    initialData?.workingFields?.map((field: Record<string, unknown>) => ({
      label: safeString(field.fieldName),
      value: safeString(field.id),
      ...field,
    })) || [];

  console.log('Initial data: ', initialData);

  const workingFieldOptions = useMemo(() => {
    const result = toArray<WorkingFieldObject>(data);
    return result.map((item) => ({
      ...item,
      label: safeString(item.fieldName),
      value: safeString(item.id),
    }));
  }, [data]);

  const {
    handleSubmit,
    register,
    control,
    reset,
    formState: { errors },
  } = methods;

  const onSubmit = async (data: any) => {
    try {
      if (initialData) {
        const response = await updateDepartment(data);
        reset();
        onUpdate && onUpdate();
        toast.success('Department updated successfully');
        return response;
      }
      const response = await createDepartment(data);
      reset();
      onUpdate && onUpdate();
      toast.success('Department created successfully');
      return response;
    } catch (error) {
      toast.error(`Failed to ${initialData ? 'update' : 'create'} department`);
    }
  };

  return (
    <div className='bg-white rounded p-10 w-150'>
      <form onSubmit={handleSubmit(onSubmit)}>
        <div className='flex flex-col gap-2 '>
          <TextInputField
            label='Department Name'
            register={register}
            id='departmentName'
            errors={errors}
            placeholder='Nhập department name'
          />
          <TextInputField
            label='Department Code'
            register={register}
            id='departmentCode'
            errors={errors}
            placeholder='Nhập department code'
          />
          <TextInputField
            label='Description'
            register={register}
            id='description'
            type='textarea'
            errors={errors}
            placeholder='Nhập description'
          />
          <TextInputField
            label='Icon SVG'
            register={register}
            id='iconSvg'
            type='url'
            errors={errors}
            placeholder='Nhập icon SVG'
          />
          <Controller
            name='workingFields'
            control={control}
            render={({ field }) => (
              <ManualCustomCombobox
                {...field}
                id='workingFields'
                label='Working Fields'
                multiple
                defaultValue={initialWorkingField || []}
                isLoading={isLoading}
                placeholder='Select working fields'
                options={workingFieldOptions}
                updateType={{ type: 'object' }}
                errors={errors?.workingFields?.message as string | null}
              />
            )}
          />
          <br />
          <RadioInputField
            control={control}
            name='active'
            options={[
              { label: 'Active', value: 'true' },
              { label: 'Inactive', value: 'false' },
            ]}
            value={initialData?.active ? 'true' : 'false'}
            error={errors?.active?.message as string | null}
          />
          <br />
          <RadioInputField
            label='Bộ phận cần bảo mật'
            control={control}
            name='isSecurity'
            options={[
              { label: 'Private', value: 'true' },
              { label: 'Public', value: 'false' },
            ]}
            value='false'
            error={errors?.isSecurity?.message as string | null}
          />
        </div>
        <Button
          disabled={updating || creating}
          type='submit'
          className='mt-4'
          variant={'positive'}
        >
          {initialData ? 'Update Department' : 'Create Department'}
        </Button>
      </form>
    </div>
  );
};

export default DepartmentForm;
