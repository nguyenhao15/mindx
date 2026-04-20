import { Controller, useFormContext } from 'react-hook-form';

import { Switch } from '@/components/input-elements/Switch';
import type { UserManagementDTO } from '@/modules/core/auth/schemas/userSchema';

const UserAccountStatus = () => {
  const { watch, control } = useFormContext<UserManagementDTO>();

  const enabled = watch('enabled');
  const accountNonLocked = watch('accountNonLocked');

  console.log('Enabled: ', enabled, 'Account non locked: ', accountNonLocked);

  return (
    <div className='space-y-4'>
      <div>
        <h2 className='text-lg font-semibold text-gray-900'>Account Status</h2>
        <p className='text-sm text-gray-500'>
          Manage user activation and lock state.
        </p>
      </div>
      <div className='bg-white rounded-xl shadow-sm border border-gray-200 p-8 space-y-5'>
        <Controller
          control={control}
          name='enabled'
          render={({ field: { onChange, value, ...rest } }) => (
            <Switch
              id='enabled-toggle'
              {...rest}
              checked={enabled}
              onChange={onChange}
              label={enabled ? 'Enabled' : 'Disabled'}
            />
          )}
        />

        <Controller
          control={control}
          name='accountNonLocked'
          render={({ field: { onChange, value, ...rest } }) => (
            <Switch
              id='non-locked-toggle'
              {...rest}
              checked={accountNonLocked}
              onChange={onChange}
              label={accountNonLocked ? 'Account Non Locked' : 'Account Locked'}
            />
          )}
        />
      </div>
    </div>
  );
};

export default UserAccountStatus;
