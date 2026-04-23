import { Route, Routes } from 'react-router-dom';
import AdminLayouts from '../shared/AdminLayouts';
import AdminHomePage from '../pages/AdminHomePage';
import NotFoundComponent from '@/pages/NotFoundComponent';
import { UserTableComponent } from '../pages/UserTableComponent';
import WorkflowPage from '../pages/WorkflowPage';
import HumanResourcePage from '../pages/HumanResourcePage';

const AdminRoute = () => {
  return (
    <Routes>
      <Route element={<AdminLayouts />}>
        <Route index element={<AdminHomePage />} />
        <Route path='/users/*' element={<UserTableComponent />} />
        <Route path='/workflows/*' element={<WorkflowPage />} />
        <Route path='/human-resource/*' element={<HumanResourcePage />} />
      </Route>
      <Route path='*' element={<NotFoundComponent homePath='/admin' />} />
    </Routes>
  );
};

export default AdminRoute;
