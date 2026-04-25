import ModalComponent from '@/components/shared/ModalComponent';
import { Button } from '@/components/ui/button';
import { Clock3, Package2 } from 'lucide-react';
import { useState } from 'react';
import ProposalForm from './ProposalForm';
import ProposalFormsInput from './ProposalFormsInput';

type Solution = {
  id: string;
  title: string;
  detail: string;
  estimateHours: number;
  materials: string[];
};

interface TechnicalSolutionsCardProps {
  maintenanceId: number;
  canAddSolution: boolean;
  solutions: Solution[];
}

const TechnicalSolutionsCard = ({
  canAddSolution,
  solutions,
  maintenanceId,
}: TechnicalSolutionsCardProps & { maintenanceId: number }) => {
  const [modalOpen, setModalOpen] = useState(false);
  return (
    <section className='w-full bg-white rounded-2xl border border-slate-100 p-5 sm:p-6'>
      <h2 className='text-base font-semibold text-slate-800'>
        Phương Án Kỹ Thuật
      </h2>

      <div className='mt-4 space-y-3'>
        <Button onClick={() => setModalOpen(true)}>
          Thêm phương án kỹ thuật
        </Button>
        {solutions.map((solution) => (
          <article key={solution.id} className='rounded-xl bg-slate-50 p-4'>
            <h3 className='text-sm font-semibold text-slate-800'>
              {solution.title}
            </h3>
            <p className='mt-1 text-sm text-slate-600'>{solution.detail}</p>
            <div className='mt-3 flex flex-wrap items-center gap-4'>
              <span className='inline-flex items-center gap-1.5 text-xs text-slate-600'>
                <Clock3 size={14} className='text-slate-400' />
                {solution.estimateHours} giờ dự kiến
              </span>
              <span className='inline-flex items-center gap-1.5 text-xs text-slate-600'>
                <Package2 size={14} className='text-slate-400' />
                {solution.materials.join(', ')}
              </span>
            </div>
          </article>
        ))}
      </div>
      <ModalComponent open={modalOpen} onClose={() => setModalOpen(false)}>
        <ProposalFormsInput id={maintenanceId} />
      </ModalComponent>
    </section>
  );
};

export default TechnicalSolutionsCard;
