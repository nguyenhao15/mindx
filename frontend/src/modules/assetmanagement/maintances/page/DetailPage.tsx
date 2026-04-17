import DetailMasterPage from '../components/Details/DetailMasterPage';
import UpdateItemComponent from '../components/Updates/UpdateItemComponent';
import { useParams } from 'react-router-dom';
import { useGetMaintanceDetailById } from '../hooks/useMaintenanceHooks';
import { useState } from 'react';
import ModalComponent from '@/components/shared/ModalComponent';

const DetailPage = () => {
  const { id } = useParams<{ id: string }>();
  const { data, isLoading, error } = useGetMaintanceDetailById(Number(id));
  const [openModal, setOpenModal] = useState(false);

  return (
    <div className='p-2'>
      <DetailMasterPage
        onUpdateOpen={() => setOpenModal(true)}
        item={data}
        isLoading={isLoading}
        error={error}
      />

      <ModalComponent open={openModal} onClose={() => setOpenModal(false)}>
        <UpdateItemComponent
          id={data?.maintenanceDetailsInfo?.id || 0}
          maintenancesStatus={
            data?.maintenanceDetailsInfo?.maintenancesStatus || ''
          }
          reWork={data?.maintenanceDetailsInfo?.reWork || false}
          totalCost={data?.maintenanceDetailsInfo?.totalCost || 0}
        />
      </ModalComponent>
    </div>
  );
};

export default DetailPage;
