import { WorkProfileCreate } from '@/modules/core/auth/schemas/userSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { FormProvider, useForm } from 'react-hook-form';
import { JobAssignmentCard } from '../FormElement/JobAssignmentCard';
import { Button } from '@/components/ui/button';
import { useCreateNewProfile } from '../../../hooks/useAdminHook';
import toast from 'react-hot-toast';

interface WorkProfileFormComponentProps {
  userId: string;
  staffId: string;
  afterSubmitAction?: () => void;
}

const WorkProfileFormComponent = ({
  userId,
  staffId,
  afterSubmitAction,
}: WorkProfileFormComponentProps) => {
  const methods = useForm({
    mode: 'onBlur',
    resolver: zodResolver(WorkProfileCreate),
    defaultValues: {
      userId: userId,
      staffId: staffId,
    },
  });

  const { mutateAsync: createNewProfile, isPending } = useCreateNewProfile();

  const {
    control,
    handleSubmit,
    formState: { errors },
  } = methods;

  const onSubmit = async (data: any) => {
    const payload = {
      ...data,
      userId: userId,
      staffId: staffId,
    };

    try {
      const response = await createNewProfile(payload);
      console.log('Profile created successfully:', response);
      toast.success('Hồ sơ công việc đã được tạo thành công');
      if (afterSubmitAction) {
        afterSubmitAction();
      }
    } catch (error) {
      console.error('Error creating profile:', error);
      toast.error('Đã xảy ra lỗi khi tạo hồ sơ công việc');
    }
  };

  return (
    <div className='bg-white p-10 rounded-lg shadow-md w-full'>
      <h2 className='text-lg font-medium text-slate-700'>
        Thêm hồ sơ công việc
      </h2>
      <FormProvider {...methods}>
        <form id='create-profile' onSubmit={handleSubmit(onSubmit)}>
          <JobAssignmentCard isLoading={isPending} control={control} />
        </form>
        <Button
          onClick={handleSubmit(onSubmit)}
          form='create-profile'
          variant='positive'
          type='button'
          className='mt-4 cursor-pointer'
        >
          Thêm hồ sơ công việc
        </Button>
      </FormProvider>
    </div>
  );
};

export default WorkProfileFormComponent;
