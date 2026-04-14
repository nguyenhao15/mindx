import { type LucideIcon } from 'lucide-react';

export interface ActivityItem {
  id: string;
  icon: LucideIcon;
  iconBg: string;
  iconColor: string;
  title: string;
  subtitle: string;
  timestamp: string;
  badge?: {
    label: string;
    color: string;
  };
}

interface RecentActivityListProps {
  items: ActivityItem[];
}

const RecentActivityList = ({ items }: RecentActivityListProps) => {
  return (
    <div className='bg-white rounded-2xl border border-slate-100 overflow-hidden'>
      <div className='px-6 py-4 border-b border-slate-100 flex items-center justify-between'>
        <h3 className='font-semibold text-slate-800'>Hoạt động gần đây</h3>
        <span className='text-xs text-slate-400'>{items.length} sự kiện</span>
      </div>
      <ul className='divide-y divide-slate-50'>
        {items.map((item) => {
          const Icon = item.icon;
          return (
            <li
              key={item.id}
              className='flex items-start gap-4 px-6 py-4 hover:bg-slate-50/70 transition-colors duration-100'
            >
              <div
                className={`w-9 h-9 mt-0.5 rounded-xl flex items-center justify-center shrink-0 ${item.iconBg}`}
              >
                <Icon size={16} className={item.iconColor} />
              </div>
              <div className='flex-1 min-w-0'>
                <p className='text-sm font-medium text-slate-700 truncate'>
                  {item.title}
                </p>
                <p className='text-xs text-slate-400 mt-0.5'>{item.subtitle}</p>
              </div>
              <div className='shrink-0 flex flex-col items-end gap-1.5'>
                <span className='text-xs text-slate-400'>{item.timestamp}</span>
                {item.badge && (
                  <span
                    className={`text-xs font-medium px-2 py-0.5 rounded-full ${item.badge.color}`}
                  >
                    {item.badge.label}
                  </span>
                )}
              </div>
            </li>
          );
        })}
      </ul>
    </div>
  );
};

export default RecentActivityList;
