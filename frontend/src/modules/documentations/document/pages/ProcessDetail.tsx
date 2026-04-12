import ErrorPage from '@/components/shared/ErrorPage';
import Loader from '@/components/shared/Loader';
import { useGetProcessFlowWithFullInfo } from '@/modules/documentations/document/hooks/useProcessFlowHooks';
import { useState } from 'react';
import { useLocation } from 'react-router-dom';
import ProcessFlowView from '../components/processFlowDetail/ProcessFlowView';
import ProcessFlowEdit from '../components/processFlowDetail/ProcessFlowEdit';

const ProcessDetail = ({ viewMode }: { viewMode: 'admin' | 'user' }) => {
  const location = useLocation();
  const [editMode, setEditMode] = useState(false);

  const { processItem } = location.state || {};

  const { data, isLoading, refetch, isRefetching, error } =
    useGetProcessFlowWithFullInfo(processItem?.id || '');

  const loadingData = isLoading || isRefetching;

  if (loadingData) {
    return <Loader text='Loading data...' />;
  }

  if (error) {
    return <ErrorPage message={(error as any)?.response?.data?.message} />;
  }

  return (
    <div className='p-2 flex flex-col h-full gap-2'>
      {!editMode ? (
        <ProcessFlowView
          data={data}
          isLoading={loadingData}
          setEdit={setEditMode}
          viewMode={viewMode}
        />
      ) : (
        <ProcessFlowEdit
          data={data}
          id={processItem?.processFlowDto?.id || ''}
          refetch={refetch}
          setEditMode={setEditMode}
        />
      )}
    </div>
  );
};

export default ProcessDetail;
