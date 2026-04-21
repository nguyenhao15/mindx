import type { WorkProfileEmbeddedType } from '@/modules/core/auth/schemas/userSchema';
import WorkProfileHeader from './WorkProfileHeader';
import WorkProfileCard from './WorkProfileCard';
import { Button } from '@/components/ui/button';
import { useState } from 'react';
import ModalComponent from '@/components/shared/ModalComponent';
import WorkProfileFormComponent from './WorkProfileFormComponent';

type WorkProfileListProps = {
  userId: string;
  staffId: string;
  data: WorkProfileEmbeddedType[];
};

const WorkProfileList = ({ userId, staffId, data }: WorkProfileListProps) => {
  const assignmentCount = data.length;
  const [isModalOpen, setIsModalOpen] = useState(false);

  return (
    <div>
      <section className='rounded-2xl border border-slate-100 bg-slate-50/80 p-4 sm:p-6'>
        <WorkProfileHeader assignmentCount={assignmentCount} />
        <Button
          className='cursor-pointer my-2'
          type='button'
          variant='outline'
          onClick={() => setIsModalOpen(true)}
        >
          Thêm hồ sơ công việc
        </Button>

        {assignmentCount === 0 ? (
          <div className='rounded-2xl border border-dashed border-slate-200 bg-white p-8 text-center'>
            <p className='text-sm font-medium text-slate-700'>
              Chưa có thông tin công việc.
            </p>
            <p className='mt-1 text-sm text-slate-500'>
              Vui lòng thêm ít nhất một hồ sơ công việc để quản lý phân quyền.
            </p>
          </div>
        ) : (
          <div className='grid gap-4 xl:grid-cols-2'>
            {data.map((workProfile) => (
              <WorkProfileCard key={workProfile.id} workProfile={workProfile} />
            ))}
          </div>
        )}
      </section>
      <ModalComponent open={isModalOpen} onClose={() => setIsModalOpen(false)}>
        <WorkProfileFormComponent userId={userId} staffId={staffId} />
      </ModalComponent>
    </div>
  );
};

export default WorkProfileList;
