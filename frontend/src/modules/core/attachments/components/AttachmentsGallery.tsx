import { useState } from 'react';
import AttachmentCard from './AttachmentCard';

import { useGetFlowAttachmentById } from '@/modules/core/attachments/hooks/useAttachmentHook';
import { FilePreviewer } from './FilePreview';
import ModalComponent from '@/components/shared/ModalComponent';
import Loader from '@/components/shared/Loader';
import ErrorPage from '@/components/shared/ErrorPage';

import { isImageFile, isVideoFile } from '../utils/fileUtils';

import MediaCard from './MediaCard';

interface AttachmentsGalleryProps {
  editable?: boolean;
  attachments: any[];
}

const AttachmentsGallery = ({ attachments }: AttachmentsGalleryProps) => {
  const [openDialog, setOpenDialog] = useState(false);
  const [selectedAttachment, setSelectedAttachment] = useState<any>(null);

  const mediaAttachments = attachments.filter(
    (file) => isImageFile(file) || isVideoFile(file),
  );
  const documentAttachments = attachments.filter(
    (file) => !isImageFile(file) && !isVideoFile(file),
  );

  const onAttachmentClick = (attachment: any) => {
    console.log('Attachments: ', attachment);

    setSelectedAttachment(attachment);
    setOpenDialog(true);
  };

  const {
    data: presignedUrlData,
    isLoading: isPresignedUrlLoading,
    error: presignedUrlError,
  } = useGetFlowAttachmentById({
    params: {
      id: selectedAttachment?.fileUrl ? '' : selectedAttachment?.id || '',
      expirationTime: 600,
    },
    options: {
      enabled: !!selectedAttachment && !selectedAttachment?.fileUrl,
    },
  });

  const selectedFileUrl = selectedAttachment?.fileUrl || presignedUrlData;

  return (
    <div>
      {mediaAttachments.length > 0 && (
        <section className='w-full rounded-2xl border border-slate-100 bg-white p-5 sm:p-6'>
          <h2 className='text-base font-semibold text-slate-800'>
            Hình Ảnh / Video Đính Kèm
          </h2>
          <div className='mt-4 grid grid-cols-1 gap-4 sm:grid-cols-2 xl:grid-cols-3'>
            {mediaAttachments.map((file) => (
              <MediaCard
                key={file.id || file._id || file.pathName}
                file={file}
                onSelectItem={onAttachmentClick}
              />
            ))}
          </div>
        </section>
      )}

      <section className='mt-4 w-full rounded-2xl border border-slate-100 bg-white p-5 sm:p-6'>
        <h2 className='text-base font-semibold text-slate-800'>
          Tài Liệu Đính Kèm
        </h2>
        <div className='mt-3'>
          <AttachmentCard
            attachments={documentAttachments}
            onClickAttachment={onAttachmentClick}
            emptyText='Chưa có tài liệu PDF hoặc tài liệu khác'
          />
        </div>
      </section>

      <ModalComponent open={openDialog} onClose={() => setOpenDialog(false)}>
        {isPresignedUrlLoading && !selectedAttachment?.fileUrl ? (
          <Loader text='Loading attachment...' />
        ) : (
          <FilePreviewer
            type={selectedAttachment?.fileType || selectedAttachment?.pathName}
            fileUrl={selectedFileUrl}
          />
        )}
        {presignedUrlError && <ErrorPage message={presignedUrlError.message} />}
      </ModalComponent>
    </div>
  );
};

export default AttachmentsGallery;
