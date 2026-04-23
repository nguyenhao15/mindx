import { useAuthStore } from '@/modules/core/auth/store/AuthStore';
import React from 'react';

interface WelcomeCardProps {
  systemName: string;
  Icon?: React.ComponentType<React.SVGProps<SVGSVGElement>>;
}

const WelcomeCard = ({ systemName, Icon }: WelcomeCardProps) => {
  const user = useAuthStore((state) => state.user);
  const today = new Date().toLocaleDateString('vi-VN', {
    weekday: 'long',
    year: 'numeric',
    month: 'long',
    day: 'numeric',
  });

  return (
    <div
      className='rounded-2xl px-6 py-5 flex items-center justify-between gap-4 bg-primary'
      //   style={{ backgroundColor: theme.primary }}
    >
      <div className='flex flex-col gap-1'>
        <p className='text-sm font-medium opacity-70 bg-primaryFg'>{today}</p>
        <h1 className='text-xl font-bold bg-primaryFg'>
          Xin chào, {user?.fullName} 👋
        </h1>
        <p className='text-sm opacity-70 mt-0.5 bg-primaryFg'>
          Bảng điều khiển {systemName} — Cấu hình & Quản lý hệ thống
        </p>
      </div>

      <div
        className='hidden sm:flex items-center justify-center w-12 h-12 rounded-xl'
        style={{ backgroundColor: 'rgba(255,255,255,0.12)' }}
      >
        {Icon && <Icon className='w-6 h-6 bg-primaryFg' />}
      </div>
    </div>
  );
};

export default WelcomeCard;
