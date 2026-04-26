import ModalComponent from '@/components/shared/ModalComponent';
import { Button } from '@/components/ui/button';
import { useState } from 'react';
import ProposalFormsInput from './ProposalFormsInput';
import type { ProposalNestObject } from '../../schema/proposalSchema';
import ProposalCard from './ProposalCard';
import { Check } from 'lucide-react';

interface TechnicalSolutionsCardProps {
  maintenanceId: number;
  canAddSolution: boolean;
  solutions: ProposalNestObject[];
}

const TechnicalSolutionsCard = ({
  canAddSolution = false,
  solutions,
  maintenanceId,
}: TechnicalSolutionsCardProps & { maintenanceId: number }) => {
  const [modalOpen, setModalOpen] = useState(false);

  const handleOpenModal = () => {
    setModalOpen(true);
  };

  const handleOnClose = () => {
    setModalOpen(false);
  };

  return (
    <section className='w-full bg-white rounded-2xl border border-slate-100 p-5 sm:p-6'>
      <h2 className='text-base font-bold text-slate-800'>Phương Án Kỹ Thuật</h2>

      <div className='mt-4 space-y-3'>
        {canAddSolution && (
          <Button onClick={handleOpenModal}>Thêm phương án kỹ thuật</Button>
        )}
        {solutions.map((solution) => (
          <ProposalCard
            key={solution.id}
            EditIcon={Check}
            item={solution}
            index={solution.id || 0}
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
