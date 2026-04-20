import './App.css';
import Loader from './components/shared/Loader';
import ActivatePassword from './modules/core/auth/components/ActivatePassword';
import StaffProfile from './modules/core/auth/components/StaffProfile';
import { useGetUserInfo } from './modules/core/auth/hooks/useAuthentication';
import LockAccountPage from './modules/core/auth/pages/LockAccountPage';
import LoginPage from './modules/core/auth/pages/LoginPage';
import UnAuthorizePage from './modules/core/auth/pages/UnAuthorizePage';
import UserProfile from './modules/core/auth/pages/UserProfile';
import {
  handleLogout,
  useAuthStore,
} from './modules/core/auth/store/AuthStore';
import HomePage from './pages/HomePage';
import { Suspense, lazy } from 'react';
import { Toaster } from 'react-hot-toast';
import {
  Navigate,
  BrowserRouter as Router,
  Route,
  Routes,
} from 'react-router-dom';

const NotFound = lazy(() => import('./pages/NotFoundComponent'));
const DocumentRoute = lazy(
  () => import('./modules/documentations/document/routes/DocumentRoute'),
);
const AdminRoute = lazy(() => import('./modules/core/admin/routes/AdminRoute'));
const AssetRoute = lazy(
  () => import('./modules/assetmanagement/routes/AssetManagementRoutes'),
);

function App() {
  const { isLoading, isError } = useGetUserInfo();
  const user = useAuthStore((state) => state.user);
  const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
  const isProfileSet = localStorage.getItem('profile_id') !== null;

  const isAuthenticated = !isError && !!user;
  const isAdmin = user?.systemRole === 'ADMIN';
  const isEnabled = user?.enabled;
  const isLocked = !user?.accountNonLocked;
  const shouldShowLoading = isLoading && !user && isLoggedIn;

  if (shouldShowLoading) {
    return (
      <div className='min-h-screen mx-auto my-auto justify-center items-center flex'>
        <Loader text={'Loading data...'} />
      </div>
    );
  }

  console.log('Is Profile set: ', isProfileSet);

  if (!isLoggedIn) {
    handleLogout();
  }

  if (isAuthenticated && isEnabled && isLocked) {
    return <LockAccountPage />;
  }

  if (isAuthenticated && !isEnabled) {
    return (
      <div className='m-auto'>
        <ActivatePassword />
      </div>
    );
  }

  if (isAuthenticated && !isProfileSet) {
    return (
      <StaffProfile
        profiles={user.workProfileList || []}
        onSelect={() => {
          window.location.reload();
        }}
      />
    );
  }

  return (
    <>
      <Router>
        <Routes>
          <Route
            path='/login'
            element={isAuthenticated ? <Navigate to='/' /> : <LoginPage />}
          />

          {isAdmin && (
            <Route
              path='/admin/*'
              element={
                <Suspense fallback={<Loader text={'Loading admin...'} />}>
                  <AdminRoute />
                </Suspense>
              }
            />
          )}
          <Route path='/' element={<HomePage />} />
          <Route
            path='/assets/*'
            element={
              <Suspense
                fallback={<Loader text={'Loading asset management...'} />}
              >
                <AssetRoute />
              </Suspense>
            }
          />

          <Route
            path='/docs/*'
            element={
              <Suspense fallback={<Loader text={'Loading docs...'} />}>
                <DocumentRoute />
              </Suspense>
            }
          />

          <Route path='/unauthorized' element={<UnAuthorizePage />} />
          <Route path='/profile' element={<UserProfile user={user!} />} />

          <Route
            path='*'
            element={
              <Suspense fallback={<Loader text={'Loading...'} />}>
                <NotFound />
              </Suspense>
            }
          />
        </Routes>
      </Router>
      <Toaster position='top-center' />
    </>
  );
}

export default App;
