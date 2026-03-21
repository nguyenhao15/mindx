import { useFormContext } from 'react-hook-form';

import { Switch } from '@/components/shared/Switch';
import type { UserManagementDTO } from '@/validations/userSchema';

const UserAccountStatus = () => {
  const { watch, setValue } = useFormContext<UserManagementDTO>();

  const enabled = watch('enabled');
  const accountNonLocked = watch('accountNonLocked');

  return (
    <div className='space-y-4'>
      <div>
        <h2 className='text-lg font-semibold text-gray-900'>Account Status</h2>
        <p className='text-sm text-gray-500'>
          Manage user activation and lock state.
        </p>
      </div>
      <div className='bg-white rounded-xl shadow-sm border border-gray-200 p-8 space-y-5'>
        <Switch
          id='enabled-toggle'
          checked={enabled}
          onChange={(checked) => setValue('enabled', checked)}
          label={enabled ? 'Enabled' : 'Disabled'}
        />

        <Switch
          id='non-locked-toggle'
          checked={accountNonLocked}
          onChange={(checked) => setValue('accountNonLocked', checked)}
          label={accountNonLocked ? 'Account Non Locked' : 'Account Locked'}
        />
      </div>
    </div>
  );
};

export default UserAccountStatus;
