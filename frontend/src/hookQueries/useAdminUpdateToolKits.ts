import {
  useAddUser,
  useLockUser,
  useResetPassword,
  useUpdateUser,
} from './useAdminHook';

export const useAdminUpdateToolKits = (userId: string, options = {}) => {
  const {
    mutateAsync: createUser,
    isPending: isCreating,
    error: createUserError,
  } = useAddUser();
  const {
    mutateAsync: lockUser,
    isPending: isLocking,
    error: lockUserError,
  } = useLockUser(userId);
  const {
    mutateAsync: updateUser,
    isPending: isUpdating,
    error: updateUserError,
  } = useUpdateUser(userId);
  const {
    mutateAsync: resetPassword,
    isPending: isResettingPassword,
    error: resetPasswordError,
  } = useResetPassword(userId);

  const isLoading =
    isCreating || isLocking || isUpdating || isResettingPassword;
  const error =
    createUserError || lockUserError || updateUserError || resetPasswordError;

  return {
    createUser,
    lockUser,
    updateUser,
    resetPassword,
    isLoading,
    error,
  };
};
