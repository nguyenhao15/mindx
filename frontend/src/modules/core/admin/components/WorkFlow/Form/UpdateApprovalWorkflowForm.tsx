import { FormProvider, useForm } from 'react-hook-form';
import { useUpdateApprovalPolicy } from '../../../hooks/useApprovalPolicy';
import { Button } from '@/components/ui/button';
import ApprovalFormElements from './ApprovalFormElements';
import { zodResolver } from '@hookform/resolvers/zod';
import { createApprovalPolicySchema } from '../../../schema/approvalPolicySchema';
import { useEffect } from 'react';
import toast from 'react-hot-toast';

interface UpdateApprovalWorkflowFormProps {
  initialValues: any;

  afterSubmit: () => void;
}

const UpdateApprovalWorkflowForm = ({
  initialValues,
  afterSubmit,
}: UpdateApprovalWorkflowFormProps) => {
  const methods = useForm({
    mode: 'onBlur',
    defaultValues: initialValues,
    resolver: zodResolver(createApprovalPolicySchema),
  });

  const {
    handleSubmit,
    control,
    reset,
    register,
    formState: { errors },
  } = methods;

  useEffect(() => {
    console.log('Initial value: ', initialValues);

    if (initialValues) {
      reset(initialValues);
    }
  }, [initialValues]);

  const {
    mutateAsync: updateApprovalPolicy,
    isPending: isUpdatingApprovalPolicy,
  } = useUpdateApprovalPolicy(initialValues?.id);

  const onSubmit = async (data: any) => {
    const payload = createApprovalPolicySchema.parse(data);
    const payloadToSend = {
      ...payload,
      id: initialValues.id,
    };

    try {
      await updateApprovalPolicy(payloadToSend);
      afterSubmit();
      toast.success('Approval policy updated successfully');
    } catch (error) {
      console.error('Failed to update approval policy:', error);
      toast.error('Failed to update approval policy');
    }
  };

  return (
    <div className='bg-white p-5 max-w-screen w-xl rounded-2xl'>
      <h2 className='font-bold'> Update Approval Workflow Form</h2>
      <FormProvider {...methods}>
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className='space-y-6'>
            <ApprovalFormElements
              isLoading={isUpdatingApprovalPolicy}
              control={control}
              register={register}
              errors={errors}
            />
          </div>
          <Button
            type='submit'
            variant='positive'
            className='mt-5 ml-auto block cursor-pointer'
            disabled={isUpdatingApprovalPolicy}
          >
            {isUpdatingApprovalPolicy ? 'Submitting...' : 'Submit'}
          </Button>
        </form>
      </FormProvider>
    </div>
  );
};

export default UpdateApprovalWorkflowForm;
