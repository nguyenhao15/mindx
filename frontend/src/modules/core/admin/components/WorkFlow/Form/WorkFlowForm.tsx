import { FormProvider, useForm } from 'react-hook-form';
import WorkFlowFormElements from './WorkFlowFormElements';
import { Button } from '@/components/ui/button';
import {
  createWorkFlowSchema,
  type CreateWorkFlowFormData,
} from '../../../schema/workFlowSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { useCreateWorkflow } from '../../../hooks/useWorkFlowHook';
import toast from 'react-hot-toast';

const WorkFlowForm = ({ afterSubmit }: { afterSubmit: () => void }) => {
  const { mutateAsync: createWorkflow, isPending } = useCreateWorkflow();

  const methods = useForm({
    mode: 'onBlur',
    resolver: zodResolver(createWorkFlowSchema),
    defaultValues: {
      module: '',
      fromStatus: '',
      toStatus: '',
      labelName: '',
      actionType: '',
      description: '',
      enabled: true,
    },
  });

  const {
    handleSubmit,
    control,
    register,
    formState: { errors },
  } = methods;

  const onSubmit = async (data: CreateWorkFlowFormData) => {
    try {
      await createWorkflow(data);
      toast.success('Workflow created successfully');
      afterSubmit();
    } catch (error) {
      console.error('Failed to create workflow:', error);
      toast.error('Failed to create workflow');
    }
  };

  return (
    <div className='bg-white p-5 max-w-screen-sm w-96 rounded-2xl'>
      <h2>Thêm mới workflow</h2>
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
            Lưu
          </Button>
        </form>
      </FormProvider>
    </div>
  );
};

export default WorkFlowForm;
