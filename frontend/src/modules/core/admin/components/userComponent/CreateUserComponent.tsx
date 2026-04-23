import {
  userCreateSchema,
  WorkProfileCreate,
} from '@/modules/core/auth/schemas/userSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { FormProvider, useForm } from 'react-hook-form';
import UserForm from './UserForm';
import { useAddUser } from '../../hooks/useAdminHook';
import toast from 'react-hot-toast';
import { Button } from '@/components/ui/button';

const CreateUserComponent = ({ afterSubmit }: { afterSubmit: () => void }) => {
  const methods = useForm({
    mode: 'onBlur',
    resolver: zodResolver(userCreateSchema),
  });

  const { handleSubmit, reset } = methods;

  const { mutateAsync: createUser, isPending, isError, error } = useAddUser();

  const onSubmit = async (data: any) => {
    const staffProfie = WorkProfileCreate.parse(data);
    const payload = {
      staffProfileRequestDto: staffProfie,
      ...data,
    };
    try {
      await createUser(payload);
      reset();
      toast.success('User created successfully');
      afterSubmit();
    } catch (error) {
      console.error('Error saving user:', error);
      toast.error('Failed to save user');
    }
  };

  return (
    <div>
      <FormProvider {...methods}>
        <form
          onSubmit={handleSubmit(onSubmit)}
          className=' bg-white p-6 rounded-lg shadow-md'
        >
          <UserForm
            isLoading={isPending}
            updateMode={false}
            initialUser={null}
          />
          <div className='sticky bottom-0 bg-white p-2 m-2 items-center justify-end w-full flex gap-2 border-t border-gray-200'>
            <Button
              disabled={isPending}
              variant='positive'
              className='mb-4 cursor-pointer'
            >
              Tạo người dùng mới
            </Button>
          </div>
        </form>
      </FormProvider>
    </div>
  );
};

export default CreateUserComponent;
