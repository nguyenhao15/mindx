import { useFormContext } from 'react-hook-form';
import type { CreateProposalRequestDTO } from '../../schema/proposalSchema';
import TextInputField from '@/components/input-elements/TextInputField';

interface ProposalElementFormProps {
  errors: Record<string, any>;
  isSubmitting?: boolean;
}

const ProposalElementForm = ({
  errors,
  isSubmitting,
}: ProposalElementFormProps) => {
  const { register, control, getValues } =
    useFormContext<CreateProposalRequestDTO>();


  return (
    <div className='flex flex-col gap-4'>
      <TextInputField
        id='proposalDescription'
        label='Mô tả phương án kỹ thuật'
        placeholder='Nhập mô tả phương án kỹ thuật'
        errors={errors}
        rows={5}
        type='textarea'
        disabled={isSubmitting}
        register={register}
        required
      />
      <TextInputField
        id='proposalCost'
        label='Chi phí dự kiến'
        placeholder='Nhập chi phí dự kiến'
        errors={errors}
        control={control}
        type='number'
        disabled={isSubmitting}
        register={register}
        required
      />
      <TextInputField
        id='proposaledBy'
        label='Đơn vị đề xuất'
        placeholder='Nhập đơn vị đề xuất'
        errors={errors}
        control={control}
        type='text'
        disabled={isSubmitting}
        register={register}
        required
      />
    </div>
  );
};

export default ProposalElementForm;
