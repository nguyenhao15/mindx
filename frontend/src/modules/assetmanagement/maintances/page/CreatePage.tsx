import { zodResolver } from '@hookform/resolvers/zod';
import React from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import {
  MaintenanceRequest,
  type CreateMaintenanceRequestDTO,
} from '../schema/maintenaceSchema';
import { Button } from '@/components/ui/button';
import MaintanceForm from '../components/Create/MaintanceForm';

const CreatePage = () => {
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
      }),
    ),
    defaultValues: {
      description: '',
      issueDate: '',
      locationId: '',
      totalCost: 0,
    },
  });

  const { handleSubmit } = methods;

  const onSubmit = async (data: CreateMaintenanceRequestDTO) => {
    console.log('Form data:', data);
  };

  return (
    <div className='p-6 bg-white rounded-lg shadow-md'>
      <h1 className='text-2xl font-bold'>Create New Maintenance Request</h1>
      <p className='mt-4 text-gray-600'>
        Use this page to create a new maintenance request. Fill in the required
        information and submit the form.
      </p>
      <FormProvider {...methods}>
        <form
          onSubmit={handleSubmit(onSubmit)}
          className='mt-6 space-y-4 w-full'
        >
          <MaintanceForm />
          <Button type='submit' variant={'positive'}>
            Submit Maintenance Request
          </Button>
        </form>
      </FormProvider>
    </div>
  );
};

export default CreatePage;
