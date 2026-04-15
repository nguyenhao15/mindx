import { Link } from 'react-router-dom';
import { ArrowLeft, CalendarDays, MapPin, UserRound } from 'lucide-react';

interface DetailHeaderProps {
  ticketCode: string;
  location: string;
  reporter: string;
  issueDate: string;
  status: 'WAITING' | 'PROCESSING' | 'CHECKED' | 'FINISHED';
}

const STATUS_STYLES: Record<DetailHeaderProps['status'], string> = {
  WAITING: 'bg-amber-100 text-amber-700',
  PROCESSING: 'bg-sky-100 text-sky-700',
  CHECKED: 'bg-indigo-100 text-indigo-700',
  FINISHED: 'bg-emerald-100 text-emerald-700',
};

const STATUS_LABELS: Record<DetailHeaderProps['status'], string> = {
  WAITING: 'Chờ duyệt',
  PROCESSING: 'Đang sửa',
  CHECKED: 'Đợi nghiệm thu',
  FINISHED: 'Hoàn thành',
};

const DetailHeader = ({
  ticketCode,
  location,
  reporter,
  issueDate,
  status,
}: DetailHeaderProps) => {
  return (
    <section className='w-full bg-white rounded-2xl border border-slate-100 p-5 sm:p-6'>
      <div className='flex flex-col gap-4 sm:flex-row sm:items-start sm:justify-between'>
        <div className='space-y-3'>
          <Link
            to='/assets/maintance'
            className='inline-flex items-center gap-1.5 text-sm text-[#1d3557] hover:underline'
          >
            <ArrowLeft size={14} />
            Quay lại danh sách
          </Link>
          <div>
            <h1 className='text-xl sm:text-2xl font-bold text-slate-800'>
              Chi tiết Đơn Sửa Chữa {ticketCode}
            </h1>
            <p className='text-sm text-slate-500 mt-1'>
              Theo dõi tiến độ, phương án kỹ thuật và lịch sử xử lý theo thời
              gian thực.
            </p>
          </div>
        </div>

        <span
          className={`inline-flex w-fit rounded-full px-3 py-1 text-xs font-semibold ${STATUS_STYLES[status]}`}
        >
          {STATUS_LABELS[status]}
        </span>
      </div>

      <div className='mt-5 grid grid-cols-1 gap-2 sm:grid-cols-3'>
        <p className='flex items-center gap-2 text-sm text-slate-600'>
          <MapPin size={15} className='text-slate-400' />
          {location}
        </p>
        <p className='flex items-center gap-2 text-sm text-slate-600'>
          <UserRound size={15} className='text-slate-400' />
          {reporter}
        </p>
        <p className='flex items-center gap-2 text-sm text-slate-600'>
          <CalendarDays size={15} className='text-slate-400' />
          {issueDate}
        </p>
      </div>
    </section>
  );
};

export default DetailHeader;
