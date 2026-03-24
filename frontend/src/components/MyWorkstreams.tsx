import { FaChevronRight } from 'react-icons/fa';
import { SiGoogleanalytics } from 'react-icons/si';

export function MyWorkstreams() {
  return (
    <div className='bg-white dark:bg-slate-800 rounded-2xl border border-slate-100 dark:border-slate-700 p-6 shadow-sm'>
      <h3 className='text-lg font-bold mb-6 flex items-center gap-2'>
        <SiGoogleanalytics className='text-primary' />
        My Workstreams
      </h3>
      <div className='space-y-4'>
        <div className='flex items-start gap-4 p-3 rounded-lg hover:bg-slate-50 dark:hover:bg-slate-700/50 transition-colors cursor-pointer group'>
          <div className='size-2 rounded-full bg-yellow-400 mt-2'></div>
          <div className='flex-1'>
            <p className='text-sm font-bold'>Annual Performance Review</p>
            <p className='text-xs text-slate-500'>SOP-229 • Updated 2d ago</p>
          </div>
          <FaChevronRight className='text-slate-300 group-hover:text-primary transition-colors' />
        </div>
        <div className='flex items-start gap-4 p-3 rounded-lg hover:bg-slate-50 dark:hover:bg-slate-700/50 transition-colors cursor-pointer group'>
          <div className='size-2 rounded-full bg-green-500 mt-2'></div>
          <div className='flex-1'>
            <p className='text-sm font-bold'>New Client Onboarding</p>
            <p className='text-xs text-slate-500'>PR-45 • Active</p>
          </div>
          <FaChevronRight className='text-slate-300 group-hover:text-primary transition-colors' />
        </div>
        <div className='flex items-start gap-4 p-3 rounded-lg hover:bg-slate-50 dark:hover:bg-slate-700/50 transition-colors cursor-pointer group'>
          <div className='size-2 rounded-full bg-blue-500 mt-2'></div>
          <div className='flex-1'>
            <p className='text-sm font-bold'>Travel Expense Claim</p>
            <p className='text-xs text-slate-500'>FN-102 • Draft</p>
          </div>
          <FaChevronRight className='text-slate-300 group-hover:text-primary transition-colors' />
        </div>
      </div>
      <button className='w-full mt-6 py-2.5 text-sm font-bold text-slate-600 dark:text-slate-300 border border-slate-200 dark:border-slate-600 rounded-xl hover:bg-slate-50 dark:hover:bg-slate-700 transition-all'>
        Open Workspace
      </button>
    </div>
  );
}
