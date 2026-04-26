import { formatPrice } from '@/utils/formatValue';
import { Pencil, Trash2, UserRound } from 'lucide-react';
import { Button } from '@/components/ui/button';
import type { CreateProposalRequestDTO } from '../../schema/proposalSchema';

interface ProposalCardProps {
  item: CreateProposalRequestDTO;
  index: number;
  onEdit: (item: CreateProposalRequestDTO, index: number) => void;
  onDelete: (index: number) => void;
}

const ProposalCard = ({ item, index, onEdit, onDelete }: ProposalCardProps) => {
  return (
    <article className='rounded-xl border border-slate-200 bg-white p-4 shadow-sm'>
      <div className='flex items-start justify-between gap-3'>
        <h3 className='text-sm font-semibold text-slate-800'>
          Đề xuất #{index + 1}
        </h3>
        <div className='flex items-center gap-2'>
          <Button
            size='icon-xs'
            variant='ghost'
            type='button'
            onClick={() => onEdit(item, index)}
            aria-label='Chỉnh sửa đề xuất'
          >
            <Pencil className='size-4 text-slate-600' />
          </Button>
          <Button
            size='icon-xs'
            variant='ghost'
            type='button'
            onClick={() => onDelete(index)}
            aria-label='Xóa đề xuất'
          >
            <Trash2 className='size-4 text-red-500' />
          </Button>
        </div>
      </div>

      <div className='mt-3 rounded-lg bg-slate-50 p-3'>
        <p className='text-xs font-medium uppercase tracking-wide text-slate-500'>
          Mô tả phương án
        </p>
        <p className='mt-2 max-h-32 overflow-y-auto whitespace-pre-wrap wrap-break-word pr-1 text-sm leading-6 text-slate-700'>
          {item.proposalDescription}
        </p>
      </div>

      <div className='mt-3 flex flex-wrap items-center justify-between gap-3'>
        <div>
          <p className='text-xs text-slate-500'>Chi phí dự kiến</p>
          <p className='text-sm font-semibold text-slate-800'>
            {formatPrice(item.proposalCost)}
          </p>
        </div>

        <div className='inline-flex items-center gap-1.5 rounded-full bg-slate-100 px-2.5 py-1'>
          <UserRound className='size-3.5 text-slate-500' />
          <span className='text-xs font-medium text-slate-700'>
            {item.proposedBy}
          </span>
        </div>
      </div>
    </article>
  );
};

export default ProposalCard;
