import {
  createProposalSchema,
  type CreateProposalRequestDTO,
} from '../../schema/proposalSchema';
import { useEffect } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { Button } from '@/components/ui/button';
import ProposalElementForm from './ProposalElementForm';

interface ProposalFormProps {
  itemId: number;
  onSubmit: (proposal: CreateProposalRequestDTO) => void;
  defaultValue?: Partial<CreateProposalRequestDTO>;
}

const ProposalForm = ({
  itemId,
  onSubmit,
  defaultValue,
}: ProposalFormProps) => {
  const methods = useForm({
    mode: 'onBlur',
    resolver: zodResolver(createProposalSchema),
    defaultValues: {
      maintenanceId: itemId,
      proposalDescription: '',
      proposalCost: 0,
      proposaledBy: '',
      proposalStatus: 'PROPOSAL_PENDING',
      ...defaultValue,
    },
  });

  console.log('Edit item: ', defaultValue);

  const {
    handleSubmit,
    reset,
    formState: { errors, isSubmitting },
  } = methods;

  useEffect(() => {
    reset({
      maintenanceId: itemId,
      proposalDescription: '',
      proposalCost: 0,
      proposaledBy: '',
      proposalStatus: 'PROPOSAL_PENDING',
      ...defaultValue,
    });
  }, [defaultValue, itemId, reset]);

  const onFormSubmit = async (data: CreateProposalRequestDTO) => {
    onSubmit(data);
    reset({
      maintenanceId: itemId,
      proposalDescription: '',
      proposalCost: 0,
      proposaledBy: '',
      proposalStatus: 'PROPOSAL_PENDING',
    });
  };

  return (
    <div className='p-2 flex flex-col gap-4 bg-gray-50 rounded w-96'>
      <FormProvider {...methods}>
        <form onSubmit={handleSubmit(onFormSubmit)}>
          <ProposalElementForm errors={errors} isSubmitting={isSubmitting} />
        </form>
      </FormProvider>
      <Button
        className='self-end p-2 cursor-pointer'
        onClick={handleSubmit(onFormSubmit)}
        disabled={isSubmitting}
      >
        Thêm phương án kỹ thuật
      </Button>
    </div>
  );
};

export default ProposalForm;
