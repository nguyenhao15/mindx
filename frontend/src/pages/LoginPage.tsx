import TextInputField from '@/components/input-elements/TextInputField';
import ErrorCatchComponent from '@/components/shared/ErrorCatchComponent';
import HeaderBar from '@/components/shared/HeaderBar';
import { Button } from '@/components/ui/button';
import { useLogin } from '@/modules/core/auth/hooks/useAuthentication';
import { motion } from 'framer-motion';
import { useForm } from 'react-hook-form';

const LoginPage = () => {
  const { mutateAsync: login, isPending: isLogging, error } = useLogin();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({
    mode: 'onBlur',
  });

  const handleLogin = async (data: any) => {
    try {
      await login(data);
    } catch (error) {
      console.error('Login failed: ', error);
    }
  };

  return (
    <div
      className='flex flex-col justify-center sm:px-6 lg:px-8 h-screen w-full
    bg-linear-to-r from-brand-primary to-brand-primary/5'
    >
      <motion.div
        className='sm:mx-auto sm:w-full sm:max-w-md'
        initial={{ opacity: 0, y: -20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.8 }}
      ></motion.div>
      <motion.div
        className='mt-8 sm:mx-auto sm:w-full sm:max-w-md'
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.8, delay: 0.2 }}
      >
        <div className=' bg-white rounded flex flex-col gap-2 p-4 justify-center items-center'>
          <form
            onSubmit={handleSubmit(handleLogin)}
            className='sm:w-112.5 w-90 shadow-custom py-8 sm:px-8 px-4 rounded-md'
            action=''
          >
            <h2 className='mt-6 text-center text-3xl font-extrabold text-black'>
              Đăng nhập
            </h2>
            <hr className='mt-2 mb-5 text-black' />
            <ErrorCatchComponent error={error} />

            <div className='flex mt-2 flex-col gap-3 '>
              <TextInputField
                label='Mã nhân viên'
                required
                id='username'
                type='text'
                message='*Staff Id is required'
                placeholder='Mã nhân viên'
                register={register}
                errors={errors}
              />
              <TextInputField
                label='Mật khẩu'
                required
                id='password'
                type='password'
                message='*Password is required'
                placeholder='Mật khẩu'
                register={register}
                errors={errors}
              />
            </div>
            <Button
              disabled={isLogging}
              type='submit'
              className='w-full mt-6 cursor-pointer'
            >
              <span className='text-sm font-medium'>Đăng nhập</span>
            </Button>
          </form>
        </div>
      </motion.div>
    </div>
  );
};

export default LoginPage;
