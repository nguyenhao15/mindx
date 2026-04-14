import { Route, Routes } from 'react-router-dom';
import AssetLayout from '../shared/AssetLayout';
import MaintanceLayout from '../maintances/MaintanceLayout';
import HomePage from '../maintances/page/HomePage';
import CreatePage from '../maintances/page/CreatePage';
import AssetHomePage from '../shared/AssetHomePage';
import NotFoundComponent from '@/pages/NotFoundComponent';

const AssetManagementRoutes = () => {
  return (
    <Routes>
      <Route element={<AssetLayout />}>
        <Route index element={<AssetHomePage />} />

        {/* Maintance module */}
        <Route path='maintance' element={<MaintanceLayout />}>
          <Route index element={<HomePage />} />
          <Route path='create' element={<CreatePage />} />
          <Route path='*' element={<NotFoundComponent />} />
        </Route>

        {/* Catch-all for unmatched asset paths */}
        <Route path='*' element={<NotFoundComponent />} />
      </Route>
    </Routes>
  );
};

export default AssetManagementRoutes;
