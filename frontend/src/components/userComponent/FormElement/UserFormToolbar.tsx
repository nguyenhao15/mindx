import { Button } from '@/components/ui/button';

type UserFormToolbarProps = {
  isUpdateMode: boolean;
  isSubmitting: boolean;
  accountNonLocked: boolean;
  onClose: () => void;
  onSave: () => void;
  onLockUser: () => void;
  onResetPassword?: () => void;
};

const UserFormToolbar = ({
  isUpdateMode,
  isSubmitting,
  accountNonLocked,
  onClose,
  onSave,
  onLockUser,
  onResetPassword,
}: UserFormToolbarProps) => {
  return (
    <header className='sticky top-0 z-40 bg-white border-b border-gray-200 shadow-sm'>
      <div className='xl:max-w-5xl lg:max-w-4xl md:max-w-3xl max-w-xl mx-auto px-6 py-6 flex items-center justify-between'>
        <h1 className='text-2xl font-bold text-gray-900 tracking-tight'>
          {isUpdateMode ? 'Update User' : 'Create New User'}
        </h1>

        <div className='flex items-center gap-4'>
          {isUpdateMode && (
            <Button
              variant='brand'
              type='button'
              onClick={onResetPassword}
              disabled={isSubmitting}
            >
              Reset Password
            </Button>
          )}
          {isUpdateMode && (
            <Button
              variant={accountNonLocked ? 'warning' : 'positive'}
              type='button'
              onClick={onLockUser}
              disabled={isSubmitting}
            >
              {accountNonLocked ? 'Lock User' : 'Unlock User'}
            </Button>
          )}
          <Button
            variant='outline'
            type='button'
            onClick={onClose}
            disabled={isSubmitting}
          >
            Cancel
          </Button>
          <Button
            variant='positive'
            type='button'
            onClick={onSave}
            disabled={isSubmitting}
          >
            {isUpdateMode ? 'Update User' : 'Create User'}
          </Button>
        </div>
      </div>
    </header>
  );
};

export default UserFormToolbar;
