import type { ProcessFlowDTO } from '@/validations/processFlowSchema';
import clsx from 'clsx';
import { DetailRow } from '../shared/DetailRow';

type AccessRule = ProcessFlowDTO['accessRule'];

type AcessDetailComponentProps = {
  accessRule?: AccessRule | null;
  className?: string;
};

const accessTypeMap: Record<
  NonNullable<AccessRule>['accessType'],
  { label: string; className: string }
> = {
  PUBLIC: {
    label: 'Công khai',
    className: 'bg-emerald-100 text-emerald-700 border-emerald-200',
  },
  LOOSEN: {
    label: 'Mở rộng',
    className: 'bg-slate-100 text-slate-700 border-slate-200',
  },
  RESTRICTED: {
    label: 'Hạn chế',
    className: 'bg-amber-100 text-amber-700 border-amber-200',
  },
};

const renderList = (items?: string[] | null, emptyLabel = 'Không giới hạn') => {
  if (!items || items.length === 0) {
    return <span className='text-sm text-slate-400'>{emptyLabel}</span>;
  }

  return (
    <div className='flex flex-wrap gap-2'>
      {items.map((item) => (
        <span
          key={item}
          className='rounded-full border border-slate-200 bg-slate-50 px-3 py-1 text-xs font-medium text-slate-700'
        >
          {item}
        </span>
      ))}
    </div>
  );
};

const AcessDetailComponent = ({
  accessRule,
  className,
}: AcessDetailComponentProps) => {
  if (!accessRule) {
    return (
      <aside
        className={clsx(
          'rounded-2xl border border-slate-200 bg-white p-5 shadow-sm',
          className,
        )}
      >
        <h3 className='text-lg font-semibold text-slate-900'>Quyền truy cập</h3>
        <p className='mt-2 text-sm text-slate-500'>
          Quy trình này hiển thị cho tất cả mọi người mà không có hạn chế nào
        </p>
      </aside>
    );
  }

  const accessTypeMeta = accessTypeMap[accessRule.accessType];

  return (
    <aside
      className={clsx(
        'w-full max-w-96 rounded-2xl border border-slate-200 bg-white p-5 shadow-sm',
        className,
      )}
    >
      <div className='flex items-start justify-between gap-4'>
        <div>
          <p className='text-xs font-semibold uppercase tracking-[0.2em] text-slate-500'>
            Access Rule
          </p>
          <h3 className='mt-1 text-xl font-bold text-slate-900'>
            Quyền truy cập
          </h3>
        </div>

        <span
          className={clsx(
            'rounded-full border px-3 py-1 text-xs font-semibold',
            accessTypeMeta.className,
          )}
        >
          {accessTypeMeta.label}
        </span>
      </div>

      <div className='mt-5 space-y-4'>
        <DetailRow label='Phòng ban'>
          {renderList(accessRule.departmentCodes)}
        </DetailRow>

        <DetailRow label='Chức vụ'>
          {renderList(accessRule.positionCodes)}
        </DetailRow>

        <DetailRow label='Lĩnh vực'>
          {renderList(accessRule.fieldIds)}
        </DetailRow>

        <DetailRow label='Cơ sở / BU'>{renderList(accessRule.buIds)}</DetailRow>

        <DetailRow label='Người dùng được phép'>
          {renderList(accessRule.allowedUserIds)}
        </DetailRow>

        <DetailRow label='Cấp bậc tối thiểu'>
          <span className='inline-flex rounded-full bg-blue-50 px-3 py-1 text-sm font-semibold text-blue-700'>
            Level {accessRule.minLevel ?? 0}
          </span>
        </DetailRow>
      </div>
    </aside>
  );
};

export default AcessDetailComponent;
