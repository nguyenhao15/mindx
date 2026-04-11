import { basementSchema } from '@/modules/documentation/validations/basementSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { FormProvider, useForm } from 'react-hook-form';
import TextInputField from '../input-elements/TextInputField';
import RadioInputField from '../shared/RadioInputField';
import { Button } from '../ui/button';
import {
  useCreateBasement,
  useUpdateBasement,
} from '@/modules/documentation/hookQueries/useBasementHook';
import toast from 'react-hot-toast';
import { useEffect, useState } from 'react';
import ErrorCatchComponent from '../shared/ErrorCatchComponent';

interface BasementFormProps {
  updateMode?: boolean;
  initialValues: any; // Thay 'any' bằng kiểu dữ liệu cụ thể nếu có
  onSubmit: () => void; // Thay 'any' bằng kiểu dữ liệu cụ thể nếu có
}

const BasementForm = ({
  updateMode,
  initialValues,
  onSubmit,
}: BasementFormProps) => {
  const [isLoading, setIsLoading] = useState(false);
  const methods = useForm({
    mode: 'onBlur',
    resolver: zodResolver(basementSchema),
    defaultValues: initialValues || {},
  });

  const {
    register,
    handleSubmit,
    control,
    formState: { errors },
  } = methods;

  const {
    mutateAsync: createBasement,
    isPending: isCreating,
    error: createError,
  } = useCreateBasement();

  const {
    mutateAsync: updateBasement,
    isPending: isUpdating,
    error: updateError,
  } = useUpdateBasement(initialValues?.id);

  const onSubmitHandler = async (data: any) => {
    try {
      if (updateMode) {
        await updateBasement(data);
      } else {
        await createBasement(data);
      }
      toast.success(
        updateMode
          ? 'Basement updated successfully!'
          : 'Basement created successfully!',
      );
      onSubmit();
    } catch (error) {
      console.log('Errors in create/update basement: ', error);

      toast.error(
        updateMode
          ? 'Failed to update basement.'
          : 'Failed to create basement.',
      );
    }
  };

  useEffect(() => {
    setIsLoading(isCreating || isUpdating);
  }, [isCreating, isUpdating]);

  return (
    <div className='flex flex-col p-4 md:px-10 bg-white rounded shadow space-y-4 min-w-100'>
      <div>
        <h2 className='text-2xl font-bold text-slate-800'>
          {updateMode ? 'Update Basement' : 'Create New Basement'}
        </h2>
        <p className='text-sm text-slate-500 mt-1'>
          {updateMode
            ? 'Cập nhật thông tin cơ sở'
            : 'Điền thông tin để tạo mới cơ sở'}
        </p>
        <div className='mt-2 h-1 w-16 bg-blue-600 rounded' />
      </div>
      {(createError || updateError) && (
        <ErrorCatchComponent error={createError || updateError} />
      )}
      <div className='p-4 bg-slate-50 rounded border border-slate-200'>
        <h3 className='text-lg font-semibold text-slate-700 mb-2'>
          Thông tin cơ sở
        </h3>
        <p className='text-sm text-slate-500 mb-4'>
          Điền đầy đủ thông tin để tạo hoặc cập nhật cơ sở
        </p>
      </div>
      <FormProvider {...methods}>
        <form onSubmit={handleSubmit(onSubmitHandler)} className='space-y-4'>
          <div className='grid grid-cols-1 md:grid-cols-2 gap-4'>
            <TextInputField
              label='Basement Name'
              register={register}
              id='buFullName'
              isLoading={isLoading}
              placeholder='Nhập tên cơ sở'
              errors={errors}
            />
            <TextInputField
              label='Basement Short Name'
              register={register}
              isLoading={isLoading}
              id='buId'
              placeholder='Nhập tên viết tắt cơ sở'
              errors={errors}
            />
            <TextInputField
              label='Mã kế toán'
              isLoading={isLoading}
              register={register}
              id='accountantCode'
              placeholder='Nhập mã kế toán'
              errors={errors}
            />
            <TextInputField
              label='Khu vực'
              isLoading={isLoading}
              register={register}
              id='areaFullName'
              placeholder='Nhập khu vực'
              errors={errors}
            />
            <TextInputField
              label='Loại cơ sở'
              register={register}
              isLoading={isLoading}
              id='buType'
              placeholder='Nhập loại cơ sở'
              errors={errors}
            />
            <TextInputField
              label='Diện tích cơ sở'
              register={register}
              isLoading={isLoading}
              id='size'
              placeholder='Nhập diện tích cơ sở'
              errors={errors}
            />
            <TextInputField
              label='Thành phố'
              register={register}
              isLoading={isLoading}
              id='city'
              placeholder='Nhập thành phố'
              errors={errors}
            />
            <TextInputField
              label='Địa chỉ'
              register={register}
              type='textarea'
              isLoading={isLoading}
              id='address'
              placeholder='Nhập địa chỉ'
              errors={errors}
            />
            <RadioInputField
              label='Trạng thái'
              control={control}
              disabled={isLoading}
              name='active'
              options={[
                { label: 'Active', value: 'true' },
                { label: 'Inactive', value: 'false' },
              ]}
              value={initialValues?.active ? 'true' : 'false'}
            />
          </div>
          <Button
            disabled={isLoading}
            className='my-2 cursor-pointer w-full'
            type='submit'
            variant={'positive'}
          >
            {updateMode ? 'Update Basement' : 'Create Basement'}
          </Button>
        </form>
      </FormProvider>
    </div>
  );
};

export default BasementForm;
