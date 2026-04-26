import React, { useEffect } from 'react';
import {
  createWorkFlowSchema,
  type CreateWorkFlowFormData,
} from '../../../schema/workFlowSchema';
import { FormProvider, useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { useUpdateWorkflow } from '../../../hooks/useWorkFlowHook';
import toast from 'react-hot-toast';
import WorkFlowFormElements from './WorkFlowFormElements';
import { Button } from '@/components/ui/button';

interface UpdateWorkFlowFormProps {
  initialValues: any;
  editMode: boolean;
  afterSubmit: () => void;
}

const UpdateWorkFlowForm = ({
  initialValues,
  editMode,
  afterSubmit,
}: UpdateWorkFlowFormProps) => {
  const { mutateAsync: updateWorkflow, isPending } = useUpdateWorkflow(
    initialValues.id,
  );
  const methods = useForm({
    mode: 'onBlur',
    resolver: zodResolver(createWorkFlowSchema),
    defaultValues: initialValues,
  });

  const {
    handleSubmit,
    control,
    register,
    reset,
    formState: { errors },
  } = methods;

  useEffect(() => {
    if (initialValues) {
      reset({
        ...initialValues,
        enabled: initialValues.enabled ?? true,
      });
    }
  }, [initialValues, editMode]);

  const onSubmit = async (data: CreateWorkFlowFormData) => {
    try {
      await updateWorkflow(data);
      toast.success('Workflow updated successfully');
      afterSubmit();
    } catch (error) {
      console.error('Failed to update workflow:', error);
      toast.error('Failed to update workflow');
    }
  };

  return (
    <div className='bg-white p-5 max-w-screen-sm w-96 rounded-2xl'>
      <h2>Cập nhật workflow</h2>
      <FormProvider {...methods}>
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className='space-y-6'>
            <WorkFlowFormElements
              isLoading={isPending}
              control={control}
              register={register}
              errors={errors}
            />
          </div>
          <Button
            type='submit'
            disabled={isPending}
            className='mt-4 cursor-pointer'
            variant={'default'}
          >
            Cập nhật Workflow
          </Button>
        </form>
      </FormProvider>
    </div>
  );
};

export default UpdateWorkFlowForm;
