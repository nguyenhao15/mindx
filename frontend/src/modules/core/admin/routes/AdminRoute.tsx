import React from 'react';
import { Route, Routes } from 'react-router-dom';
import AdminAppPage from '../pages/AdminAppPage';
import { useAuthStore } from '../../auth/store/AuthStore';

const AdminRoute = () => {
  const user = useAuthStore((state) => state.user);
  
  return (
    <Routes>
      <Route path='/' element={<AdminAppPage user={user!} />} />
    </Routes>
  );
};

export default AdminRoute;
