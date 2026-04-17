import { FormProvider, useForm } from 'react-hook-form';
import {
  MaintenanceUpdateRequest,
  type MaintenanceStatus,
} from '../../schema/maintenaceSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import UpdateForm from './UpdateForm';
import { Button } from '@/components/ui/button';
import {
  useGetAvailableActionUpdate,
  useUpdateMaintance,
} from '../../hooks/useMaintenanceHooks';

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
      maintenancesStatus: 'APPROVED',
      module: 'MAINTENANCE',
      changeType: 'UPDATE',
      identifier: String(id),
      reWork,
      totalCost,
    },
  });

  const { mutateAsync, isPending } = useUpdateMaintance();

  const { data } = useGetAvailableActionUpdate(maintenancesStatus, {
    enabled: !!maintenancesStatus,
  });

  const {
    handleSubmit,
    register,
    control,
    formState: { errors },
  } = methods;

  const onSubmitData = async (data: any) => {
    const maintenanceUpdatePayload = MaintenanceUpdateRequest.parse(data);

    const updateAuditPayload = {
      identifier: String(id),
      changeType: 'UPDATE',
      updateValue: 'APPROVED',
      module: 'MAINTENANCE',
      description: data.description,
    };

    const { maintenancesStatus, ...rest } = maintenanceUpdatePayload;

    const sendinData = {
      maintenancesStatus: 'APPROVED' as MaintenanceStatus,
      ...rest,
    };

    const finalPayload = {
      requestDto: sendinData,
      auditUpdateRequest: updateAuditPayload,
    };

    const payload = {
      id: String(id),
      data: finalPayload,
    };
    try {
      await mutateAsync(payload);
    } catch (error) {
      console.log('Error: ', error);
    }
  };

  console.log('Errors: ', errors);

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
                disabled={isPending}
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
