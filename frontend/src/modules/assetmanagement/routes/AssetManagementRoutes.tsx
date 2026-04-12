import React from 'react';
import { Route, Routes } from 'react-router-dom';
import MaintanceLayout from '../maintances/components/shared/MaintanceLayout';
import HomePage from '../maintances/page/HomePage';

const AssetManagementRoutes = () => {
  return (
    <Routes>
      <Route element={<MaintanceLayout />}>
        <Route path='' element={<HomePage />} />
      </Route>
    </Routes>
  );
};

export default AssetManagementRoutes;
