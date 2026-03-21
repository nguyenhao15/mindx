import { Toaster } from 'react-hot-toast';
import './App.css';
import Layout from './components/Layout';

import { HomePage } from './pages/HomePage';
import {
  Navigate,
  Route,
  BrowserRouter as Router,
  Routes,
} from 'react-router-dom';
import React from 'react';
import ProcessDetail from './pages/ProcessDetail';
import CreateNewProcessPage from './pages/CreateNewProcessPage';
import { useGetUserInfo } from './hookQueries/useAuthentication';
import LoginPage from './pages/LoginPage';
import Loader from './components/shared/Loader';
import AdminAppPage from './pages/AdminAppPage';
import DocumentPage from './pages/DocumentPage';
import { handleLogout, useAuthStore } from './stores/AuthStore';
import UnAuthorizePage from './pages/UnAuthorizePage';
import DepartmentPage from './pages/DepartmentPage';
import ProcessFlowByDepartment from './pages/ProcessFlowByDepartment';
import ProcessItemPage from './pages/ProcessItemPage';
import MyDocuments from './pages/MyDocuments';
import UserProfile from './pages/UserProfile';
import LockAccountPage from './pages/LockAccountPage';

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

  const isLocked = user?.accountNonLocked;

  if (isAuthenticated && isEnabled && isLocked) {
    return <LockAccountPage />;
  }
  if (isAuthenticated && !isEnabled) {
    return (
      <div className='min-h-screen mx-auto my-auto justify-center items-center flex'>
        <h2 className='text-2xl font-semibold text-red-600'>
          Tài khoản của bạn chưa được kích hoạt. Vui lòng liên hệ quản trị viên.
        </h2>
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
