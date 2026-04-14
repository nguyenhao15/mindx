import { zodResolver } from '@hookform/resolvers/zod';
import { FormProvider, useForm } from 'react-hook-form';
import {
  MaintenanceRequest,
  type CreateMaintenanceRequestDTO,
} from '../schema/maintenaceSchema';
import { Button } from '@/components/ui/button';
import MaintanceForm from '../components/Create/MaintanceForm';
import { useCreateMaintance } from '../hooks/useMaintenanceHooks';
import toast from 'react-hot-toast';
import InfoComponents from '../components/Create/InfoComponents';

const CreatePage = () => {
  const { mutateAsync: createItem, isPending } = useCreateMaintance();

  const methods = useForm<CreateMaintenanceRequestDTO>({
    mode: 'onBlur',
    resolver: zodResolver(
      MaintenanceRequest.pick({
        description: true,
        maintenanceCategoryId: true,
        maintenanceItemId: true,
        issueDate: true,
        locationId: true,
        totalCost: true,
        attachments: true,
      }),
    ),
    defaultValues: {
      description: '',
      issueDate: new Date(),
      locationId: '',
      totalCost: 0,
    },
  });

  const { reset, handleSubmit } = methods;

  const onSubmit = async (data: CreateMaintenanceRequestDTO) => {
    const formData = new FormData();
    const { attachments, ...rest } = data;

    const jsonBlob = new Blob([JSON.stringify(rest)], {
      type: 'application/json',
    });
    formData.append('data', jsonBlob);
    attachments.forEach((file: any) => {
      formData.append('files', file);
    });

    try {
      await createItem(formData);
      reset();
      toast.success('Maintenance request created successfully');
    } catch (error) {
      toast.error('Failed to create maintenance request');
    }
  };

  return (
    <div className='flex flex-col gap-6 items-center'>
      <div className='flex justify-center items-start flex-col gap-4 lg:flex-row'>
        <div className='flex-1 min-w-0'>
          <FormProvider {...methods}>
            <form onSubmit={handleSubmit(onSubmit)} className='space-y-4'>
              <MaintanceForm />
            </form>
          </FormProvider>
        </div>
        <div className='w-full lg:w-80 flex flex-col gap-4'>
          <InfoComponents />
          <Button
            disabled={isPending}
            className='cursor-pointer w-full py-3 font-semibold bg-[#1d3557] hover:bg-[#162840]'
            variant='default'
            onClick={handleSubmit(onSubmit)}
          >
            Gửi yêu cầu bảo trì
          </Button>
        </div>
      </div>
    </div>
  );
};

export default CreatePage;
