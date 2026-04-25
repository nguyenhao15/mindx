import { formatDate } from 'date-fns';

interface DetailHeaderProps {
  desrciption: string;
  ticketCode: string;
  location: string;
  reporter: string;
  issueDate: string;
  status:
    | 'WAITING'
    | 'APPROVED'
    | 'REJECTED'
    | 'PROCESSING'
    | 'CHECKED'
    | 'FINISHED'
    | 'COMPLETED';
}
interface LineInfoProps {
  label: string;
  value: string;
  valueSize: 'sm' | 'md' | 'lg';
}

function LineInfo({ label, value, valueSize }: LineInfoProps) {
  return (
    <div className='flex items-center gap-2 text-sm text-slate-100'>
      <p className='flex-1/3'>{label}: </p>
      <span className={`flex-2/3 text-${valueSize} font-bold text-slate-200`}>
        {value}
      </span>
    </div>
  );
}

const DetailHeader = ({
  desrciption,
  ticketCode,
  location,
  reporter,
  issueDate,
  status,
}: DetailHeaderProps) => {
  return (
    <div className='p-4 w-full bg-primary rounded-lg'>
      <div className='flex flex-col gap-3 px-4 py-2'>
        <LineInfo
          label={'Mã sửa chữa'}
          value={'#' + ticketCode}
          valueSize='lg'
        />
        <LineInfo
          label={'Ngày báo cáo'}
          value={formatDate(new Date(issueDate), 'dd/MM/yyyy')}
          valueSize='sm'
        />

        <LineInfo label={'Địa điểm'} value={location} valueSize='sm' />
        <LineInfo
          label={'Người báo cáo'}
          value={reporter.toUpperCase()}
          valueSize='sm'
        />
        <div className='h-px mb-4 bg-gray-50' />

        <LineInfo label={'Trạng thái'} value={status} valueSize='sm' />
      </div>
    </div>
  );
};

export default DetailHeader;
