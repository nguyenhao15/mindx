import {
  activatePasswordSchema,
  type ActivatePasswordDTO,
} from '@/modules/core/auth/schemas/userSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { FormProvider, useForm } from 'react-hook-form';
import toast from 'react-hot-toast';
import { useState } from 'react';

import TextInputField from '../input-elements/TextInputField';
import ErrorCatchComponent from '../shared/ErrorCatchComponent';
import { Button } from '../ui/button';
import { useActivateAccount } from '@/modules/core/auth/hooks/useAuthentication';

const ActivatePassword = () => {
  const [error, setError] = useState<string | null>(null);

  const methods = useForm<ActivatePasswordDTO>({
    mode: 'onBlur',
    resolver: zodResolver(activatePasswordSchema),
    defaultValues: {
      newPassword: '',
      confirmPassword: '',
    },
  });

  const {
    mutateAsync: activateAccount,

    error: activateError,
  } = useActivateAccount();

  const {
    handleSubmit,
    register,
    reset,
    formState: { errors, isSubmitting },
  } = methods;

  const onSubmit = async (data: ActivatePasswordDTO) => {
    try {
      const { newPassword } = data;
      await activateAccount(newPassword);
      reset();
      setError(null);
      toast.success('Kích hoạt mật khẩu thành công');
    } catch (err: any) {
      console.error('Error activating password:', err);
      setError(err);
      toast.error('Không thể kích hoạt mật khẩu');
    }
  };

  return (
    <div className='rounded-3xl bg-white p-6 shadow-sm ring-1 ring-slate-200 sm:p-8'>
      <div className='mb-6'>
        <h2 className='mb-2 text-2xl font-semibold text-slate-900'>
          Kích hoạt mật khẩu
        </h2>
        <p className='text-sm text-slate-500'>
          Tạo mật khẩu mới để kích hoạt tài khoản của bạn.
        </p>
      </div>

      <FormProvider {...methods}>
        <form className='space-y-5' onSubmit={handleSubmit(onSubmit)}>
          <ErrorCatchComponent error={error} />

          <TextInputField
            id='newPassword'
            label='Mật khẩu mới'
            type='password'
            isLoading={isSubmitting}
            required
            register={register}
            errors={errors}
            placeholder='Nhập mật khẩu mới'
          />

          <TextInputField
            id='confirmPassword'
            label='Xác nhận mật khẩu mới'
            type='password'
            isLoading={isSubmitting}
            required
            register={register}
            errors={errors}
            placeholder='Nhập lại mật khẩu mới'
          />

          <div className='rounded-2xl bg-slate-50 p-4 text-sm text-slate-600 ring-1 ring-slate-200'>
            Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường và số.
          </div>

          <div className='flex justify-end gap-3'>
            <Button
              type='button'
              variant='outline'
              onClick={() => reset()}
              disabled={isSubmitting}
            >
              Đặt lại
            </Button>
            <Button
              type='submit'
              variant='positive'
              disabled={isSubmitting}
              className='cursor-pointer'
            >
              {isSubmitting ? 'Đang kích hoạt...' : 'Kích hoạt mật khẩu'}
            </Button>
          </div>
        </form>
      </FormProvider>
    </div>
  );
};

export default ActivatePassword;
