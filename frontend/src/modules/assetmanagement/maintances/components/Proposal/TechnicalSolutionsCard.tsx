import ModalComponent from '@/components/shared/ModalComponent';
import { Button } from '@/components/ui/button';
import { useState } from 'react';
import ProposalFormsInput from './ProposalFormsInput';
import {
  createProposalSchema,
  type ProposalNestObject,
} from '../../schema/proposalSchema';
import ProposalCard from './ProposalCard';
import { Check, X } from 'lucide-react';
import { useUpdateProposal } from '../../hooks/useProposal';

interface TechnicalSolutionsCardProps {
  maintenanceId: number;
  canAddSolution?: boolean;
  solutions: ProposalNestObject[];
}

const TechnicalSolutionsCard = ({
  canAddSolution = false,
  solutions,
  maintenanceId,
}: TechnicalSolutionsCardProps & { maintenanceId: number }) => {
  const [modalOpen, setModalOpen] = useState(false);

  const { mutateAsync: update, isPending: isUpdating } = useUpdateProposal();

  const handleOpenModal = () => {
    setModalOpen(true);
  };

  const handleOnClose = () => {
    setModalOpen(false);
  };

  const handleApproveProposal = async (proposal: any) => {
    const payload = {
      ...proposal,
      maintenanceId: maintenanceId,
      proposalStatus: 'PROPOSAL_ACCEPTED',
    };

    const payloadToSend = createProposalSchema.parse(payload);

    try {
      await update({ id: proposal.id, data: payloadToSend });
    } catch (error) {
      console.error('Failed to approve proposal:', error);
    }
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
            RemoveIcon={X}
            item={solution}
            index={solution.id || 0}
            onEdit={handleApproveProposal}
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
