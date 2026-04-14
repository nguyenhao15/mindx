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

  const {
    reset,
    handleSubmit,
    formState: { errors },
  } = methods;

  console.log();

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
    <div className='p-6 w-fit bg-white rounded-lg shadow-md'>
      <h1 className='text-2xl font-bold'>Create New Maintenance Request</h1>
      <p className='mt-4 text-gray-600'>
        Use this page to create a new maintenance request. Fill in the required
        information and submit the form.
      </p>
      <div className='flex justify-center items-start flex-col gap-2 lg:flex-row p-2'>
        <div className='w-2/3 p-2'>
          <FormProvider {...methods}>
            <form
              onSubmit={handleSubmit(onSubmit)}
              className='mt-6 space-y-4 w-full'
            >
              <MaintanceForm />
            </form>
          </FormProvider>
        </div>
        <div className='w-1/3 grid p-2 mt-6 space-y-4'>
          <InfoComponents />
          <Button
            disabled={isPending}
            className='cursor-pointer p-5 font-bold'
            variant={'default'}
            onClick={handleSubmit(onSubmit)}
          >
            Submit Maintenance Request
          </Button>
        </div>
      </div>
    </div>
  );
};

export default CreatePage;
