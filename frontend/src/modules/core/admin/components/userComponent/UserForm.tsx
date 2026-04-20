import {
  userManagementSchema,
  type UserManagementFormInput,
  type UserManagementDTO,
  type UserCreateDTO,
  userCreateSchema,
  WorkProfileCreate,
} from '@/modules/core/auth/schemas/userSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { FormProvider, useForm, useFormContext } from 'react-hook-form';
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
  updateMode?: boolean;
  initialUser: UserManagementDTO | null;
  error?: any;
}

const UserForm = ({
  updateMode = false,
  initialUser,
  error,
}: UserFormProps) => {
  const {
    register,
    formState: { errors },
    control,
  } = useFormContext();

  return (
    <div className='p-4 overflow-hidden'>
      <div className='h-full overflow-y-auto  bg-white border border-slate-100 rounded-xl '>
        <main className='xl:max-w-5xl lg:max-w-4xl md:max-w-3xl max-w-xl mx-auto px-6 py-8 pb-24'>
          {!updateMode && (
            <div className='mb-10 bg-blue-50 border border-blue-100 rounded-lg p-4 flex items-start gap-3'>
              <InfoIcon className='w-5 h-5 text-blue-600 shrink-0 mt-0.5' />
              <p className='text-sm text-blue-800'>
                <strong>Auto-Password Generation:</strong> The password will be
                auto-generated and sent to the user via email upon creation.
                They will be prompted to change it on their first login.
              </p>
            </div>
          )}

          {updateMode && (
            <div className='mb-10 bg-amber-50 border border-amber-100 rounded-lg p-4 flex items-start gap-3'>
              <InfoIcon className='w-5 h-5 text-amber-600 shrink-0 mt-0.5' />
              <p className='text-sm text-amber-800'>
                Update mode enabled. Save and lock actions are configured for
                API wiring.
              </p>
            </div>
          )}

          <ErrorCatchComponent error={error} />

          <div className='space-y-6'>
            <UserCredential register={register} errors={errors} />
            <SystemPermission register={register} errors={errors} />
            {!updateMode && <JobAssignmentCard control={control} />}
            {updateMode && (
              <>
                <UserAccountStatus />
                <WorkProfileInfo data={initialUser?.workProfileList || []} />
              </>
            )}
          </div>
        </main>
      </div>
    </div>
  );
};

export default UserForm;
