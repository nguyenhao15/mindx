import { useLogOut } from '@/modules/core/auth/hooks/useAuthentication';
import toast from 'react-hot-toast';

const LockAccountPage = () => {
  const { mutateAsync } = useLogOut();
  const handleAccountLogout = async () => {
    try {
      await mutateAsync();
      toast.success('Đăng xuất thành công');
    } catch (error) {
      console.error('Error during logout:', error);
    }
  };

  return (
    <div className='relative flex min-h-screen items-center justify-center overflow-hidden bg-slate-50 px-6'>
      <div className='pointer-events-none absolute inset-0'>
        <div className='absolute left-0 top-10 h-40 w-40 rounded-full bg-rose-200/50 blur-3xl' />
        <div className='absolute right-0 top-1/3 h-56 w-56 rounded-full bg-amber-200/40 blur-3xl' />
        <div className='absolute bottom-10 left-1/3 h-48 w-48 rounded-full bg-orange-200/40 blur-3xl' />
      </div>

      <div className='relative z-10 w-full max-w-xl rounded-2xl border border-slate-200 bg-white/85 p-8 text-center shadow-xl backdrop-blur'>
        <p className='text-sm font-semibold uppercase tracking-[0.35em] text-slate-500'>
          Account Locked
        </p>
        <h1 className='mt-3 text-4xl font-extrabold text-brand-primary sm:text-5xl'>
          Tài khoản của bạn đã bị khóa
        </h1>
        <p className='mt-4 text-base leading-relaxed text-slate-600'>
          Tài khoản của bạn hiện đang bị khóa. Vui lòng liên hệ quản trị viên để
          được hỗ trợ mở khóa tài khoản.
        </p>

        <div className='mt-8 flex flex-col gap-3 sm:flex-row sm:justify-center'>
          <button
            type='button'
            onClick={handleAccountLogout}
            className='rounded-lg cursor-pointer bg-slate-900 px-5 py-3 text-sm font-semibold text-white transition hover:bg-slate-700'
          >
            Đăng xuất
          </button>
        </div>
      </div>
    </div>
  );
};

export default LockAccountPage;
