import { SearchIcon, PlusIcon } from 'lucide-react';

interface ActionHeaderProps {
  title: string;
  searchPlaceholder: string;
  ctaLabel: string;
  onSearch?: (query: string) => void;
  onCtaClick?: () => void;
}
export function ActionHeader({
  title,
  searchPlaceholder,
  ctaLabel,
  onSearch,
  onCtaClick,
}: ActionHeaderProps) {
  return (
    <div className='flex flex-col sm:flex-row sm:items-center justify-between gap-4 mb-6'>
      <h2 className='text-2xl font-bold text-slate-900'>{title}</h2>

      <div className='flex items-center gap-3'>
        <div className='relative'>
          <SearchIcon
            className='absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-slate-400'
            aria-hidden='true'
          />
          <input
            type='text'
            placeholder={searchPlaceholder}
            onChange={(e) => onSearch?.(e.target.value)}
            className='pl-9 pr-4 py-2 w-full sm:w-64 bg-white border border-slate-200 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-[#e31f20]/20 focus:border-[#e31f20] transition-colors placeholder:text-slate-400 text-slate-900'
            aria-label='Search'
          />
        </div>

        {onCtaClick && (
          <button
            onClick={onCtaClick}
            className='flex items-center cursor-pointer gap-2 bg-[#e31f20] hover:bg-[#c91b1c] text-white px-4 py-2 rounded-lg text-sm font-medium transition-all hover:scale-[1.02] active:scale-[0.98] focus:outline-none focus:ring-2 focus:ring-[#e31f20] focus:ring-offset-2'
            aria-label={ctaLabel}
          >
            <PlusIcon className='w-4 h-4' aria-hidden='true' />
            {ctaLabel}
          </button>
        )}
      </div>
    </div>
  );
}
