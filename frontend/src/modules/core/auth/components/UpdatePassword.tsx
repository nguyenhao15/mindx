import {
  updatePasswordSchema,
  type UpdatePasswordDTO,
} from '@/modules/core/auth/schemas/userSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { FormProvider, useForm } from 'react-hook-form';
import toast from 'react-hot-toast';

import TextInputField from '@/components/input-elements/TextInputField';
import { Button } from '@/components/ui/button';
import { useState } from 'react';
import ErrorCatchComponent from '@/components/shared/ErrorCatchComponent';

type UpdatePasswordProps = {
  onSubmitPassword?: (data: UpdatePasswordDTO) => Promise<void> | void;
};

const UpdatePassword = ({ onSubmitPassword }: UpdatePasswordProps) => {
  const [error, setError] = useState<string | null>(null);
  const methods = useForm<UpdatePasswordDTO>({
    mode: 'onBlur',
    resolver: zodResolver(updatePasswordSchema),
    defaultValues: {
      currentPassword: '',
      newPassword: '',
      confirmNewPassword: '',
    },
  });

  const {
    handleSubmit,
    register,
    reset,
    formState: { errors, isSubmitting },
  } = methods;

  const onSubmit = async (data: UpdatePasswordDTO) => {
    try {
      await onSubmitPassword?.(data);
      reset();
      setError(null);
    } catch (error: any) {
      console.error('Error updating password:', error);
      setError(error);
      toast.error('Không thể cập nhật mật khẩu');
    }
  };

  return (
    <div className='rounded-3xl bg-white p-6 shadow-sm ring-1 ring-slate-200 sm:p-8'>
      <div className='mb-6'>
        <h2 className='mb-2 text-2xl font-semibold text-slate-900'>
          Update Password
        </h2>
        <p className='text-sm text-slate-500'>
          Nhập mật khẩu hiện tại và tạo mật khẩu mới an toàn cho tài khoản.
        </p>
      </div>

      <FormProvider {...methods}>
        <form className='space-y-5' onSubmit={handleSubmit(onSubmit)}>
          <ErrorCatchComponent error={error} />
          <TextInputField
            id='currentPassword'
            label='Current Password'
            type='password'
            required
            register={register}
            errors={errors}
            placeholder='Enter current password'
          />

          <TextInputField
            id='newPassword'
            label='New Password'
            type='password'
            required
            register={register}
            errors={errors}
            placeholder='Enter new password'
          />

          <TextInputField
            id='confirmNewPassword'
            label='Re-type New Password'
            type='password'
            required
            register={register}
            errors={errors}
            placeholder='Re-enter new password'
          />

          <div className='rounded-2xl bg-slate-50 p-4 text-sm text-slate-600 ring-1 ring-slate-200'>
            Mật khẩu mới phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường và
            số.
          </div>

          <div className='flex justify-end gap-3'>
            <Button
              type='button'
              variant='outline'
              onClick={() => reset()}
              disabled={isSubmitting}
            >
              Reset
            </Button>
            <Button
              type='submit'
              variant='positive'
              disabled={isSubmitting}
              className='cursor-pointer'
            >
              {isSubmitting ? 'Updating...' : 'Update Password'}
            </Button>
          </div>
        </form>
      </FormProvider>
    </div>
  );
};

export default UpdatePassword;
