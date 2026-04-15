import { Route, Routes } from 'react-router-dom';
import MaintanceLayout from '../MaintanceLayout';
import NotFoundComponent from '@/pages/NotFoundComponent';
import CreatePage from '../page/CreatePage';

import ProcessingRequest from '../page/ProcessingRequest';
import HomePage from '../page/HomePage';
import DetailPage from '../page/DetailPage';

const MaintanceRoutes = () => {
  return (
    <Routes>
      <Route element={<MaintanceLayout />}>
        {/* Maintance module */}

        <Route index element={<HomePage />} />
        <Route path='create' element={<CreatePage />} />
        <Route path='detail/:id' element={<DetailPage />} />
        <Route path='list' element={<ProcessingRequest />} />
        <Route path='*' element={<NotFoundComponent />} />
      </Route>

      {/* Catch-all for unmatched asset paths */}
      <Route path='*' element={<NotFoundComponent />} />
    </Routes>
  );
};

export default MaintanceRoutes;
