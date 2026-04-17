import { formatDateTime, formatPrice } from '@/utils/formatValue';

interface DetailInfoProps {
  id: string | number;
  fixCategory: {
    categoryTitle: string;
  };
  fixItem: {
    itemTitle: string;
  };
  totalProposals: number;
  totalCost: number;
  createdDate: string;
  lastModifiedDate: string;
  description: string;
  reWork: boolean;
}

const DetailInfo = ({
  id,
  fixCategory,
  fixItem,
  totalProposals,
  totalCost,
  createdDate,
  lastModifiedDate,
  description,
  reWork,
}: DetailInfoProps) => {
  return (
    <section className='w-full bg-white rounded-2xl border border-slate-100 p-5 sm:p-6'>
      <div className='flex items-center justify-between gap-3'>
        <h2 className='text-base font-semibold text-slate-800'>
          Thông Tin Sự Cố
        </h2>
        <span className='inline-flex rounded-full bg-[#1d3557]/10 px-3 py-1 text-xs font-semibold text-[#1d3557]'>
          ID #{id ?? id}
        </span>
      </div>

      <div className='mt-4 grid grid-cols-1 gap-3 sm:grid-cols-2 xl:grid-cols-3'>
        <article className='rounded-xl bg-slate-50 p-4'>
          <p className='text-xs text-slate-500'>Danh mục sửa chữa</p>
          <p className='mt-1 text-sm font-semibold text-slate-800'>
            {fixCategory?.categoryTitle || 'N/A'}
          </p>
        </article>

        <article className='rounded-xl bg-slate-50 p-4'>
          <p className='text-xs text-slate-500'>Hạng mục</p>
          <p className='mt-1 text-sm font-semibold text-slate-800'>
            {fixItem?.itemTitle || 'N/A'}
          </p>
        </article>

        <article className='rounded-xl bg-slate-50 p-4'>
          <p className='text-xs text-slate-500'>Tổng phương án</p>
          <p className='mt-1 text-sm font-semibold text-slate-800'>
            {totalProposals ?? 0}
          </p>
        </article>

        <article className='rounded-xl bg-slate-50 p-4'>
          <p className='text-xs text-slate-500'>Tổng chi phí</p>
          <p className='mt-1 text-sm font-semibold text-slate-800'>
            {formatPrice(totalCost ?? 0)}
          </p>
        </article>

        <article className='rounded-xl bg-slate-50 p-4'>
          <p className='text-xs text-slate-500'>Ngày tạo đơn</p>
          <p className='mt-1 text-sm font-semibold text-slate-800'>
            {formatDateTime(createdDate)}
          </p>
        </article>

        <article className='rounded-xl bg-slate-50 p-4'>
          <p className='text-xs text-slate-500'>Cập nhật gần nhất</p>
          <p className='mt-1 text-sm font-semibold text-slate-800'>
            {formatDateTime(lastModifiedDate)}
          </p>
        </article>
      </div>

      <article className='mt-3 rounded-xl bg-slate-50 p-4'>
        <p className='text-xs text-slate-500'>Mô tả sự cố</p>
        <p className='mt-1 text-sm text-slate-700'>
          {description || 'Chưa có mô tả'}
        </p>
        <p className='mt-2 text-xs text-slate-500'>
          Re-work: {reWork ? 'Có' : 'Không'}
        </p>
      </article>
    </section>
  );
};

export default DetailInfo;
