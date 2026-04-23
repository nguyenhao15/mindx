import { Outlet } from 'react-router-dom';
import AdminNavBar from './AdminNavBar';

const AdminLayouts = () => {
  return (
    <div className='flex h-screen bg-slate-50 font-sans overflow-hidden'>
      <AdminNavBar />
      <main className='flex-1 overflow-y-auto'>
        <Outlet />
      </main>
    </div>
  );
};

export default AdminLayouts;
