import { useState } from 'react';
import type { CreateProposalRequestDTO } from '../../schema/proposalSchema';
import ProposalForm from './ProposalForm';
import ProposalCard from './ProposalCard';

interface ProposalFormProps {
  id: number;
}

const ProposalFormsInput = ({ id }: ProposalFormProps) => {
  const [editItem, setEditItem] = useState<CreateProposalRequestDTO | null>(
    null,
  );
  const [editingIndex, setEditingIndex] = useState<number | null>(null);
  const [proposalList, setProposalList] = useState<CreateProposalRequestDTO[]>(
    [],
  );

  const handleAddProposal = (proposal: CreateProposalRequestDTO) => {
    setProposalList((prev) => [...prev, proposal]);
  };

  const handleEditProposal = (
    item: CreateProposalRequestDTO,
    index: number,
  ) => {
    setEditItem(item);
    setEditingIndex(index);
    handleDeleteProposal(index);
  };

  const handleDeleteProposal = (index: number) => {
    setProposalList((prev) =>
      prev.filter((_, proposalIndex) => proposalIndex !== index),
    );

    if (editingIndex === index) {
      setEditingIndex(null);
      setEditItem(null);
      return;
    }

    if (editingIndex !== null && editingIndex > index) {
      setEditingIndex((prev) => (prev === null ? null : prev - 1));
    }
  };

  return (
    <div className='flex flex-col gap-4 p-5 bg-white rounded'>
      <h2 className='text-lg font-semibold'>
        Proposal Forms for Maintenance ID: {id}
      </h2>
      <div className='flex flex-col gap-4'>
        <ProposalForm
          itemId={id}
          onSubmit={handleAddProposal}
          defaultValue={editItem || {}}
        />

        {proposalList.length > 0 && (
          <div className='space-y-3'>
            {proposalList.map((proposal, index) => (
              <ProposalCard
                key={`${proposal.maintenanceId}-${index}`}
                item={proposal}
                index={index}
                onEdit={handleEditProposal}
                onDelete={handleDeleteProposal}
              />
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default ProposalFormsInput;
