import LockAccountPage from './pages/LockAccountPage';
import LoginPage from './pages/LoginPage';
import UnAuthorizePage from './pages/UnAuthorizePage';
import UserProfile from './pages/UserProfile';

export const authPaths = {
  login: '/login',
  unauthorized: '/unauthorized',
  profile: '/profile',
  lock: '/lock-account',
} as const;

export { authApi } from './api/authApi';

export {
  useActivateAccount,
  useGetUserInfo,
  useLogin,
  useLogOut,
} from './hooks/useAuthentication';

export {
  activateAccount,
  getUserInfo,
  login,
  logout,
  refreshToken,
  updatePassword,
} from './queries/authAction';

export { default as LoginPage } from './pages/LoginPage';
export { default as LockAccountPage } from './pages/LockAccountPage';
export { default as UnAuthorizePage } from './pages/UnAuthorizePage';
export { default as UserProfile } from './pages/UserProfile';

export { handleLogout, useAuthStore } from './store/AuthStore';

export {
  activatePasswordSchema,
  updatePasswordSchema,
  userManagementSchema,
  UserResponseObject,
  UserResponseSchema,
  userSchema,
  WorkProfile,
} from './schemas/userSchema';

export type {
  ActivatePasswordDTO,
  UpdatePasswordDTO,
  UserDTO,
  UserManagementDTO,
  UserManagementFormInput,
  UserResponse,
  UserResponseObjectType,
  WorkProfileType,
} from './schemas/userSchema';

export const authPages = {
  LoginPage,
  UnAuthorizePage,
  LockAccountPage,
  UserProfile,
} as const;
