import { FormProvider, useForm } from 'react-hook-form';
import {
  MAINTENANCE_STATUS_VALUES,
  MaintenanceUpdateFormSchema,
  MaintenanceUpdateRequest,
  type MaintenanceStatus,
  type UpdateMaintenanceFormDTO,
  type UpdateMaintenanceRequestDTO,
} from '../../schema/maintenaceSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import UpdateForm from './UpdateForm';
import { Button } from '@/components/ui/button';
import {
  AuditUpdateJPASchema,
  type AuditUpdateJPADTO,
} from '@/validations/auditSchema';
import {
  useGetAvailableActionUpdate,
  useUpdateMaintance,
} from '../../hooks/useMaintenanceHooks';
import toast from 'react-hot-toast';
import { useEffect } from 'react';
import { parse } from 'date-fns';

interface UpdateItemComponentProps {
  id: number;
  data: any;
  afterUpdate?: () => void;
}

const UpdateItemComponent = ({
  id,
  data,
  afterUpdate,
}: UpdateItemComponentProps) => {
  const methods = useForm({
    mode: 'onBlur',
    resolver: zodResolver(MaintenanceUpdateFormSchema),
    defaultValues: data,
  });

  const { mutateAsync, isPending } = useUpdateMaintance();

  const { data: availableActions } = useGetAvailableActionUpdate(id, {
    enabled: !!id,
  });

  const {
    handleSubmit,
    register,
    setError,
    control,
    reset,
    formState: { errors },
  } = methods;

  useEffect(() => {
    reset({
      ...data,
      identifier: String(id),
      type: 'UPDATE',
      module: 'MAINTENANCE',
    });
  }, [data, id]);

  const onSubmitData = async (
    formData: UpdateMaintenanceFormDTO,
    action?: string,
  ) => {
    if (action === 'APPROVED' && !formData.inspectAt) {
      setError('inspectAt', {
        type: 'manual',
        message: 'Ngày kiểm tra là bắt buộc khi duyệt bảo trì',
      });
      return;
    }
    const maintenanceUpdatePayload =
      MaintenanceUpdateFormSchema.parse(formData);

    const auditPayload = AuditUpdateJPASchema.parse(formData);

    const nextStatus = MAINTENANCE_STATUS_VALUES.includes(
      action as MaintenanceStatus,
    )
      ? (action as MaintenanceStatus)
      : data.maintenancesStatus;

    const updateAuditPayload: AuditUpdateJPADTO = {
      ...auditPayload,
      updateValue: nextStatus,
      description: formData.description || '',
    };

    const sendingData: UpdateMaintenanceRequestDTO =
      MaintenanceUpdateRequest.parse({
        requestDto: MaintenanceUpdateFormSchema.parse({
          ...maintenanceUpdatePayload,
          maintenancesStatus: nextStatus,
        }),
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
      <h3 className='text-center font-bold'> Cập nhật bảo trì</h3>
      <FormProvider {...methods}>
        <form
          action=''
          onSubmit={submitWithAction()}
          className='w-full min-w-96'
        >
          <UpdateForm
            control={control}
            isLoading={isPending}
            register={register}
            errors={errors}
          />
        </form>
      </FormProvider>
      <div
        className={`grid grid-cols-${availableActions?.length} gap-2 p-2 items-center justify-self-center`}
      >
        {availableActions?.length > 0 &&
          availableActions.map(
            (action: {
              label: string;
              nextStatus: string;
              actionType: string;
            }) => (
              <Button
                key={action.nextStatus}
                variant={action.actionType.toLowerCase() as any}
                className='mt-4 cursor-pointer'
                onClick={submitWithAction(action.nextStatus)}
                disabled={isPending}
              >
                {action?.label}
              </Button>
            ),
          )}
      </div>
    </div>
  );
};

export default UpdateItemComponent;
