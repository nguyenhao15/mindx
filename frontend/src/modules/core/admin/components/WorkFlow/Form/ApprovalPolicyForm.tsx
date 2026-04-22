import { zodResolver } from '@hookform/resolvers/zod';
import React from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import {
  createApprovalPolicySchema,
  type CreateApprovalPolicy,
} from '../../../schema/approvalPolicySchema';
import { useCreateNewApprovalPolicy } from '../../../hooks/useApprovalPolicy';
import ApprovalFormElements from './ApprovalFormElements';
import { Button } from '@/components/ui/button';

interface ApprovalPolicyFormProps {
  afterSubmit: () => void;
}

const ApprovalPolicyForm = ({ afterSubmit }: ApprovalPolicyFormProps) => {
  const { mutateAsync: createApprovalPolicy, isPending } =
    useCreateNewApprovalPolicy();
  const methods = useForm({
    mode: 'onBlur',
    resolver: zodResolver(createApprovalPolicySchema),
    defaultValues: {
      module: '',
      targetStatus: '',
      allowType: '',
      requesterPosition: '*',
      isActive: true,
    },
  });

  const {
    handleSubmit,
    control,
    register,
    formState: { errors },
  } = methods;

  const onSubmit = async (data: CreateApprovalPolicy) => {
    try {
      await createApprovalPolicy(data);
      afterSubmit();
    } catch (error) {
      console.error('Failed to create approval policy:', error);
    }
  };

  return (
    <div className='bg-white p-5 max-w-screen w-xl rounded-2xl'>
      <h2>Approval Policy Form</h2>
      <FormProvider {...methods}>
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className='space-y-6'>
            <ApprovalFormElements
              isLoading={isPending}
              control={control}
              register={register}
              errors={errors}
            />
          </div>
          <Button
            type='submit'
            variant='positive'
            className='mt-5 ml-auto block cursor-pointer'
            disabled={isPending}
          >
            {isPending ? 'Submitting...' : 'Submit'}
          </Button>
        </form>
      </FormProvider>
    </div>
  );
};

export default ApprovalPolicyForm;
