import { MessageSquare, UserRound } from 'lucide-react';

type TimelineItem = {
  id: string;
  actor: string;
  role: 'Người báo cáo' | 'Kỹ thuật viên' | 'Quản lý';
  action: string;
  note: string;
  timestamp: string;
};

interface ActivityTimelineProps {
  activities: TimelineItem[];
}

const ROLE_STYLE: Record<TimelineItem['role'], string> = {
  'Người báo cáo': 'bg-slate-100 text-slate-700',
  'Kỹ thuật viên': 'bg-sky-100 text-sky-700',
  'Quản lý': 'bg-emerald-100 text-emerald-700',
};

const ActivityTimeline = ({ activities }: ActivityTimelineProps) => {
  return (
    <section className='w-full bg-white rounded-2xl border border-slate-100 p-5 sm:p-6'>
      <h2 className='text-base font-semibold text-slate-800'>
        Lịch Sử & Cập Nhật
      </h2>
      <div className='mt-5 space-y-4'>
        {activities.map((item) => (
          <article key={item.id} className='relative pl-8'>
            <span className='absolute left-0 top-0 flex h-6 w-6 items-center justify-center rounded-full bg-[#1d3557]/10'>
              <MessageSquare size={13} className='text-[#1d3557]' />
            </span>
            <div className='rounded-xl bg-slate-50 p-4'>
              <div className='flex flex-wrap items-center justify-between gap-2'>
                <p className='text-sm font-semibold text-slate-800'>
                  {item.action}
                </p>
                <span className='text-xs text-slate-500'>{item.timestamp}</span>
              </div>
              <div className='mt-2 flex items-center gap-2'>
                <span className='inline-flex items-center gap-1 text-xs text-slate-600'>
                  <UserRound size={12} />
                  {item.actor}
                </span>
                <span
                  className={`inline-flex rounded-full px-2 py-0.5 text-xs font-semibold ${ROLE_STYLE[item.role]}`}
                >
                  {item.role}
                </span>
              </div>
              <p className='mt-2 text-sm text-slate-600'>{item.note}</p>
            </div>
          </article>
        ))}
      </div>
    </section>
  );
};

export default ActivityTimeline;
