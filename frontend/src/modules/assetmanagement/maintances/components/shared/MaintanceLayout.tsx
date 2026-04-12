import Loader from '@/components/shared/Loader';
import { useGetUserInfo } from '@/modules/core/auth/hooks/useAuthentication';
import React from 'react';
import { Outlet } from 'react-router-dom';

const MaintanceLayout = () => {
  const { data: user, isLoading } = useGetUserInfo();

  if (isLoading && !user) return <Loader text={'Loading data...'} />;

  return (
    <div className='flex-col flex h-screen'>
      <div className='flex-1'>
        <Outlet />
      </div>
    </div>
  );
};

export default MaintanceLayout;
