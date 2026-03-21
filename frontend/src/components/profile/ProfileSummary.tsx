import type {
  UserResponseObjectType,
  WorkProfileType,
} from '@/validations/userSchema';

import { getStatusClasses } from './profileUtils';

type ProfileSummaryProps = {
  profileData: UserResponseObjectType;
  mainWorkProfile: WorkProfileType;
};

const ProfileSummary = ({
  profileData,
  mainWorkProfile,
}: ProfileSummaryProps) => {
  const accountStatuses = [
    { label: 'Account Locked', value: profileData.accountNonLocked },
    { label: 'Account Expired', value: profileData.accountNonExpired },
    { label: 'Credentials Valid', value: profileData.credentialsNonExpired },
    { label: 'User Enabled', value: profileData.enabled },
    { label: '2FA Enabled', value: profileData.twoFactorEnabled },
  ];

  return (
    <div className='grid gap-6 lg:grid-cols-3'>
      <article className='rounded-3xl bg-white p-6 shadow-sm ring-1 ring-slate-200 lg:col-span-2'>
        <h2 className='mb-4 text-xl font-semibold text-slate-900'>
          Basic Information
        </h2>
        <dl className='grid gap-4 sm:grid-cols-2'>
          <div className='rounded-2xl bg-slate-50 p-4'>
            <dt className='text-xs font-semibold uppercase tracking-wide text-slate-500'>
              User ID
            </dt>
            <dd className='mt-1 break-all text-sm font-medium text-slate-900'>
              {profileData._id}
            </dd>
          </div>

          <div className='rounded-2xl bg-slate-50 p-4'>
            <dt className='text-xs font-semibold uppercase tracking-wide text-slate-500'>
              Staff ID
            </dt>
            <dd className='mt-1 text-sm font-medium text-slate-900'>
              {profileData.staffId}
            </dd>
          </div>

          <div className='rounded-2xl bg-slate-50 p-4'>
            <dt className='text-xs font-semibold uppercase tracking-wide text-slate-500'>
              Username
            </dt>
            <dd className='mt-1 text-sm font-medium text-slate-900'>
              {profileData.username ?? 'Not assigned'}
            </dd>
          </div>

          <div className='rounded-2xl bg-slate-50 p-4'>
            <dt className='text-xs font-semibold uppercase tracking-wide text-slate-500'>
              Main Position
            </dt>
            <dd className='mt-1 text-sm font-medium text-slate-900'>
              {mainWorkProfile.positionCode}
            </dd>
          </div>

          <div className='rounded-2xl bg-slate-50 p-4 sm:col-span-2'>
            <dt className='text-xs font-semibold uppercase tracking-wide text-slate-500'>
              Department
            </dt>
            <dd className='mt-1 text-sm font-medium text-slate-900'>
              {mainWorkProfile.departmentId}
            </dd>
          </div>
        </dl>
      </article>

      <article className='rounded-3xl bg-white p-6 shadow-sm ring-1 ring-slate-200'>
        <h2 className='mb-4 text-xl font-semibold text-slate-900'>
          Account Status
        </h2>
        <ul className='space-y-3'>
          {accountStatuses.map((status) => (
            <li
              key={status.label}
              className='flex items-center justify-between rounded-xl bg-slate-50 px-3 py-2'
            >
              <span className='text-sm text-slate-700'>{status.label}</span>
              <span
                className={`inline-flex rounded-full px-2.5 py-1 text-xs font-semibold ring-1 ${getStatusClasses(status.value)}`}
              >
                {status.value ? 'Yes' : 'No'}
              </span>
            </li>
          ))}
        </ul>
      </article>
    </div>
  );
};

export default ProfileSummary;
