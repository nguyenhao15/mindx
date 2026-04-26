import { useState } from 'react';
import type { CreateProposalRequestDTO } from '../../schema/proposalSchema';
import ProposalForm from './ProposalForm';
import ProposalCard from './ProposalCard';
import { Switch } from '@/components/input-elements/Switch';
import { useCreateProposal } from '../../hooks/useProposal';

interface ProposalFormProps {
  id: number;
}

const ProposalFormsInput = ({ id }: ProposalFormProps) => {
  const [multipleProposalsEnabled, setMultipleProposalsEnabled] =
    useState(false);
  const [editItem, setEditItem] = useState<CreateProposalRequestDTO | null>(
    null,
  );
  const [editingIndex, setEditingIndex] = useState<number | null>(null);
  const [proposalList, setProposalList] = useState<CreateProposalRequestDTO[]>(
    [],
  );

  const { mutateAsync: createProposal, isPending: isCreatingProposal } =
    useCreateProposal(id);

  const handleAddProposal = async (proposal: CreateProposalRequestDTO) => {
    if (multipleProposalsEnabled) {
      setProposalList((prev) => [...prev, proposal]);
    } else {
      try {
        await createProposal([proposal]);

        setProposalList([]);
      } catch (error) {
        console.error('Failed to create proposal:', error);
      }
    }

    setEditItem(null);
    setEditingIndex(null);
  };

  const handleEditProposal = (item: any, index: number) => {
    setEditItem(item);
    setEditingIndex(item.indexValue ? index : null);
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
    <div className=' flex flex-col gap-4 p-5 bg-white rounded'>
      <h2 className='text-lg font-semibold m-4 text-slate-700'>
        Proposal Forms for Maintenance ID: {id}
      </h2>
      <div className='w-full flex flex-col gap-4 items-center'>
        <Switch
          id='proposal-switch'
          label='Thêm nhiều đề xuất'
          onChange={() => setMultipleProposalsEnabled((prev) => !prev)}
          checked={multipleProposalsEnabled}
        />
        <ProposalForm
          itemId={id}
          onSubmit={handleAddProposal}
          defaultValue={editItem || {}}
        />
        <div>
          {proposalList.length > 0 && (
            <div className='flex flex-row gap-4 overflow-x-scroll space-y-3 h-64 p-3 bg-gray-50 rounded'>
              {proposalList.map((proposal: any, index: number) => (
                <ProposalCard
                  key={proposal.indexValue}
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
    </div>
  );
};

export default ProposalFormsInput;
