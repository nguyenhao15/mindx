import { userSchema, type UserDTO } from '@/validations/userSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { FormProvider, useForm } from 'react-hook-form';
import { InfoIcon } from 'lucide-react';
import { Button } from '../ui/button';
import UserCredential from './FormElement/UserCredential';
import SystemPermission from './FormElement/SystemPermission';
import JobAssignmentManagment from './FormElement/JobAssignmentManagment';
import { useAddUser } from '@/hookQueries/useAdminHook';

const UserForm = ({
  initialUser,
  onClose,
}: {
  initialUser?: UserDTO;
  onClose: () => void;
}) => {
  const methods = useForm<UserDTO>({
    mode: 'onBlur',
    resolver: zodResolver(userSchema),
    defaultValues: initialUser || {},
  });

  const { mutateAsync: createUser, isPending } = useAddUser();

  const {
    handleSubmit,
    register,
    watch,
    formState: { errors },
  } = methods;

  const onSubmit = async (data: UserDTO) => {
    try {
      await createUser(data);
      onClose();
    } catch (error) {
      console.error('Error creating user:', error);
    }
  };

  console.log('Errors: ', errors);

  return (
    <div className='p-10 h-screen w-screen overflow-hidden'>
      <div className='h-full overflow-y-auto  bg-white border border-slate-100 rounded-xl shadow-sm'>
        <header className='sticky top-0 z-40 bg-white border-b border-gray-200 shadow-sm'>
          <div className='xl:max-w-5xl lg:max-w-4xl md:max-w-3xl max-w-xl mx-auto px-6 py-6 flex items-center justify-between'>
            <h1 className='text-2xl font-bold text-gray-900 tracking-tight'>
              {initialUser ? 'Update User' : 'Create New User'}
            </h1>
            <div className='flex items-center gap-4'>
              <Button variant='outline' type='button' onClick={onClose}>
                Cancel
              </Button>
              <Button
                variant='positive'
                type='submit'
                onClick={handleSubmit(onSubmit)}
              >
                {initialUser ? 'Update User' : 'Create User'}
              </Button>
            </div>
          </div>
        </header>
        <main className='xl:max-w-5xl lg:max-w-4xl md:max-w-3xl max-w-xl mx-auto px-6 py-8 pb-24'>
          <div className='mb-10 bg-blue-50 border border-blue-100 rounded-lg p-4 flex items-start gap-3'>
            <InfoIcon className='w-5 h-5 text-blue-600 shrink-0 mt-0.5' />
            <p className='text-sm text-blue-800'>
              <strong>Auto-Password Generation:</strong> The password will be
              auto-generated and sent to the user via email upon creation. They
              will be prompted to change it on their first login.
            </p>
          </div>
          <FormProvider {...methods}>
            <form onSubmit={handleSubmit(onSubmit)}>
              <div className='space-y-6'>
                <UserCredential register={register} errors={errors} />
                <SystemPermission register={register} errors={errors} />
                <JobAssignmentManagment
                  initialForm={initialUser?.workProfileList || []}
                />
              </div>
            </form>
          </FormProvider>
        </main>
      </div>
    </div>
  );
};

export default UserForm;
