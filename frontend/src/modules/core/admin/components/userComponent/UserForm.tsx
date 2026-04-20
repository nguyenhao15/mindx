import {
  userManagementSchema,
  type UserManagementFormInput,
  type UserManagementDTO,
  type UserCreateDTO,
  userCreateSchema,
} from '@/modules/core/auth/schemas/userSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { FormProvider, useForm } from 'react-hook-form';
import { InfoIcon } from 'lucide-react';
import UserCredential from './FormElement/UserCredential';
import SystemPermission from './FormElement/SystemPermission';
import UserAccountStatus from './FormElement/UserAccountStatus';
import UserFormToolbar from './FormElement/UserFormToolbar';
import toast from 'react-hot-toast';
import { useAdminUpdateToolKits } from '@/modules/core/admin/hooks/useAdminUpdateToolKits';
import ErrorCatchComponent from '@/components/shared/ErrorCatchComponent';
import WorkProfileInfo from './WorkProfileInfo';
import { JobAssignmentCard } from './FormElement/JobAssignmentCard';

interface UserFormProps {
  initialUser: UserManagementDTO | null;
  onClose: () => void;
}

const UserForm = ({ initialUser, onClose }: UserFormProps) => {
  const isUpdateMode = !!initialUser?._id;
  const schemaToUse = isUpdateMode ? userManagementSchema : userCreateSchema;
  const methods = useForm({
    mode: 'onBlur',
    resolver: zodResolver(schemaToUse),
    defaultValues: {
      ...(isUpdateMode && {
        _id: initialUser?._id,
        workProfileList: initialUser?.workProfileList ?? [],
        enabled: initialUser?.enabled ?? true,
        accountNonLocked: initialUser?.accountNonLocked ?? true,
      }),
      staffId: initialUser?.staffId ?? '',
      fullName: initialUser?.fullName ?? '',
      email: initialUser?.email ?? '',
      systemRole: initialUser?.systemRole ?? 'USER',
    },
  });

  const { createUser, updateUser, lockUser, resetPassword, isLoading, error } =
    useAdminUpdateToolKits(initialUser?._id ?? '');

  const {
    handleSubmit,
    register,
    watch,
    formState: { errors },
  } = methods;

  const onSubmit = async (formData: any) => {
    try {
      if (isUpdateMode) {
        const payloadToUpdate = userManagementSchema.parse(formData);
        await updateUser(payloadToUpdate);
        toast.success('User updated successfully');
        onClose();
        return;
      }

      const payloadToCreate = userCreateSchema.parse(formData);
      await createUser(payloadToCreate);
      toast.success('User created successfully');
      onClose();
    } catch (error) {
      console.error('Error saving user:', error);
      toast.error('Failed to save user');
    }
  };

  const handleLockUser = async () => {
    const currentLockState = watch('accountNonLocked') ?? true;
    await lockUser({ locked: !currentLockState });
    methods.setValue('accountNonLocked', !currentLockState);
  };

  const handleResetPassword = async () => {
    try {
      await resetPassword();
      toast.success('Password reset successfully');
    } catch (error) {
      toast.error('Failed to reset password');
    }
  };

  return (
    <div className='p-10 h-screen w-screen overflow-hidden'>
      <div className='h-full overflow-y-auto  bg-white border border-slate-100 rounded-xl shadow-sm'>
        <UserFormToolbar
          isUpdateMode={isUpdateMode}
          isSubmitting={isLoading}
          accountNonLocked={watch('accountNonLocked') ?? true}
          onClose={onClose}
          onResetPassword={handleResetPassword}
          onSave={handleSubmit(onSubmit)}
          onLockUser={handleLockUser}
        />
        <main className='xl:max-w-5xl lg:max-w-4xl md:max-w-3xl max-w-xl mx-auto px-6 py-8 pb-24'>
          {!isUpdateMode && (
            <div className='mb-10 bg-blue-50 border border-blue-100 rounded-lg p-4 flex items-start gap-3'>
              <InfoIcon className='w-5 h-5 text-blue-600 shrink-0 mt-0.5' />
              <p className='text-sm text-blue-800'>
                <strong>Auto-Password Generation:</strong> The password will be
                auto-generated and sent to the user via email upon creation.
                They will be prompted to change it on their first login.
              </p>
            </div>
          )}

          {isUpdateMode && (
            <div className='mb-10 bg-amber-50 border border-amber-100 rounded-lg p-4 flex items-start gap-3'>
              <InfoIcon className='w-5 h-5 text-amber-600 shrink-0 mt-0.5' />
              <p className='text-sm text-amber-800'>
                Update mode enabled. Save and lock actions are configured for
                API wiring.
              </p>
            </div>
          )}

          <ErrorCatchComponent error={error} />

          <FormProvider {...methods}>
            <form onSubmit={handleSubmit(onSubmit)}>
              <div className='space-y-6'>
                <UserCredential register={register} errors={errors} />
                <SystemPermission register={register} errors={errors} />
                <UserAccountStatus />
                {!isUpdateMode && (
                  <JobAssignmentCard control={methods.control} />
                )}
                {isUpdateMode && (
                  <WorkProfileInfo data={initialUser?.workProfileList || []} />
                )}
              </div>
            </form>
          </FormProvider>
        </main>
      </div>
    </div>
  );
};

export default UserForm;
