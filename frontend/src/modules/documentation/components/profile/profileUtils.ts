import type { WorkProfileType } from '@/modules/documentation/validations/userSchema';

export const getStatusClasses = (active: boolean) =>
  active
    ? 'bg-emerald-100 text-emerald-700 ring-emerald-200'
    : 'bg-rose-100 text-rose-700 ring-rose-200';

export const getMainPosition = (profiles: WorkProfileType[]) => {
  return profiles.find((profile) => profile.isMainPosition) ?? profiles[0];
};
