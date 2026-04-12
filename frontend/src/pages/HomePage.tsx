import { MODULE_NAME } from '@/constants/module-const';
import { useLogOut } from '@/modules/core/auth/hooks/useAuthentication';
import { useAuthStore } from '@/modules/core/auth/store/AuthStore';
import { Link, useNavigate } from 'react-router-dom';

const HomePage = () => {
  const navigate = useNavigate();
  const user = useAuthStore((state) => state.user);
  const { mutateAsync: logout, isPending: isLoggingOut } = useLogOut();

  const handleLogout = async () => {
    try {
      await logout();
      navigate('/login', { replace: true });
    } catch (error) {
      console.error('Logout failed:', error);
    }
  };

  return (
    <section className='min-h-screen bg-linear-to-br from-slate-50 via-white to-cyan-50 px-6 py-10'>
      <div className='mx-auto max-w-6xl space-y-8'>
        <header className='rounded-2xl border border-slate-200 bg-white/90 p-6 shadow-sm'>
          <div className='flex flex-col gap-6 lg:flex-row lg:items-center lg:justify-between'>
            <div>
              <h1 className='text-3xl font-bold tracking-tight text-slate-900 sm:text-4xl'>
                Xin chao, {user?.fullName || 'ban'}
              </h1>
              <p className='mt-2 text-base text-slate-600'>
                Lựa chọn module bạn muốn truy cập
              </p>
            </div>

            <button
              type='button'
              onClick={handleLogout}
              disabled={isLoggingOut}
              className='inline-flex cursor-pointer items-center justify-center rounded-xl bg-slate-900 px-5 py-3 text-sm font-semibold text-white transition hover:bg-slate-700 disabled:cursor-not-allowed disabled:opacity-60'
            >
              {isLoggingOut ? 'Đang đăng xuất...' : 'Đăng xuất'}
            </button>
          </div>

          <div className='mt-5 grid grid-cols-1 gap-3 sm:grid-cols-2 lg:grid-cols-4'>
            <div className='rounded-xl border border-slate-200 bg-slate-50 p-3'>
              <p className='text-xs font-medium uppercase tracking-wide text-slate-500'>
                Mã nhân viên
              </p>
              <p className='mt-1 text-sm font-semibold text-slate-900'>
                {user?.staffId || '-'}
              </p>
            </div>

            <div className='rounded-xl border border-slate-200 bg-slate-50 p-3'>
              <p className='text-xs font-medium uppercase tracking-wide text-slate-500'>
                Vai trò
              </p>
              <p className='mt-1 text-sm font-semibold text-slate-900'>
                {user?.systemRole || '-'}
              </p>
            </div>

            <div className='rounded-xl border border-slate-200 bg-slate-50 p-3 sm:col-span-2'>
              <p className='text-xs font-medium uppercase tracking-wide text-slate-500'>
                Email
              </p>
              <p className='mt-1 break-all text-sm font-semibold text-slate-900'>
                {user?.email || '-'}
              </p>
            </div>
          </div>
        </header>

        <div className='grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-3'>
          {MODULE_NAME.map((module) => (
            <Link
              key={module.path}
              to={module.path.startsWith('/') ? module.path : `/${module.path}`}
              className='flex flex-col group rounded-2xl border border-slate-200 bg-white p-5 shadow-sm transition-all hover:-translate-y-1 hover:border-cyan-300 hover:shadow-lg focus:outline-none focus:ring-2 focus:ring-cyan-400'
            >
              <div className='flex items-start justify-between'>
                <h2 className='text-xl font-semibold text-slate-900'>
                  {module.title}
                </h2>
                <span className='rounded-full bg-slate-100 px-2.5 py-1 text-xs font-medium text-slate-600'>
                  Open
                </span>
              </div>

              <p className='mt-3 text-sm text-slate-500 flex-1'>
                {module.desc}
              </p>

              <div className='mt-5 text-sm font-semibold text-cyan-700 group-hover:text-cyan-900'>
                Go to module
              </div>
            </Link>
          ))}
        </div>
      </div>
    </section>
  );
};

export default HomePage;
