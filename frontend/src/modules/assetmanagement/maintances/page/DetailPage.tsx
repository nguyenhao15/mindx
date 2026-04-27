import DetailMasterPage from '../components/Details/DetailMasterPage';
import UpdateItemComponent from '../components/Updates/UpdateItemComponent';
import { useParams } from 'react-router-dom';
import { useGetMaintanceDetailById } from '../hooks/useMaintenanceHooks';
import { useState } from 'react';
import ModalComponent from '@/components/shared/ModalComponent';
import FinishedComponent from '../components/Details/FinishedComponent';

const DetailPage = () => {
  const { id } = useParams<{ id: string }>();
  const [updateType, setUpdateType] = useState<
    'update' | 'rework' | 'finished'
  >('update');
  const { data, isLoading, error } = useGetMaintanceDetailById(Number(id));
  const [openModal, setOpenModal] = useState(false);

  const handleUpdateClick = (type: 'update' | 'rework' | 'finished') => {
    setUpdateType(type);
    setOpenModal(true);
  };

  const handleCloseModal = () => {
    setOpenModal(false);
  };

  return (
    <div className='p-2'>
      <DetailMasterPage
        onUpdateOpen={handleUpdateClick}
        item={data}
        isLoading={isLoading}
        error={error}
      />

      <ModalComponent open={openModal} onClose={handleCloseModal}>
        {updateType === 'finished' ? (
          <FinishedComponent
            id={data?.maintenanceDetailsInfo?.id || 0}
            afterUpdate={handleCloseModal}
          />
        ) : (
          <UpdateItemComponent
            id={data?.maintenanceDetailsInfo?.id || 0}
            maintenancesStatus={
              data?.maintenanceDetailsInfo?.maintenancesStatus || ''
            }
            afterUpdate={handleCloseModal}
            reWork={data?.maintenanceDetailsInfo?.reWork || false}
            totalCost={data?.maintenanceDetailsInfo?.totalCost || 0}
          />
        )}
      </ModalComponent>
    </div>
  );
};

export default DetailPage;
