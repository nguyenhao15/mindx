import { useState } from 'react';
import type { UserResponseObjectType } from '@/modules/core/auth/schemas/userSchema';

import { Button } from '../../../../components/ui/button';

import { updatePassword } from '@/modules/core/auth/queries/authAction';
import toast from 'react-hot-toast';
import ProfileHeader from '../components/ProfileHeader';
import ProfileSummary from '../components/ProfileSummary';
import UpdatePassword from '../components/UpdatePassword';
import ProfileDetails from '../components/ProfileDetails';
import { getMainPosition } from '../components/profileUtils';

const UserProfile = ({ user }: { user: UserResponseObjectType }) => {
  const [showUpdatePassword, setShowUpdatePassword] = useState(false);
  const profileData = user;
  const mainWorkProfile = getMainPosition(profileData.workProfileList);

  const handlePasswordUpdate = async (data: {
    currentPassword: string;
    newPassword: string;
  }) => {
    // Implement password update logic here, e.g., call an API endpoint
    try {
      await updatePassword(data);
      toast.success('Cập nhật mật khẩu thành công');
    } catch (error) {
      throw error; // Let the error be handled by the calling component
    }
  };

  return (
    <section className='min-h-screen bg-linear-to-br from-slate-50 via-sky-50 to-emerald-50 p-4 sm:p-6 lg:p-10'>
      <div className='mx-auto max-w-6xl space-y-6'>
        <ProfileHeader profileData={profileData} />
        <div className='flex justify-end'>
          <Button
            type='button'
            variant={showUpdatePassword ? 'outline' : 'brand'}
            onClick={() => setShowUpdatePassword((current) => !current)}
          >
            {showUpdatePassword ? 'Hide Change Password' : 'Change Password'}
          </Button>
        </div>
        <ProfileSummary
          profileData={profileData}
          mainWorkProfile={mainWorkProfile}
        />
        {showUpdatePassword && (
          <UpdatePassword onSubmitPassword={handlePasswordUpdate} />
        )}
        <ProfileDetails profileData={profileData} />
      </div>
    </section>
  );
};

export default UserProfile;
