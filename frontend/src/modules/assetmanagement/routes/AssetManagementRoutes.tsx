import { Route, Routes } from 'react-router-dom';
import AssetLayout from '../shared/AssetLayout';
import AssetHomePage from '../shared/AssetHomePage';
import NotFoundComponent from '@/pages/NotFoundComponent';
import MaintanceRoutes from '../maintances/routes/MaintanceRoutes';

const AssetManagementRoutes = () => {
  return (
    <Routes>
      <Route element={<AssetLayout />}>
        <Route index element={<AssetHomePage />} />
        <Route path='maintance/*' element={<MaintanceRoutes />} />
        <Route path='*' element={<NotFoundComponent />} />
      </Route>
    </Routes>
  );
};

export default AssetManagementRoutes;
