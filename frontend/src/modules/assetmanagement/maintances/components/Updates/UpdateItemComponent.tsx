import { FormProvider, useForm } from 'react-hook-form';
import {
  MaintenanceUpdateRequest,
  type MaintenanceStatus,
} from '../../schema/maintenaceSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import UpdateForm from './UpdateForm';
import { Button } from '@/components/ui/button';

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
    <div className='p-10 bg-slate-100 rounded-lg w-full'>
      <FormProvider {...methods}>
        <form
          action=''
          onSubmit={handleSubmit(onSubmitData)}
          className='w-full min-w-96'
        >
          <UpdateForm control={control} register={register} errors={errors} />
        </form>
        <Button
          type='submit'
          variant={'default'}
          onClick={handleSubmit(onSubmitData)}
          className='mt-4'
        >
          Cập Nhật
        </Button>
      </FormProvider>
    </div>
  );
};

export default UpdateItemComponent;
