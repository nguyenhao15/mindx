import React from 'react';
import type { ModuleStat } from '../../type';

interface StatCardProps {
  stats: ModuleStat[];
}

function StatCard({ stat }: { stat: ModuleStat }) {
  return (
    <div
      className='rounded-2xl border bg-surface p-5 flex flex-col gap-3'
      //   style={{
      //     backgroundColor: theme.surface,
      //     borderColor: theme.border,
      //   }}
    >
      <p
        className='text-xs font-semibold uppercase tracking-wider'
        // style={{ color: theme.textSubtle }}
      >
        {stat.label}
      </p>

      <div className='flex items-end justify-between gap-2'>
        <span
          className='text-3xl font-bold tabular-nums'
          //   style={{ color: theme.text }}
        >
          {stat.count.toLocaleString('vi-VN')}
        </span>

        {/* <div
          className='flex items-center gap-1 rounded-lg px-2 py-1 text-xs font-semibold'
          style={{
            backgroundColor: isPositive ? theme.successBg : theme.dangerBg,
            color: isPositive ? theme.success : theme.danger,
          }}
        >
          {isPositive ? (
            <TrendingUp className='w-3.5 h-3.5' />
          ) : (
            <TrendingDown className='w-3.5 h-3.5' />
          )}
          <span>
            {isPositive ? '+' : ''}
            {stat.delta}
          </span>
        </div> */}
      </div>

      {stat.unit ? <p className='text-xs text-secondary'>{stat.unit}</p> : null}
    </div>
  );
}

export function AdminHomeStatCards({
  stats,
}: Readonly<{ stats: ModuleStat[] }>) {
  return (
    <div className='grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-5 gap-4'>
      {stats.map((stat: ModuleStat) => (
        <StatCard key={stat.moduleId} stat={stat} />
      ))}
    </div>
  );
}
