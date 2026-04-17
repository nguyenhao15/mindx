import { FormProvider, useForm } from 'react-hook-form';
import {
  MAINTENANCE_STATUS_VALUES,
  MaintenanceUpdateFormSchema,
  MaintenanceUpdateRequest,
  type MaintenanceStatus,
  type UpdateMaintenanceFormInputDTO,
  type UpdateMaintenanceRequestDataDTO,
  type UpdateMaintenanceRequestDTO,
} from '../../schema/maintenaceSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import UpdateForm from './UpdateForm';
import { Button } from '@/components/ui/button';
import type { AuditUpdateJPADTO } from '@/validations/auditSchema';
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
  const methods = useForm<UpdateMaintenanceFormInputDTO>({
    resolver: zodResolver(MaintenanceUpdateFormSchema),
    defaultValues: {
      maintenancesStatus,
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

  const onSubmitData = async (
    formData: UpdateMaintenanceFormInputDTO,
    action?: string,
  ) => {
    const maintenanceUpdatePayload =
      MaintenanceUpdateFormSchema.parse(formData);

    const nextStatus = MAINTENANCE_STATUS_VALUES.includes(
      action as MaintenanceStatus,
    )
      ? (action as MaintenanceStatus)
      : maintenancesStatus;

    const updateAuditPayload: AuditUpdateJPADTO = {
      identifier: String(id),
      changeType: 'UPDATE',
      updateValue: nextStatus,
      module: 'MAINTENANCE',
      description: formData.description,
    };

    const requestDto: UpdateMaintenanceRequestDataDTO = {
      maintenancesStatus: nextStatus,
      reWork: maintenanceUpdatePayload.reWork,
      totalCost: maintenanceUpdatePayload.totalCost,
      isDeleted: maintenanceUpdatePayload.isDeleted,
      inspectAt: maintenanceUpdatePayload.inspectAt,
      completionAt: maintenanceUpdatePayload.completionAt,
      verifiedAt: maintenanceUpdatePayload.verifiedAt,
    };

    const sendingData: UpdateMaintenanceRequestDTO =
      MaintenanceUpdateRequest.parse({
        requestDto,
        auditUpdateRequest: updateAuditPayload,
      });

    const payload = {
      id: String(id),
      data: sendingData,
    };
    try {
      await mutateAsync(payload);
    } catch (error) {
      console.log('Error: ', error);
    }
  };

  const submitWithAction = (action?: string) =>
    handleSubmit((formData) => onSubmitData(formData, action));

  return (
    <div className='p-10 bg- rounded-lg w-full bg-white border border-slate-100 '>
      <FormProvider {...methods}>
        <form
          action=''
          onSubmit={submitWithAction()}
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
                type='button'
                className='mt-4'
                onClick={submitWithAction(action)}
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
