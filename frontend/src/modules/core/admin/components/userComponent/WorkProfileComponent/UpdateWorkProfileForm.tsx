import { WorkProfileEmbedded } from '@/modules/core/auth/schemas/userSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { FormProvider, useForm } from 'react-hook-form';
import { JobAssignmentCard } from '../FormElement/JobAssignmentCard';
import { useUpdateStaffProfile } from '@/modules/core/humanResource/hooks/useStaffProfileHook';
import { Button } from '@/components/ui/button';

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
    defaultValues: {
      ...initalData,
    },
  });

  const {
    control,
    handleSubmit,
    formState: { errors },
  } = methods;

  const onSubmit = async (data: any) => {
    try {
      await updateStaffProfile(data);
      if (afterSubmitAction) {
        afterSubmitAction();
      }
    } catch (error) {
      console.error('Error updating profile:', error);
    }
  };

  return (
    <div className='flex flex-col gap-4 bg-white p-4 rounded shadow'>
      <FormProvider {...methods}>
        <form onSubmit={handleSubmit(onSubmit)}>
          <JobAssignmentCard
            control={control}
            isLoading={isUpdatingStaffProfile}
          />
        </form>
      </FormProvider>
      <Button
        onClick={handleSubmit(onSubmit)}
        variant='positive'
        disabled={isUpdatingStaffProfile}
      >
        Cập nhật hồ sơ công việc
      </Button>
    </div>
  );
};

export default UpdateWorkProfileForm;
