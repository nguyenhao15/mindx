import React from 'react';

const WorkProfileHeader = ({
  assignmentCount,
}: {
  assignmentCount: number;
}) => {
  return (
    <div className='mb-5 flex flex-col gap-3 sm:mb-6 sm:flex-row sm:items-end sm:justify-between'>
      <div>
        <h2 className='text-base font-semibold text-[#1d3557] sm:text-lg'>
          Work Profile
        </h2>
        <p className='mt-1 text-sm text-slate-600'>
          Danh sách vị trí, phòng ban và quyền truy cập theo hồ sơ công việc.
        </p>
      </div>
      <span className='inline-flex w-fit items-center rounded-full bg-[#1d3557]/10 px-3 py-1 text-xs font-semibold text-[#1d3557] ring-1 ring-[#1d3557]/15'>
        {assignmentCount} hồ sơ công việc
      </span>
    </div>
  );
};

export default WorkProfileHeader;
