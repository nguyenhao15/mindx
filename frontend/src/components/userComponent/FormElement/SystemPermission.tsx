import RadioInputField from '@/components/shared/RadioInputField';

import { useFormContext } from 'react-hook-form';

const SystemPermission = ({
  register,
  errors,
}: {
  register: any;
  errors: any;
}) => {
  const { control, watch } = useFormContext();
  return (
    <div className='space-y-4'>
      <div>
        <h2 className='text-lg font-semibold text-gray-900'>
          System Permission
        </h2>
        <p className='text-sm text-gray-500'>
          Basic login information for the new user.
        </p>
      </div>
      <RadioInputField
        required
        control={control}
        label='Quyền hệ thống'
        name='systemRole'
        value='USER'
        options={[
          { label: 'Admin', value: 'ADMIN' },
          { label: 'User', value: 'USER' },
        ]}
        error={errors.systemRole?.message}
      />
    </div>
  );
};

export default SystemPermission;
