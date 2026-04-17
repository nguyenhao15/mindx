import React, { useState } from 'react';
import DetailMasterPage from '../components/Details/DetailMasterPage';
import ModalComponent from '@/components/shared/ModalComponent';
import UpdateItemComponent from '../components/Updates/UpdateItemComponent';
import { useParams } from 'react-router-dom';

const DetailPage = () => {
  const { id } = useParams<{ id: string }>();
  const [selectedItem, setSelectedItem] = useState<any>(null);
  const [openUpdateModal, setOpenUpdateModal] = useState(false);

  const handleSelectItemUpdate = (item: any) => {
    // Handle the logic when an item is selected for update
    console.log('Selected item for update:', item);
    setSelectedItem(item);
    setOpenUpdateModal(true);
  };

  return (
    <div className='p-2'>
      <DetailMasterPage
        id={Number(id)}
        onSelectItemUpdate={handleSelectItemUpdate}
      />
      <ModalComponent
        open={openUpdateModal}
        onClose={() => setOpenUpdateModal(false)}
      >
        <UpdateItemComponent
          id={selectedItem?.id || 0}
          maintenancesStatus={selectedItem?.maintenancesStatus || ''}
          reWork={selectedItem?.reWork || false}
          totalCost={selectedItem?.totalCost || 0}
        />
      </ModalComponent>
    </div>
  );
};

export default DetailPage;
