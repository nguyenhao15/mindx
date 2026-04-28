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
import toast from 'react-hot-toast';

interface UpdateItemComponentProps {
  id: number;
  maintenancesStatus: MaintenanceStatus;
  reWork: boolean;
  totalCost: number;
  afterUpdate?: () => void;
}

const UpdateItemComponent = ({
  id,
  maintenancesStatus,
  reWork,
  totalCost,
  afterUpdate,
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

  const { data } = useGetAvailableActionUpdate(id, {
    enabled: !!id,
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
      assignedTo: maintenanceUpdatePayload.assignedTo,
      verifiedAt: maintenanceUpdatePayload.verifiedAt,
      ...maintenanceUpdatePayload,
    };

    const sendingData: UpdateMaintenanceRequestDTO =
      MaintenanceUpdateRequest.parse({
        requestDto,
        auditUpdateRequest: updateAuditPayload,
      });
    const formdata = new FormData();
    formdata.append(
      'data',
      new Blob([JSON.stringify(sendingData)], { type: 'application/json' }),
    );
    const payload = {
      id: String(id),
      data: formdata,
    };
    try {
      await mutateAsync(payload);
      toast.success('Cập nhật thành công');
      if (afterUpdate) {
        afterUpdate();
      }
    } catch (error: any) {
      toast.error(error.response?.data?.message || 'Cập nhật thất bại');
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
        <div
          className={`grid grid-cols-${data?.length} gap-2 p-2 items-center justify-self-center`}
        >
          {data?.length > 0 &&
            data.map(
              (action: {
                label: string;
                nextStatus: string;
                actionType: string;
              }) => (
                <Button
                  key={action.nextStatus}
                  type='button'
                  variant={action.actionType.toLowerCase() as any}
                  className='mt-4'
                  onClick={submitWithAction(action.nextStatus)}
                  disabled={isPending}
                >
                  {action?.label}
                </Button>
              ),
            )}
        </div>
      </FormProvider>
    </div>
  );
};

export default UpdateItemComponent;
