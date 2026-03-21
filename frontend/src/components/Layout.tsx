import HeaderBar from './shared/HeaderBar';
import { Outlet } from 'react-router-dom';
import Loader from './shared/Loader';
import { useGetUserInfo } from '@/hookQueries/useAuthentication';

const Layout = () => {
  const { data: user, isLoading } = useGetUserInfo();

  if (isLoading && !user)
    return (
      <div className='min-h-screen mx-auto my-auto justify-center items-center flex'>
        <Loader text={'Loading data...'} />
      </div>
    );
  // Nếu có lỗi khi lấy thông tin user, xem như chưa đăng nhập

  return (
    <div className='flex-col flex h-screen'>
      <HeaderBar />
      <div className='flex-1'>
        <Outlet />
      </div>
    </div>
  );
};

export default Layout;
