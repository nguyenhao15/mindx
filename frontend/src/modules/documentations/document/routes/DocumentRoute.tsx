import { Route, Routes } from 'react-router-dom';
import ProcessDetail from '../pages/ProcessDetail';
import ProcessItemPage from '../pages/ProcessItemPage';
import CreateNewProcessPage from '../pages/CreateNewProcessPage';
import MyDocuments from '../pages/MyDocuments';
import ProcessFlowByDepartment from '../pages/ProcessFlowByDepartment';
import DepartmentPage from '@/modules/core/departments/pages/DepartmentPage';
import DocumentPage from '../pages/DocumentPage';
import { HomePage } from '@/modules/documentations/document/pages/HomePage';
import Layout from '../components/shared/Layout';

const DocumentRoute = () => {
  return (
    <Routes>
      <Route element={<Layout />}>
        <Route index element={<HomePage />} />
        <Route path='documents' element={<DocumentPage />} />
        <Route path='departments' element={<DepartmentPage />} />
        <Route path='departments/:id' element={<ProcessFlowByDepartment />} />
        <Route path='my-documents' element={<MyDocuments />} />
        <Route
          path='/tag-flow/:id'
          element={<ProcessDetail viewMode='user' />}
        />
        <Route path='create-document' element={<CreateNewProcessPage />} />
        <Route path='processes' element={<ProcessItemPage />} />
      </Route>
    </Routes>
  );
};

export default DocumentRoute;
