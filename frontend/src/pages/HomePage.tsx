import ModuleListComponents from '@/components/shared/ModuleListComponents';
import ProfileUser from '@/components/shared/ProfileUser';
import { useAuthStore } from '@/modules/core/auth/store/AuthStore';

const HomePage = () => {
  const activeProfileId = localStorage.getItem('profile_id');
  const user = useAuthStore((state) => state.user);
  const activeProfile = user?.workProfileList?.find(
    (profile) => profile.id === activeProfileId,
  );

  return (
    <section className='min-h-screen bg-linear-to-br from-slate-50 via-white to-cyan-50 px-6 py-10'>
      <div className='mx-auto max-w-6xl space-y-8'>
        {user && activeProfile && (
          <ProfileUser user={user} activeProfile={activeProfile} />
        )}
        <ModuleListComponents />
      </div>
    </section>
  );
};

export default HomePage;
