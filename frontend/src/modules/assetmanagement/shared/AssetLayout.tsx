import Loader from '@/components/shared/Loader';
import { useGetUserInfo } from '@/modules/core/auth/hooks/useAuthentication';
import { Outlet } from 'react-router-dom';
import NavMenu from '../maintances/components/shared/NavMenu';

const AssetLayout = () => {
  const { data: user, isLoading } = useGetUserInfo();

  if (isLoading && !user) return <Loader text={'Loading data...'} />;

  return (
    <div className='flex h-screen bg-slate-50 font-sans overflow-hidden'>
      <NavMenu />
      <main className='flex-1 overflow-y-auto'>
        <Outlet />
      </main>
    </div>
  );
};

export default AssetLayout;
