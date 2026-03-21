import type { UserResponseObjectType } from '@/validations/userSchema';
import ProfileDetails from '../components/profile/ProfileDetails';
import ProfileHeader from '../components/profile/ProfileHeader';
import ProfileSummary from '../components/profile/ProfileSummary';
import { getMainPosition } from '../components/profile/profileUtils';

const UserProfile = ({ user }: { user: UserResponseObjectType }) => {
  const profileData = user || {};
  const mainWorkProfile = getMainPosition(profileData.workProfileList);

  return (
    <section className='min-h-screen bg-linear-to-br from-slate-50 via-sky-50 to-emerald-50 p-4 sm:p-6 lg:p-10'>
      <div className='mx-auto max-w-6xl space-y-6'>
        <ProfileHeader profileData={profileData} />
        <ProfileSummary
          profileData={profileData}
          mainWorkProfile={mainWorkProfile}
        />
        <ProfileDetails profileData={profileData} />
      </div>
    </section>
  );
};

export default UserProfile;
