import { Link } from 'react-router-dom';

const NotFoundComponent = () => {
  return (
    <div className='relative flex my-auto mx-auto p-2 max-h-screen items-center justify-center overflow-hidden bg-slate-50 px-6'>
      <div className='pointer-events-none absolute inset-0'>
        <div className='absolute -left-10 top-10 h-40 w-40 rounded-full bg-cyan-200/50 blur-3xl' />
        <div className='absolute right-0 top-1/3 h-56 w-56 rounded-full bg-emerald-200/40 blur-3xl' />
        <div className='absolute bottom-10 left-1/3 h-48 w-48 rounded-full bg-blue-200/40 blur-3xl' />
      </div>

      <div className='relative z-10 w-full max-w-xl rounded-2xl border border-slate-200 bg-white/80 p-8 text-center shadow-xl backdrop-blur'>
        <p className='text-sm font-semibold uppercase tracking-[0.35em] text-slate-500'>
          Error 404
        </p>
        <h1 className='mt-3 text-5xl font-extrabold text-slate-900'>
          Trang không tồn tại
        </h1>
        <p className='mt-4 text-base leading-relaxed text-slate-600'>
          Liên kết bạn truy cập có thể đã bị thay đổi hoặc không còn khả dụng.
          Vui lòng quay về trang chủ để tiếp tục.
        </p>

        <div className='mt-8 flex flex-col gap-3 sm:flex-row sm:justify-center'>
          <Link
            to='/'
            className='rounded-lg bg-slate-900 px-5 py-3 text-sm font-semibold text-white transition hover:bg-slate-700'
          >
            Về trang chủ
          </Link>
          <Link
            to='/login'
            className='rounded-lg border border-slate-300 bg-white px-5 py-3 text-sm font-semibold text-slate-700 transition hover:bg-slate-100'
          >
            Đến trang đăng nhập
          </Link>
        </div>
      </div>
    </div>
  );
};

export default NotFoundComponent;
