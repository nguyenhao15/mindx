import AttachmentControl from '@/modules/core/attachments/components/AttachmentControlComponent';
import { zodResolver } from '@hookform/resolvers/zod';
import React from 'react';
import { Controller, FormProvider, useForm } from 'react-hook-form';
import { MaintenanceEntity } from '../../schema/maintenaceSchema';
import { Button } from '@/components/ui/button';
import TextInputField from '@/components/input-elements/TextInputField';
import { useUpdateMaintance } from '../../hooks/useMaintenanceHooks';
import type { AuditUpdateJPADTO } from '@/validations/auditSchema';
import toast from 'react-hot-toast';

interface FinishedComponentProps {
  id: number;
  afterUpdate: () => void;
}

const FinishedComponent = ({ id, afterUpdate }: FinishedComponentProps) => {
  const { mutateAsync: updateAction, isPending: isUpdating } =
    useUpdateMaintance();
  const methods = useForm({
    mode: 'onBlur',
    resolver: zodResolver(
      MaintenanceEntity.pick({
        attachments: true,
        totalCost: true,
        maintenancesStatus: true,
      }),
    ),
    defaultValues: {
      attachments: [],
      totalCost: 0,
      maintenancesStatus: 'FINISHED',
    },
  });

  const {
    register,
    control,
    handleSubmit,
    formState: { errors },
  } = methods;

  const onSubmit = async (data: any) => {
    const { attachments, ...rest } = data;

    const updateAuditPayload: AuditUpdateJPADTO = {
      identifier: String(id),
      changeType: 'UPDATE',
      updateValue: 'FINISHED',
      module: 'MAINTENANCE',
      description: 'Hoàn thành công việc bảo trì',
    };

    const dataToUpdate = {
      requestDto: rest,
      auditUpdateRequest: updateAuditPayload,
    };
    const formData = new FormData();
    formData.append(
      'data',
      new Blob([JSON.stringify(dataToUpdate)], { type: 'application/json' }),
    );

    if (attachments && attachments.length > 0) {
      attachments.forEach((file: any) => {
        formData.append('attachments', file);
      });
    }

    const payload = {
      id: String(id),
      data: formData,
    };

    try {
      await updateAction(payload);
      afterUpdate();
      toast.success('Cập nhật thành công!');
    } catch (error) {
      console.error('Error updating maintenance:', error);
      toast.error('Cập nhật thất bại. Vui lòng thử lại.');
    }
  };

  return (
    <div className='flex flex-col gap-4 bg-white p-4 rounded-lg'>
      <FormProvider {...methods}>
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className='flex flex-col p-4 gap-4'>
            <TextInputField
              id='totalCost'
              labelSize='lg'
              label='Tổng chi phí'
              type='number'
              errors={errors}
              control={control}
              register={register}
            />
            <Controller
              name='attachments'
              control={control}
              render={({ field: { onChange, value, ...rest } }) => (
                <AttachmentControl
                  title='Biên bản nghiệm thu/Hình ảnh hoàn thành'
                  onFileAttach={onChange}
                  errorMessage={errors.attachments?.message}
                  attachedFile={value}
                  {...rest}
                />
              )}
            />
          </div>
          <Button
            className='self-end cursor-pointer'
            onClick={handleSubmit(onSubmit)}
            variant={'positive'}
          >
            Hoàn thành
          </Button>
        </form>
      </FormProvider>
    </div>
  );
};

export default FinishedComponent;
