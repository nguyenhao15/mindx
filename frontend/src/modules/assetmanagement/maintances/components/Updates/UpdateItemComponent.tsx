import { FormProvider, useForm } from 'react-hook-form';
import {
  MaintenanceUpdateRequest,
  type MaintenanceStatus,
} from '../../schema/maintenaceSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import UpdateForm from './UpdateForm';
import { Button } from '@/components/ui/button';
import { useGetAvailableActionUpdate } from '../../hooks/useMaintenanceHooks';

interface UpdateItemComponentProps {
  id: number;
  maintenancesStatus: MaintenanceStatus;
  reWork: boolean;
  totalCost: number;
}

const UpdateItemComponent = ({
  id,
  maintenancesStatus,
  reWork,
  totalCost,
}: UpdateItemComponentProps) => {
  const methods = useForm({
    resolver: zodResolver(MaintenanceUpdateRequest),
    defaultValues: {
      maintenancesStatus,
      reWork,
      totalCost,
    },
  });

  const { data } = useGetAvailableActionUpdate(maintenancesStatus, {
    enabled: !!maintenancesStatus,
  });

  const {
    handleSubmit,
    register,
    control,
    formState: { errors },
  } = methods;

  const onSubmitData = (data: any) => {
    console.log('Form Data:', data);
  };

  return (
    <div className='p-10 bg- rounded-lg w-full bg-white border border-slate-100 '>
      <FormProvider {...methods}>
        <form
          action=''
          onSubmit={handleSubmit(onSubmitData)}
          className='w-full min-w-96'
        >
          <UpdateForm
            currentStatus={maintenancesStatus}
            control={control}
            register={register}
            errors={errors}
          />
        </form>
        <div className='flex gap-2 p-2 items-center justify-self-center'>
          {data?.length > 0 &&
            data.map((action: string) => (
              <Button
                key={action}
                className='mt-4'
                onClick={handleSubmit(onSubmitData)}
                disabled={
                  data?.length === 0 || Object.keys(errors).length > 0
                    ? true
                    : false
                }
              >
                {action}
              </Button>
            ))}
        </div>
      </FormProvider>
    </div>
  );
};

export default UpdateItemComponent;
