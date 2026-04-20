import React, { useEffect } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { useGetUserById, useUpdateUser } from '../../hooks/useAdminHook';
import { userManagementSchema } from '@/modules/core/auth/schemas/userSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import UserForm from './UserForm';
import { Button } from '@/components/ui/button';
import Loader from '@/components/shared/Loader';
import ErrorCatchComponent from '@/components/shared/ErrorCatchComponent';
import toast from 'react-hot-toast';

interface UpdateUserComponentProps {
  staffId: string; // Tùy chỉnh kiểu dữ liệu của staffId nếu có thể
}

const UpdateUserComponent = ({ staffId }: UpdateUserComponentProps) => {
  const { data, isLoading, error, isSuccess } = useGetUserById(staffId);

  const { mutateAsync: update, isLoading: isUpdating } = useUpdateUser(staffId);

  const methods = useForm({
    mode: 'onBlur',
    resolver: zodResolver(userManagementSchema),
    defaultValues: data || {},
  });

  const { reset, handleSubmit } = methods;

  useEffect(() => {
    if (isSuccess && data) {
      reset(data);
    }
  }, [isSuccess, data, reset]);

  const onSubmit = async (formData: any) => {
    try {
      await update(formData);
      toast.success('Cập nhật người dùng thành công!');
    } catch (error) {
      toast.error('Cập nhật người dùng thất bại. Vui lòng thử lại.');
    }
  };

  if (isLoading) return <Loader />;

  if (error) return <ErrorCatchComponent error={error} />;

  if (!data) return null;

  return (
    <div>
      <FormProvider {...methods}>
        <form
          onSubmit={handleSubmit(onSubmit)}
          className=' bg-white p-6 rounded-lg shadow-md'
        >
          <UserForm updateMode={true} initialUser={data} />
          <div className='sticky bottom-0 bg-white p-2 m-2 items-center justify-end w-full flex gap-2 border-t border-gray-200'>
            <Button variant='positive' disabled={isUpdating} className='mb-4'>
              Cập nhật thông tin người dùng
            </Button>
          </div>
        </form>
      </FormProvider>
    </div>
  );
};

export default UpdateUserComponent;
