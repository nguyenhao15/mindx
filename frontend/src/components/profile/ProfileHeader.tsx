import type { UserResponseObjectType } from "@/validations/userSchema";


type ProfileHeaderProps = {
  profileData: UserResponseObjectType;
};

const ProfileHeader = ({ profileData }: ProfileHeaderProps) => {
  return (
    <header className='rounded-3xl bg-white/90 p-6 shadow-sm ring-1 ring-slate-200 backdrop-blur sm:p-8'>
      <p className='text-sm font-semibold uppercase tracking-[0.2em] text-sky-700'>
        Staff Profile
      </p>
      <div className='mt-3 flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between'>
        <div>
          <h1 className='mb-1 text-3xl font-bold text-slate-900 sm:text-4xl'>
            {profileData.fullName}
          </h1>
          <p className='text-sm text-slate-500 sm:text-base'>
            {profileData.email}
          </p>
        </div>
        <span className='inline-flex w-fit items-center rounded-full bg-sky-100 px-4 py-2 text-xs font-semibold uppercase tracking-wide text-sky-800 ring-1 ring-sky-200'>
          {profileData.systemRole}
        </span>
      </div>
    </header>
  );
};

export default ProfileHeader;
