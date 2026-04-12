import { useState } from 'react';
import AttachmentCard from './AttachmentCard';

import { useGetFlowAttachmentById } from '@/modules/core/attachments/hooks/useAttachmentHook';
import { FilePreviewer } from './FilePreview';
import ModalComponent from '@/components/shared/ModalComponent';
import Loader from '@/components/shared/Loader';
import ErrorPage from '@/components/shared/ErrorPage';

const AttachmentsGallery = ({
  attachments,
}: {
  editable: boolean;
  attachments: any[];
}) => {
  const [openDialog, setOpenDialog] = useState(false);
  const [selectedAttachment, setSelectedAttachment] = useState<any>(null);

  const onAttachmentClick = (attachment: any) => {
    setSelectedAttachment(attachment);
    setOpenDialog(true);
  };

  const {
    data: presignedUrlData,
    isLoading: isPresignedUrlLoading,
    error: presignedUrlError,
  } = useGetFlowAttachmentById({
    id: selectedAttachment?.id || '',
    expirationTime: 600,
  });

  return (
    <div>
      <div className='flex flex-row flex-wrap gap-2 p-2 rounded'>
        {attachments.map((file) => (
          <AttachmentCard
            key={file.id}
            attachment={file}
            onClickAttachment={onAttachmentClick}
          />
        ))}
      </div>
      <ModalComponent open={openDialog} onClose={() => setOpenDialog(false)}>
        {isPresignedUrlLoading ? (
          <Loader text='Loading attachment...' />
        ) : (
          <FilePreviewer
            type={selectedAttachment?.pathName}
            fileUrl={presignedUrlData}
          />
        )}
        {presignedUrlError && <ErrorPage message={presignedUrlError.message} />}
      </ModalComponent>
    </div>
  );
};

export default AttachmentsGallery;
