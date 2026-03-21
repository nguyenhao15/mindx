import type React from 'react';

export const DetailRow = ({
  label,
  children,
}: {
  label: string;
  children: React.ReactNode;
}) => {
  return (
    <div className='space-y-2 rounded-xl border border-slate-100 bg-slate-50/70 p-4'>
      <p className='text-xs font-semibold uppercase tracking-[0.2em] text-slate-500'>
        {label}
      </p>
      <div>{children}</div>
    </div>
  );
};
