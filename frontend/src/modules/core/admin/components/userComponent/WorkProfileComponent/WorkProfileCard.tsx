import { Switch } from '@/components/input-elements/Switch';

import type { WorkProfileEmbeddedType } from '@/modules/core/auth/schemas/userSchema';
import { useUpdateStaffProfile } from '@/modules/core/humanResource/hooks/useStaffProfileHook';
import {
  BriefcaseBusiness,
  Building2,
  Layers3,
  ShieldCheck,
} from 'lucide-react';
import { useGetUserById } from '../../../hooks/useAdminHook';
import toast from 'react-hot-toast';
import { Button } from '@/components/ui/button';

interface WorkProfileCardProps {
  onSelectCard?: () => void;
  workProfile: WorkProfileEmbeddedType;
}

const WorkProfileCard = ({
  workProfile,
  onSelectCard,
}: WorkProfileCardProps) => {
  const { mutateAsync: updateStaffProfile, isPending: isUpdatingStaffProfile } =
    useUpdateStaffProfile(workProfile.id);

  const { refetch } = useGetUserById(workProfile.staffId);

  const handleOnChangeActive = async () => {
    const { active, ...rest } = workProfile;
    const dataToUpdate = {
      ...rest,
      active: !active,
    };
    try {
      await updateStaffProfile(dataToUpdate);
      refetch();
      toast.success(
        `Cập nhật hồ sơ công việc thành công! Hồ sơ này hiện đang ${!active ? 'kích hoạt' : 'vô hiệu hóa'}.`,
      );
    } catch (error) {
      console.log('Error');
    }
  };

  const handleOnChangeDefault = async () => {
    const { isDefault, ...rest } = workProfile;
    const dataToUpdate = {
      ...rest,
      isDefault: !isDefault,
    };
    try {
      await updateStaffProfile(dataToUpdate);
      refetch();
      toast.success(
        `Cập nhật hồ sơ công việc thành công! Hồ sơ này hiện đang ${
          !isDefault
            ? 'được đặt làm công việc chính'
            : 'không còn là công việc chính'
        }.`,
      );
    } catch (error) {
      console.log('Error');
    }
  };

  return (
    <article
      key={workProfile.id}
      className='group rounded-2xl border cursor-pointer border-slate-100 bg-white p-4 transition-colors hover:border-[#1d3557]/20 focus-within:ring-2 focus-within:ring-[#1d3557]/20 sm:p-5'
    >
      <div className='mb-5 flex items-center justify-between gap-2'>
        <Button
          variant={workProfile.isDefault ? 'positive' : 'neutral'}
          size='xs'
          type='button'
          disabled={Boolean(workProfile.isDefault) && isUpdatingStaffProfile}
          onClick={handleOnChangeDefault}
          className='p-4'
        >
          {workProfile.isDefault ? 'Công việc chính' : 'Công việc phụ'}
        </Button>
        <Button
          variant='outline'
          type='button'
          size='xs'
          disabled={isUpdatingStaffProfile}
          onClick={onSelectCard}
          className='cursor-pointer p-4'
        >
          Chỉnh sửa
        </Button>
      </div>
      <div className='mb-4 flex items-start justify-between gap-3'>
        <div className='space-y-2'>
          <p className='inline-flex items-center gap-2 text-xs font-medium text-slate-500'>
            <BriefcaseBusiness className='h-4 w-4 text-[#1d3557]' />
            Vị trí công việc
          </p>
          <h3 className='text-base font-semibold text-slate-900'>
            {workProfile.positionName}
          </h3>
        </div>
      </div>
      <div className='m-2'>
        <Switch
          id={`default-switch-${workProfile.id}`} // Unique ID for accessibility
          label='Active'
          checked={Boolean(workProfile.active)}
          onChange={handleOnChangeActive}
        />
      </div>

      <dl className='grid gap-3 sm:grid-cols-2'>
        <div className='rounded-lg bg-slate-50 p-3'>
          <dt className='inline-flex items-center gap-2 text-xs font-medium uppercase tracking-wide text-slate-500'>
            <Building2 className='h-3.5 w-3.5 text-slate-500' />
            Department
          </dt>
          <dd className='mt-1 text-sm font-semibold text-slate-800'>
            {workProfile.departmentName}
          </dd>
        </div>
        <div className='rounded-lg bg-slate-50 p-3'>
          <dt className='inline-flex items-center gap-2 text-xs font-medium uppercase tracking-wide text-slate-500'>
            <Layers3 className='h-3.5 w-3.5 text-slate-500' />
            Position Level
          </dt>
          <dd className='mt-1 text-sm font-semibold text-slate-800'>
            {workProfile.positionLevel}
          </dd>
        </div>
      </dl>

      <div className='mt-3 rounded-lg bg-slate-50 p-3'>
        <p className='inline-flex items-center gap-2 text-xs font-medium uppercase tracking-wide text-slate-500'>
          <ShieldCheck className='h-3.5 w-3.5 text-slate-500' />
          BU Allowed
        </p>
        <div className='mt-2 flex flex-wrap gap-2'>
          {workProfile.buAllowedList?.length ? (
            workProfile.buAllowedList.map((bu) => (
              <span
                key={bu}
                className='rounded-full bg-sky-50 px-2.5 py-1 text-xs font-medium text-sky-700 ring-1 ring-sky-200'
              >
                {bu}
              </span>
            ))
          ) : (
            <span className='text-sm text-slate-500'>Không có quyền BU.</span>
          )}
        </div>
      </div>
    </article>
  );
};

export default WorkProfileCard;
