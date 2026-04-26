import { Clock3, UserRound } from 'lucide-react';
import { formatDateTime, safeString } from '@/utils/formatValue';

type TimelineItem = {
  id: number | string;
  description?: string;
  createdDate?: string;
  createdBy?: string;
  changeType?: string;
  updateValue?: string;
};

interface ActivityTimelineProps {
  activities: TimelineItem[];
  limitItems?: number;
}

const CHANGE_TYPE_STYLE: Record<string, string> = {
  UPDATE: 'bg-sky-100 text-sky-700',
  CREATE: 'bg-emerald-100 text-emerald-700',
  DELETE: 'bg-rose-100 text-rose-700',
  CANCEL: 'bg-amber-100 text-amber-700',
  REJECT: 'bg-rose-100 text-rose-700',
  APPROVE: 'bg-emerald-100 text-emerald-700',
};

const STATUS_STYLE: Record<string, string> = {
  WAITING: 'bg-slate-100 text-slate-700',
  APPROVED: 'bg-emerald-100 text-emerald-700',
  REJECTED: 'bg-rose-100 text-rose-700',
  CHECKED: 'bg-sky-100 text-sky-700',
  PROCESSING: 'bg-amber-100 text-amber-700',
  FINISHED: 'bg-indigo-100 text-indigo-700',
  COMPLETED: 'bg-[#1d3557]/10 text-[#1d3557]',
};

const ActivityTimeline = ({
  activities,
  limitItems,
}: ActivityTimelineProps) => {
  const displayedActivities = limitItems
    ? activities.slice(0, limitItems)
    : activities;

  return (
    <section className='w-full bg-white rounded-2xl border border-slate-100 p-5 sm:p-6'>
      <h2 className='text-base font-semibold text-slate-800'>
        Lịch sử cập nhật
      </h2>

      {displayedActivities.length === 0 ? (
        <div className='mt-4 rounded-xl border border-slate-100 bg-slate-50 p-4 text-sm text-slate-500'>
          Chưa có bản ghi cập nhật nào.
        </div>
      ) : (
        <div className='mt-5 space-y-4'>
          {displayedActivities.map((item) => {
            const changeType = safeString(item.changeType, 'UPDATE');
            const updateValue = safeString(item.updateValue, 'N/A');

            return (
              <article key={item.id} className='relative pl-8'>
                <span className='absolute left-0 top-0 flex h-6 w-6 items-center justify-center rounded-full bg-[#1d3557]/10'>
                  <Clock3 size={13} className='text-[#1d3557]' />
                </span>

                <div className='rounded-xl border border-slate-100 bg-slate-50 p-4'>
                  <div className='flex flex-wrap items-center justify-between gap-2'>
                    <div className='flex flex-wrap items-center gap-2'>
                      <span
                        className={`inline-flex rounded-full px-2.5 py-1 text-xs font-semibold ${CHANGE_TYPE_STYLE[changeType] || 'bg-slate-100 text-slate-700'}`}
                      >
                        {changeType}
                      </span>
                      <span
                        className={`inline-flex rounded-full px-2.5 py-1 text-xs font-semibold ${STATUS_STYLE[updateValue] || 'bg-slate-100 text-slate-700'}`}
                      >
                        {updateValue}
                      </span>
                    </div>

                    <span className='inline-flex items-center gap-1 text-xs text-slate-500'>
                      <Clock3 size={12} />
                      {formatDateTime(item.createdDate || '')}
                    </span>
                  </div>

                  <p className='mt-3 text-sm text-slate-700'>
                    {safeString(item.description, 'Không có mô tả.')}
                  </p>

                  <div className='mt-3 inline-flex items-center gap-1 text-xs text-slate-600'>
                    <UserRound size={12} />
                    {safeString(item.createdBy, 'Không xác định')}
                  </div>
                </div>
              </article>
            );
          })}
          {limitItems && activities.length > limitItems && (
            <div className='text-center text-sm text-slate-500'>
              Và {activities.length - limitItems} bản ghi khác...
            </div>
          )}
        </div>
      )}
    </section>
  );
};

export default ActivityTimeline;
