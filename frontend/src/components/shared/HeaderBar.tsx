import { MENU_ITEM } from '@/constants/app-const';

import { FaFileAlt } from 'react-icons/fa';
import { Link } from 'react-router-dom';

import { useLogOut } from '@/hookQueries/useAuthentication';
import { LogOut } from 'lucide-react';
import { useAuthStore } from '@/stores/AuthStore';

const HeaderBar = () => {
  const userInfo = useAuthStore((state) => state.user);
  const pathname = window.location.pathname;

  const { mutateAsync: logOutAction } = useLogOut();

  const handleLogOut = async () => {
    try {
      await logOutAction();
    } catch (error) {
      console.error('Error logging out:', error);
    }
  };

  return (
    <header className='flex items-center justify-between whitespace-nowrap border-b border-solid border-slate-200 dark:border-slate-800 bg-white dark:bg-slate-900 px-10 py-3 sticky top-0 z-50'>
      <div className='flex items-center gap-8'>
        <div className='flex items-center gap-4 text-slate-900 dark:text-white'>
          <div className='size-8 flex items-center justify-center bg-brand-primary rounded-lg text-white'>
            <FaFileAlt className='size-4' />
          </div>
          <h2 className='text-lg my-auto items-center justify-center font-bold leading-tight tracking-tight'>
            Knowledge Hub
          </h2>
        </div>
        <nav className='flex items-center gap-6 text-slate-700 dark:text-slate-300'>
          {MENU_ITEM.map((item) => (
            <Link
              key={item.path}
              to={item.path}
              className={`text-sm font-medium hover:text-slate-900 dark:hover:text-white transition ${
                pathname === item.path ? 'text-slate-900 dark:text-white' : ''
              }`}
            >
              {item.label}
            </Link>
          ))}
        </nav>
      </div>

      <div className='flex items-center gap-4'>
        <div className='bg-brand-primary/20 p-2 rounded font-bold flex-col'>
          {userInfo?.fullName || userInfo?.staffId || 'User'}
        </div>
        <div className=''>
          <button
            title='Đăng xuất'
            className='flex items-center cursor-pointer hover:bg-amber-200 p-3 flex-row gap-2 rounded-lg text-sm font-medium text-slate-700 transition'
            onClick={handleLogOut}
          >
            <LogOut />
          </button>
        </div>
      </div>
    </header>
  );
};

export default HeaderBar;
