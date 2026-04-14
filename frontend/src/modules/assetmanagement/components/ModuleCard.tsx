import {
  type LucideIcon,
  ArrowRight,
  TrendingUp,
  TrendingDown,
} from 'lucide-react';
import { Link } from 'react-router-dom';

interface ModuleCardProps {
  title: string;
  description: string;
  path: string;
  icon: LucideIcon;
  accentColor: string;
  bgColor: string;
  stats: ReadonlyArray<{
    label: string;
    value: string | number;
    trend?: { value: number; positive: boolean };
  }>;
}

const ModuleCard = ({
  title,
  description,
  path,
  icon: Icon,
  accentColor,
  bgColor,
  stats,
}: ModuleCardProps) => {
  return (
    <Link
      to={path}
      className='group bg-white rounded-2xl border border-slate-100 p-6 flex flex-col gap-5 hover:border-[#1d3557]/20 hover:shadow-md transition-all duration-200 ring-2 ring-transparent hover:ring-[#1d3557]/10'
    >
      {/* Header */}
      <div className='flex items-start justify-between'>
        <div className='flex items-center gap-3'>
          <div
            className={`w-11 h-11 ${bgColor} rounded-xl flex items-center justify-center`}
          >
            <Icon size={22} className={accentColor} />
          </div>
          <div>
            <h3 className='font-semibold text-slate-800 text-base leading-tight'>
              {title}
            </h3>
            <p className='text-xs text-slate-400 mt-0.5'>{description}</p>
          </div>
        </div>
        <ArrowRight
          size={16}
          className='text-slate-300 group-hover:text-[#1d3557] group-hover:translate-x-0.5 transition-all duration-150 mt-1'
        />
      </div>

      {/* Stats */}
      <div className='grid grid-cols-2 gap-3'>
        {stats.map((stat) => (
          <div key={stat.label} className='bg-slate-50 rounded-xl p-3'>
            <p className='text-xs text-slate-400 mb-1'>{stat.label}</p>
            <div className='flex items-end gap-1.5'>
              <span className='text-xl font-bold text-slate-800'>
                {stat.value}
              </span>
              {stat.trend && (
                <span
                  className={`flex items-center gap-0.5 text-xs font-medium mb-0.5 ${
                    stat.trend.positive ? 'text-green-500' : 'text-red-400'
                  }`}
                >
                  {stat.trend.positive ? (
                    <TrendingUp size={12} />
                  ) : (
                    <TrendingDown size={12} />
                  )}
                  {Math.abs(stat.trend.value)}%
                </span>
              )}
            </div>
          </div>
        ))}
      </div>
    </Link>
  );
};

export default ModuleCard;
