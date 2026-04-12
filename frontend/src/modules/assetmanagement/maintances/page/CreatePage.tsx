import { zodResolver } from '@hookform/resolvers/zod';
import React from 'react';
import { useForm } from 'react-hook-form';
import type { CreateMaintenanceRequestDTO } from '../schema/maintenaceSchema';

const CreatePage = () => {
  const methods = useForm<CreateMaintenanceRequestDTO>({

    defaultValues: {
      // Define your form's default values here
    },
  });
  return (
    <div className='p-6 bg-white rounded-lg shadow-md'>
      <h1 className='text-2xl font-bold'>Create New Asset</h1>
      <p className='mt-4 text-gray-600'>
        Use this page to create a new asset. Fill in the required information
        and submit the form.
      </p>
    </div>
  );
};

export default CreatePage;
