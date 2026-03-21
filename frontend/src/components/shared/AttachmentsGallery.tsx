import { useState } from 'react';
import AttachmentCard from './AttachmentCard';
import PdfViewerComponent from './PdfViewrComponent';

import Loader from './Loader';
import { useGetFlowAttachmentById } from '@/hookQueries/useAttachmentHook';
import ModalComponent from './ModalComponent';
import ErrorPage from './ErrorPage';
import { FilePreviewer } from './FilePreview';

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
        {presignedUrlError && (
          <ErrorPage
            message={
              presignedUrlError?.response?.data?.message ||
              'Failed to load attachment. Please try again later.'
            }
          />
        )}
      </ModalComponent>
    </div>
  );
};

export default AttachmentsGallery;
