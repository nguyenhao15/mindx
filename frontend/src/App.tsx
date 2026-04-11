import { Toaster } from 'react-hot-toast';
import './App.css';
import Layout from './components/Layout';

import { HomePage } from './modules/documentation/pages/HomePage';
import {
  Navigate,
  Route,
  BrowserRouter as Router,
  Routes,
} from 'react-router-dom';
import React from 'react';
import ProcessDetail from './modules/documentation/pages/ProcessDetail';
import CreateNewProcessPage from './modules/documentation/pages/CreateNewProcessPage';
import { useGetUserInfo } from './modules/documentation/hookQueries/useAuthentication';
import LoginPage from './modules/documentation/pages/LoginPage';
import Loader from './components/shared/Loader';
import AdminAppPage from './modules/documentation/pages/AdminAppPage';
import DocumentPage from './modules/documentation/pages/DocumentPage';
import { handleLogout, useAuthStore } from './stores/AuthStore';
import UnAuthorizePage from './modules/documentation/pages/UnAuthorizePage';
import DepartmentPage from './modules/documentation/pages/DepartmentPage';
import ProcessFlowByDepartment from './modules/documentation/pages/ProcessFlowByDepartment';
import ProcessItemPage from './modules/documentation/pages/ProcessItemPage';
import MyDocuments from './modules/documentation/pages/MyDocuments';
import UserProfile from './modules/documentation/pages/UserProfile';
import LockAccountPage from './modules/documentation/pages/LockAccountPage';
import ActivatePassword from './components/profile/ActivatePassword';

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

  const NotFound = React.lazy(
    () => import('./modules/documentation/pages/NotFoundComponent'),
  );

  return (
    <React.Fragment>
      <Router>
        <Routes>
          <Route
            path='/login'
            element={isAuthenticated ? <Navigate to='/' /> : <LoginPage />}
          />
          <Route
            path='/admin'
            element={
              isAdmin ? (
                <AdminAppPage user={user!} />
              ) : (
                <Navigate to='/unauthorized' />
              )
            }
          />

          <Route
            path='/'
            element={isAuthenticated ? <Layout /> : <Navigate to='/login' />}
          >
            <Route path='/' element={<HomePage />} />
            <Route path='/documents' element={<DocumentPage />} />
            <Route path='/departments' element={<DepartmentPage />} />
            <Route
              path='/departments/:id'
              element={<ProcessFlowByDepartment />}
            />
            <Route path='/my-documents' element={<MyDocuments />} />
            <Route
              path='/documents/tag-flow/:id'
              element={<ProcessDetail viewMode='user' />}
            />
            <Route path='/create-document' element={<CreateNewProcessPage />} />
            <Route path='/unauthorized' element={<UnAuthorizePage />} />
            <Route path='/processes' element={<ProcessItemPage />} />
            <Route path='/profile' element={<UserProfile user={user!} />} />
          </Route>
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
