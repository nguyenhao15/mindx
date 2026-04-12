import { Route, Router, Routes } from 'react-router-dom';
import LoginPage from '../pages/LoginPage';
import LockAccountPage from '../pages/LockAccountPage';
import UnAuthorizePage from '../pages/UnAuthorizePage';
import UserProfile from '../pages/UserProfile';

const routes = ({ user }: { user: any }) => {
  return (
    <Routes>
      <Route path='/login' element={<LoginPage />} />
      <Route path='/locked' element={<LockAccountPage />} />
      <Route path='/un-authorize' element={<UnAuthorizePage />} />
      <Route path='/info' element={<UserProfile user={user} />} />
    </Routes>
  );
};

export default routes;
