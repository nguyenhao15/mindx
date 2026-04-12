export { default as DepartmentPage } from './pages/DepartmentPage';

export const authPaths = {
  login: '/login',
  unauthorized: '/unauthorized',
  profile: '/profile',
  lock: '/lock-account',
} as const;