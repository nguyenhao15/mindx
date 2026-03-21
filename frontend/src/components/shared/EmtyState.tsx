import { BoxIcon } from 'lucide-react';
interface EmptyStateProps {
  title: string;
  description: string;
  icon: typeof BoxIcon;
}
export function EmptyState({
  title,
  description,
  icon: Icon,
}: EmptyStateProps) {
  return (
    <div className='flex flex-col items-center justify-center py-24 px-4 text-center bg-white border border-slate-100 rounded-xl shadow-sm'>
      <div className='bg-slate-50 p-4 rounded-full mb-4'>
        <Icon
          className='w-12 h-12 text-slate-300'
          strokeWidth={1.5}
          aria-hidden='true'
        />
      </div>
      <h3 className='text-lg font-semibold text-slate-900 mb-2'>{title}</h3>
      <p className='text-sm text-slate-500 max-w-sm'>{description}</p>
    </div>
  );
}
