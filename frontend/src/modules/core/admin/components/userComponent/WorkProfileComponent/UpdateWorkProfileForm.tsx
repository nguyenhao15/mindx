import { WorkProfileEmbedded } from '@/modules/core/auth/schemas/userSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { FormProvider, useForm } from 'react-hook-form';
import { JobAssignmentCard } from '../FormElement/JobAssignmentCard';
import { useUpdateStaffProfile } from '@/modules/core/humanResource/hooks/useStaffProfileHook';
import { Button } from '@/components/ui/button';
import toast from 'react-hot-toast';

interface UpdateWorkProfileFormProps {
  // Define any props you need here
  initalData: any; // Replace 'any' with the actual type of your initial data
  afterSubmitAction?: () => void;
}

const UpdateWorkProfileForm = ({
  initalData,
  afterSubmitAction,
}: UpdateWorkProfileFormProps) => {
  const { mutateAsync: updateStaffProfile, isPending: isUpdatingStaffProfile } =
    useUpdateStaffProfile(initalData?.id);

  const methods = useForm({
    mode: 'onBlur',
    resolver: zodResolver(WorkProfileEmbedded),
    defaultValues: initalData,
  });

  const {
    control,
    handleSubmit,
    formState: { errors },
  } = methods;

  const onSubmit = async (data: any) => {
    try {
      await updateStaffProfile(data);
      afterSubmitAction && afterSubmitAction();
      toast.success('Cập nhật hồ sơ công việc thành công!');
    } catch (error) {
      console.error('Error updating profile:', error);
    }
  };

  return (
    <div className='flex flex-col gap-4 bg-white p-4 rounded shadow'>
      <h2>Cập nhật hồ sơ công việc</h2>
      <FormProvider {...methods}>
        <form onSubmit={handleSubmit(onSubmit)}>
          <JobAssignmentCard
            control={control}
            isLoading={isUpdatingStaffProfile}
          />
        </form>
      </FormProvider>
      <div className='flex justify-end w-full p-4'>
        <Button
          onClick={handleSubmit(onSubmit)}
          variant='positive'
          className='cursor-pointer p-4'
          disabled={isUpdatingStaffProfile}
        >
          Cập nhật hồ sơ công việc
        </Button>
      </div>
    </div>
  );
};

export default UpdateWorkProfileForm;
