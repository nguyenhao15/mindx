import { Toaster } from 'react-hot-toast';
import './App.css';
import Layout from './modules/documentations/document/components/shared/Layout';
import {
  Navigate,
  Route,
  BrowserRouter as Router,
  Routes,
} from 'react-router-dom';
import React from 'react';

import { useGetUserInfo } from './modules/core/auth/hooks/useAuthentication';
import LoginPage from './modules/core/auth/pages/LoginPage';
import Loader from './components/shared/Loader';
import AdminAppPage from './modules/core/admin/pages/AdminAppPage';
import {
  handleLogout,
  useAuthStore,
} from './modules/core/auth/store/AuthStore';
import UnAuthorizePage from './modules/core/auth/pages/UnAuthorizePage';
import UserProfile from './modules/core/auth/pages/UserProfile';
import LockAccountPage from './modules/core/auth/pages/LockAccountPage';
import ActivatePassword from './modules/core/auth/components/ActivatePassword';
import DocumentRoute from './modules/documentations/document/routes/DocumentRoute';
import AdminRoute from './modules/core/admin/routes/AdminRoute';
import HomePage from './pages/HomePage';

function App() {
  const { isLoading, isError } = useGetUserInfo();
  const user = useAuthStore((state) => state.user);
  const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';

  if (isLoading && !user && isLoggedIn) {
    return (
      <div className='min-h-screen mx-auto my-auto justify-center items-center flex'>
        <Loader text={'Loading data...'} />
      </div>
    );
  }

  if (!isLoggedIn) {
    handleLogout();
  }
  // Nếu có lỗi khi lấy thông tin user, xem như chưa đăng nhập
  const isAuthenticated = !isError && !!user;
  const isAdmin = user?.systemRole === 'ADMIN';
  const isEnabled = user?.enabled;

  const isLocked = !user?.accountNonLocked;

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

  const NotFound = React.lazy(() => import('./pages/NotFoundComponent'));

  return (
    <React.Fragment>
      <Router>
        <Routes>
          <Route
            path='/login'
            element={isAuthenticated ? <Navigate to='/' /> : <LoginPage />}
          />
          {isAdmin && <Route path='/admin/*' element={<AdminRoute />} />}
          <Route path='/' element={<HomePage />} />

          <Route path='/docs/*' element={<DocumentRoute />} />
          <Route path='/unauthorized' element={<UnAuthorizePage />} />
          <Route path='/profile' element={<UserProfile user={user!} />} />

          <Route
            path='*'
            element={
              <React.Suspense fallback={<Loader text={'Loading...'} />}>
                <NotFound />
              </React.Suspense>
            }
          />
        </Routes>
      </Router>
      <Toaster position='top-center' />
    </React.Fragment>
  );
}

export default App;
