import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form';
import { Button } from '../ui/button';
import TextInputField from '../input-elements/TextInputField';
import RadioInputField from '../shared/RadioInputField';
import { useUpdateWorkingField } from '@/hookQueries/useWorkingFieldHook';
import toast from 'react-hot-toast';
import {
  workingFieldSchema,
  type WorkingFieldObject,
} from '@/validations/workingFieldSchema';

interface WorkingFieldFormProps {
  initialData?: any | null;
  onUpdate: () => void; // Callback to trigger after successful update
}

const WorkingFieldForm = ({ initialData, onUpdate }: WorkingFieldFormProps) => {
  const methods = useForm({
    mode: 'onBlur',
    resolver: zodResolver(workingFieldSchema),
    defaultValues: initialData,
  });

  const { mutateAsync: updateItem, isPending } = useUpdateWorkingField(
    initialData?.id || '',
  );

  const {
    handleSubmit,
    register,
    control,
    reset,
    formState: { errors },
  } = methods;

  const onSubmit = async (data: WorkingFieldObject) => {
    const payload = {
      ...data,
      id: initialData?.id,
    };
    if (initialData) {
      // Call update API
      try {
        const res = await updateItem(payload);
        reset();
        toast.success('Working field updated successfully');
        onUpdate();
        return res;
      } catch (error) {
        toast.error('Failed to update working field. Please try again.');
      }
    } else {
      // Call create API (not implemented in this code snippet)
    }
  };

  return (
    <div className='bg-white p-5 rounded shadow w-2xl h-6/12'>
      <form onSubmit={handleSubmit(onSubmit)}>
        <div className='p-2 flex flex-col mb-2'>
          <TextInputField
            id='fieldName'
            label='Field Name'
            placeholder='Enter field name'
            register={register}
            required
            errors={errors}
          />
          <TextInputField
            id='fieldCode'
            label='Field Code'
            placeholder='Enter field code'
            register={register}
            required
            errors={errors}
          />
          <TextInputField
            id='description'
            type='textarea'
            label='Description'
            placeholder='Enter description'
            register={register}
            required
            errors={errors}
          />
          <br />
          <RadioInputField
            name='active'
            options={[
              { label: 'Active', value: 'true' },
              { label: 'Inactive', value: 'false' },
            ]}
            control={control}
            value={
              initialData?.active.toString() === 'Active' ? 'true' : 'false'
            }
            error={errors?.active?.message}
            required
          />
        </div>
        <Button
          disabled={isPending}
          type='submit'
          className='w-full'
          variant={'positive'}
        >
          {initialData ? 'Update Working Field' : 'Create Working Field'}
        </Button>
      </form>
    </div>
  );
};

export default WorkingFieldForm;
