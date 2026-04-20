import {
  userCreateSchema,
  WorkProfileCreate,
} from '@/modules/core/auth/schemas/userSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import React from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import UserForm from './UserForm';
import { useAddUser } from '../../hooks/useAdminHook';
import toast from 'react-hot-toast';
import { Button } from '@/components/ui/button';

const CreateUserComponent = () => {
  const methods = useForm({
    mode: 'onBlur',
    resolver: zodResolver(userCreateSchema),
  });

  const {
    handleSubmit,
    register,
    formState: { errors },
  } = methods;

  const { mutateAsync: createUser, isPending, isError, error } = useAddUser();

  const onSubmit = async (data: any) => {
    const staffProfie = WorkProfileCreate.parse(data);
    const payload = {
      staffProfileRequestDto: staffProfie,
      ...data,
    };
    try {
      await createUser(payload);
      toast.success('User created successfully');
    } catch (error) {
      console.error('Error saving user:', error);
      toast.error('Failed to save user');
    }
  };

  return (
    <div>
      <FormProvider {...methods}>
        <form
          onSubmit={handleSubmit(onSubmit)}
          className=' bg-white p-6 rounded-lg shadow-md'
        >
          <UserForm updateMode={false} initialUser={null} />
          <div className='sticky bottom-0 bg-white p-2 m-2 items-center justify-end w-full flex gap-2 border-t border-gray-200'>
            <Button
              variant='positive'
              onClick={() => window.history.back()}
              className='mb-4'
            >
              Tạo người dùng mới
            </Button>
          </div>
        </form>
      </FormProvider>
    </div>
  );
};

export default CreateUserComponent;
