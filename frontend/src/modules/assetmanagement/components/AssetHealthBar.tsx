interface HealthSegment {
  label: string;
  count: number;
  color: string;
  bgColor: string;
  textColor: string;
}

interface AssetHealthBarProps {
  total: number;
  segments: HealthSegment[];
}

const AssetHealthBar = ({ total, segments }: AssetHealthBarProps) => {
  return (
    <div className='bg-white rounded-2xl border border-slate-100 p-6 flex flex-col gap-5'>
      <div className='flex items-center justify-between'>
        <div>
          <h3 className='font-semibold text-slate-800'>Tình trạng tài sản</h3>
          <p className='text-sm text-slate-400 mt-0.5'>
            Tổng cộng{' '}
            <span className='font-semibold text-slate-700'>
              {total.toLocaleString('vi-VN')}
            </span>{' '}
            tài sản
          </p>
        </div>
        <span className='text-xs text-slate-400 bg-slate-50 px-3 py-1.5 rounded-lg'>
          Cập nhật hôm nay
        </span>
      </div>

      {/* Stacked bar */}
      <div className='flex h-3 rounded-full overflow-hidden gap-0.5'>
        {segments.map((seg) => (
          <div
            key={seg.label}
            className={`${seg.color} transition-all duration-500`}
            style={{ width: `${(seg.count / total) * 100}%` }}
            title={`${seg.label}: ${seg.count.toLocaleString('vi-VN')}`}
          />
        ))}
      </div>

      {/* Legend */}
      <div className='grid grid-cols-2 sm:grid-cols-4 gap-3'>
        {segments.map((seg) => (
          <div key={seg.label} className={`${seg.bgColor} rounded-xl p-3`}>
            <div className='flex items-center gap-1.5 mb-1'>
              <span className={`w-2 h-2 rounded-full ${seg.color}`} />
              <span className='text-xs text-slate-500'>{seg.label}</span>
            </div>
            <p className={`text-lg font-bold ${seg.textColor}`}>
              {seg.count.toLocaleString('vi-VN')}
            </p>
            <p className='text-xs text-slate-400'>
              {((seg.count / total) * 100).toFixed(1)}%
            </p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default AssetHealthBar;
