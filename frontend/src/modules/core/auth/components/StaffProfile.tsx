import { useMemo, useState } from 'react';
import type {
  WorkProfileEmbeddedType,
  WorkProfileType,
} from '@/modules/core/auth/schemas/userSchema';
import {
  Building2,
  CheckCircle2,
  ShieldCheck,
  UserRoundCog,
} from 'lucide-react';

interface StaffProfileProps {
  control?: unknown;
  profiles: WorkProfileEmbeddedType[];
  onSelect?: (profile: WorkProfileEmbeddedType) => void;
}

const LOCAL_STORAGE_PROFILE_KEY = 'profile_id';

const StaffProfile = ({ profiles, onSelect }: StaffProfileProps) => {
  const [selectedProfileId, setSelectedProfileId] = useState<string | null>(
    () => localStorage.getItem(LOCAL_STORAGE_PROFILE_KEY),
  );

  const selectedProfile = useMemo(
    () => profiles.find((profile) => profile.id === selectedProfileId) ?? null,
    [profiles, selectedProfileId],
  );

  const handleSelectProfile = (profile: WorkProfileEmbeddedType) => {
    setSelectedProfileId(profile.id);
    localStorage.setItem(LOCAL_STORAGE_PROFILE_KEY, profile.id);
    onSelect?.(profile);
  };

  return (
    <section className='rounded-2xl border border-slate-100 bg-slate-50 p-4 sm:p-6'>
      <header className='mb-5 flex flex-col gap-2 sm:mb-6'>
        <h2 className='text-lg font-semibold text-[#1d3557]'>
          Chọn hồ sơ làm việc
        </h2>
        <p className='text-sm text-slate-600'>
          Bạn cần chọn một hồ sơ công việc để sử dụng đầy đủ chức năng của hệ
          thống.
        </p>
      </header>

      <div className='mb-4 rounded-xl border border-sky-100 bg-sky-50 px-4 py-3 text-sm text-sky-800'>
        Vui lòng chọn đúng hồ sơ theo phòng ban và vị trí của bạn trước khi tiếp
        tục.
      </div>

      {selectedProfile ? (
        <div className='mb-4 rounded-xl border border-emerald-100 bg-emerald-50 px-4 py-3 text-sm text-emerald-800'>
          Hồ sơ đã chọn: <strong>{selectedProfile.positionName}</strong> -{' '}
          {selectedProfile.departmentName}
        </div>
      ) : null}

      {profiles.length === 0 ? (
        <div className='rounded-2xl border border-dashed border-slate-200 bg-white p-8 text-center'>
          <p className='text-sm font-medium text-slate-700'>
            Chưa có hồ sơ công việc khả dụng.
          </p>
          <p className='mt-1 text-sm text-slate-500'>
            Liên hệ quản trị viên để được cấp profile.
          </p>
        </div>
      ) : (
        <div className='grid gap-4 md:grid-cols-2'>
          {profiles.map((profile) => {
            const isSelected = profile.id === selectedProfileId;

            return (
              <button
                key={profile.id}
                type='button'
                onClick={() => handleSelectProfile(profile)}
                className={`w-full rounded-2xl border bg-white p-4 text-left transition-all sm:p-5 ${
                  isSelected
                    ? 'border-[#1d3557] ring-2 ring-[#1d3557]/20'
                    : 'border-slate-100 hover:border-[#1d3557]/25'
                }`}
              >
                <div className='mb-3 flex items-start justify-between gap-3'>
                  <p className='inline-flex items-center gap-2 text-xs font-medium uppercase tracking-wide text-slate-500'>
                    <UserRoundCog className='h-4 w-4 text-[#1d3557]' />
                    Work Profile
                  </p>
                  {isSelected ? (
                    <span className='inline-flex items-center gap-1 rounded-full bg-[#1d3557]/10 px-2.5 py-1 text-xs font-semibold text-[#1d3557]'>
                      <CheckCircle2 className='h-3.5 w-3.5' />
                      Đã chọn
                    </span>
                  ) : null}
                </div>

                <h3 className='text-base font-semibold text-slate-900'>
                  {profile.positionName}
                </h3>

                <dl className='mt-3 space-y-2 text-sm text-slate-700'>
                  <div className='flex items-start gap-2'>
                    <Building2 className='mt-0.5 h-4 w-4 shrink-0 text-slate-500' />
                    <div>
                      <dt className='font-medium text-slate-500'>Phòng ban</dt>
                      <dd>{profile.departmentName}</dd>
                    </div>
                  </div>

                  <div className='flex items-start gap-2'>
                    <ShieldCheck className='mt-0.5 h-4 w-4 shrink-0 text-slate-500' />
                    <div>
                      <dt className='font-medium text-slate-500'>Cấp bậc</dt>
                      <dd>{profile.positionLevel}</dd>
                    </div>
                  </div>
                </dl>

                {profile.buAllowedList?.length ? (
                  <div className='mt-3 flex flex-wrap gap-2'>
                    {profile.buAllowedList.map((bu) => (
                      <span
                        key={`${profile.id}-${bu}`}
                        className='rounded-full bg-sky-50 px-2.5 py-1 text-xs font-medium text-sky-700 ring-1 ring-sky-200'
                      >
                        {bu}
                      </span>
                    ))}
                  </div>
                ) : null}
              </button>
            );
          })}
        </div>
      )}
    </section>
  );
};

export default StaffProfile;
