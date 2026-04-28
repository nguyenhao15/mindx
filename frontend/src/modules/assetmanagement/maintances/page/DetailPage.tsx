import DetailMasterPage from '../components/Details/DetailMasterPage';
import UpdateItemComponent from '../components/Updates/UpdateItemComponent';
import { useParams } from 'react-router-dom';
import { useGetMaintanceDetailById } from '../hooks/useMaintenanceHooks';
import { useState } from 'react';
import ModalComponent from '@/components/shared/ModalComponent';
import FinishedComponent from '../components/Details/FinishedComponent';
import type { MaintenanceStatus } from '../schema/maintenaceSchema';
import ProposalFormsInput from '../components/Proposal/ProposalFormsInput';

const DetailPage = () => {
  const { id } = useParams();
  const [updateType, setUpdateType] = useState<MaintenanceStatus>('APPROVED');
  const { data, isLoading, error } = useGetMaintanceDetailById(Number(id));
  const [openModal, setOpenModal] = useState(false);

  const handleUpdateClick = (type: MaintenanceStatus) => {
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
        {updateType === 'FINISHED' && (
          <FinishedComponent
            id={Number(id) || 0}
            afterUpdate={handleCloseModal}
          />
        )}
        {updateType === 'PROCESSING' && (
          <ProposalFormsInput id={Number(id) || 0} />
        )}
        {updateType === 'APPROVED' && (
          <UpdateItemComponent
            id={Number(id) || 0}
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
