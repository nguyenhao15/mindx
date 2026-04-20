import type { WorkProfileType } from '@/modules/core/auth/schemas/userSchema';
import {
  BriefcaseBusiness,
  Building2,
  Layers3,
  ShieldCheck,
} from 'lucide-react';
import React from 'react';

interface WorkProfileCardProps {
  workProfile: WorkProfileType;
}

const WorkProfileCard = ({ workProfile }: WorkProfileCardProps) => {
  return (
    <article
      key={workProfile.uuid}
      className='group rounded-2xl border border-slate-100 bg-white p-4 transition-colors hover:border-[#1d3557]/20 focus-within:ring-2 focus-within:ring-[#1d3557]/20 sm:p-5'
    >
      <div className='mb-4 flex items-start justify-between gap-3'>
        <div className='space-y-2'>
          <p className='inline-flex items-center gap-2 text-xs font-medium text-slate-500'>
            <BriefcaseBusiness className='h-4 w-4 text-[#1d3557]' />
            Vị trí công việc
          </p>
          <h3 className='text-base font-semibold text-slate-900'>
            {workProfile.positionCode}
          </h3>
        </div>
        <span
          className={
            workProfile.isMainPosition
              ? 'rounded-full bg-emerald-100 px-2.5 py-1 text-xs font-semibold text-emerald-700 ring-1 ring-emerald-200'
              : 'rounded-full bg-amber-100 px-2.5 py-1 text-xs font-semibold text-amber-700 ring-1 ring-amber-200'
          }
        >
          {workProfile.isMainPosition ? 'Main Position' : 'Secondary'}
        </span>
      </div>

      <dl className='grid gap-3 sm:grid-cols-2'>
        <div className='rounded-lg bg-slate-50 p-3'>
          <dt className='inline-flex items-center gap-2 text-xs font-medium uppercase tracking-wide text-slate-500'>
            <Building2 className='h-3.5 w-3.5 text-slate-500' />
            Department
          </dt>
          <dd className='mt-1 text-sm font-semibold text-slate-800'>
            {workProfile.departmentId}
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
                key={`${workProfile.departmentId}-${workProfile.positionCode}-${bu}`}
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
