import ModalComponent from '@/components/shared/ModalComponent';
import { Button } from '@/components/ui/button';
import { Clock3, Package2 } from 'lucide-react';
import { useState } from 'react';
import ProposalForm from './ProposalForm';
import ProposalFormsInput from './ProposalFormsInput';
import ProposalCard from './ProposalCard';

type Solution = {
  id: string;
  title: string;
  detail: string;
  estimateHours: number;
  materials: string[];
  proposalDescription: string;
  proposalCost: number;
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

  const handleOnClose = () => {
    console.log('Esc is clicked');
    setModalOpen(false);
  };
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
          <ProposalCard
            key={solution.id}
            item={solution}
            index={0}
            onEdit={() => {}}
            onDelete={() => {}}
          />
        ))}
      </div>
      <ModalComponent open={modalOpen} onClose={handleOnClose}>
        <ProposalFormsInput id={maintenanceId} />
      </ModalComponent>
    </section>
  );
};

export default TechnicalSolutionsCard;
