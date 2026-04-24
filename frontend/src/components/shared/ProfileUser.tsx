import { useLogOut } from '@/modules/core/auth/hooks/useAuthentication';
import { useNavigate } from 'react-router-dom';
import { Button } from '../ui/button';

function InfoItem({ label, value }: { label: string; value: string }) {
  return (
    <div className='flex flex-col rounded-xl border border-slate-200 bg-primary p-5 gap-2'>
      <p className='text-md flex-1 font-medium uppercase tracking-wide text-slate-100'>
        {label}
      </p>
      <p className='mt-1 text-sm font-semibold text-slate-200'>{value}</p>
    </div>
  );
}

const ProfileUser = ({
  user,
  activeProfile,
}: {
  user: any;
  activeProfile: any;
}) => {
  const navigate = useNavigate();
  const { mutateAsync: logout, isPending: isLoggingOut } = useLogOut();
  const handleDeleteProfile = () => {
    localStorage.removeItem('profile_id');
    window.location.reload();
  };

  const handleLogout = async () => {
    try {
      await logout();
      navigate('/login', { replace: true });
    } catch (error) {
      console.error('Logout failed:', error);
    }
  };
  return (
    <header className='rounded-2xl border border-slate-200 bg-white/90 p-6 shadow-sm'>
      <div className='flex flex-col gap-6 lg:flex-row lg:items-center lg:justify-between'>
        <div>
          <h1 className='text-3xl font-bold tracking-tight text-slate-900 sm:text-4xl'>
            Xin chào, {user?.fullName || 'anh/chị'}
          </h1>
          <p className='mt-2 text-base text-slate-600'>
            {activeProfile.positionName || 'Chưa có vị trí công việc'} -{' '}
            {activeProfile.departmentName || 'Chưa có phòng ban'}
          </p>
        </div>

        <div className='flex flex-row gap-2'>
          <Button
            type='button'
            onClick={handleDeleteProfile}
            className='inline-flex cursor-pointer items-center justify-center rounded-xl bg-slate-900 px-5 py-3 text-sm font-semibold text-white transition hover:bg-slate-700'
          >
            Đổi hồ sơ công việc
          </Button>
          <Button
            type='button'
            onClick={handleLogout}
            disabled={isLoggingOut}
            className='inline-flex cursor-pointer items-center justify-center rounded-xl bg-slate-900 px-5 py-3 text-sm font-semibold text-white transition hover:bg-slate-700 disabled:cursor-not-allowed disabled:opacity-60'
          >
            {isLoggingOut ? 'Đang đăng xuất...' : 'Đăng xuất'}
          </Button>
        </div>
      </div>

      <div className='mt-5 grid grid-cols-1 gap-3 sm:grid-cols-2 lg:grid-cols-3'>
        <InfoItem label='Mã nhân viên' value={user?.staffId || '-'} />
        <InfoItem label='Email' value={user?.email || '-'} />
        <InfoItem
          label='Hồ sơ công việc'
          value={user?.workProfileList?.length.toString() || '0'}
        />
      </div>

      {activeProfile.buAllowedList.length > 0 && (
        <div className='mt-6 rounded-xl border border-slate-200 bg-slate-50 p-5'>
          <h3 className='text-base font-semibold text-slate-900'>
            Danh sách cơ sở được phép truy cập
          </h3>
          <p className='mt-2 text-sm font-medium text-slate-700'>
            {/* Placeholder: danh sách cơ sở dạng chuỗi sẽ được thêm vào */}
            {activeProfile.buAllowedList &&
              activeProfile.buAllowedList.join(', ')}
          </p>
        </div>
      )}
    </header>
  );
};

export default ProfileUser;
