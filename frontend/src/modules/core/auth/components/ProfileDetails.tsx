import { formatDateTime } from '@/utils/formatValue';
import type { UserResponseObjectType } from '@/modules/core/auth/schemas/userSchema';

type ProfileDetailsProps = {
  profileData: UserResponseObjectType;
};

const ProfileDetails = ({ profileData }: ProfileDetailsProps) => {
  return (
    <div className='grid gap-6 lg:grid-cols-2'>
      <article className='rounded-3xl bg-white p-6 shadow-sm ring-1 ring-slate-200'>
        <h2 className='mb-4 text-xl font-semibold text-slate-900'>
          Work Profile List
        </h2>
        <div className='space-y-4'>
          {profileData.workProfileList.map((workProfile, index) => (
            <div
              key={`${workProfile.departmentId}-${workProfile.positionCode}-${index}`}
              className='rounded-2xl border border-slate-200 bg-slate-50 p-4'
            >
              <div className='flex items-start justify-between gap-3'>
                <div>
                  <p className='text-sm font-semibold text-slate-900'>
                    {workProfile.positionCode}
                  </p>
                  <p className='text-sm text-slate-600'>
                    Department: {workProfile.departmentId}
                  </p>
                </div>
                {workProfile.isMainPosition && (
                  <span className='rounded-full bg-amber-100 px-2.5 py-1 text-xs font-semibold text-amber-700 ring-1 ring-amber-200'>
                    Main Position
                  </span>
                )}
              </div>
              <p className='mt-3 text-sm text-slate-700'>
                Position level: {workProfile.positionLevel}
              </p>
              <p className='mt-1 text-sm text-slate-700'>
                BU allowed list:{' '}
                {workProfile.buAllowedList?.length
                  ? workProfile.buAllowedList.join(', ')
                  : 'None'}
              </p>
            </div>
          ))}
        </div>
      </article>

      <article className='rounded-3xl bg-white p-6 shadow-sm ring-1 ring-slate-200'>
        <h2 className='mb-4 text-xl font-semibold text-slate-900'>Timeline</h2>
        <dl className='space-y-4'>
          <div className='rounded-2xl bg-slate-50 p-4'>
            <dt className='text-xs font-semibold uppercase tracking-wide text-slate-500'>
              Created Date
            </dt>
            <dd className='mt-1 text-sm font-medium text-slate-900'>
              {formatDateTime(profileData.createdDate)}
            </dd>
          </div>
          <div className='rounded-2xl bg-slate-50 p-4'>
            <dt className='text-xs font-semibold uppercase tracking-wide text-slate-500'>
              Updated Date
            </dt>
            <dd className='mt-1 text-sm font-medium text-slate-900'>
              {formatDateTime(profileData.updatedDate)}
            </dd>
          </div>
        </dl>
      </article>
    </div>
  );
};

export default ProfileDetails;
