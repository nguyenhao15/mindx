import { useState } from 'react';
import AttachmentCard from './AttachmentCard';

import { useGetFlowAttachmentById } from '@/modules/core/attachments/hooks/useAttachmentHook';
import { FilePreviewer } from './FilePreview';
import ModalComponent from '@/components/shared/ModalComponent';
import Loader from '@/components/shared/Loader';
import ErrorPage from '@/components/shared/ErrorPage';
import { SmartImage } from '@/components/shared/SmartImage';
import { getFileNameFromPath } from '../utils/fileUtils';
import { ExternalLink } from 'lucide-react';
import { en } from 'zod/v4/locales';

interface AttachmentsGalleryProps {
  editable?: boolean;
  attachments: any[];
}

const IMAGE_EXTENSIONS = ['jpg', 'jpeg', 'png', 'gif', 'webp', 'bmp', 'svg'];
const VIDEO_EXTENSIONS = ['mp4', 'mov', 'avi', 'mkv', 'webm', 'm4v'];

const getExtensionFromPath = (pathName: string) => {
  if (!pathName) return '';
  const sanitized = pathName.split('?')[0];
  const parts = sanitized.split('.');
  return parts.length > 1 ? parts[parts.length - 1].toLowerCase() : '';
};

const isImageFile = (file: any) => {
  const normalizedType = String(file?.fileType || '').toLowerCase();
  if (normalizedType.startsWith('image/')) return true;
  const extension = getExtensionFromPath(
    String(file?.pathName || file?.fileName || ''),
  );
  return IMAGE_EXTENSIONS.includes(extension);
};

const isVideoFile = (file: any) => {
  const normalizedType = String(file?.fileType || '').toLowerCase();
  if (normalizedType.startsWith('video/')) return true;
  const extension = getExtensionFromPath(
    String(file?.pathName || file?.fileName || ''),
  );
  return VIDEO_EXTENSIONS.includes(extension);
};

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
            {mediaAttachments.map((file) => {
              const mediaUrl = file.fileUrl || '';
              const displayName = getFileNameFromPath(
                file.pathName || file.fileName || 'attachment',
              );
              const fileKey = file.id || file._id || file.pathName;

              return (
                <article key={fileKey} className='rounded-xl bg-slate-50 p-3'>
                  {isImageFile(file) ? (
                    <SmartImage
                      src={mediaUrl}
                      alt={displayName}
                      className='h-52 w-full object-cover'
                    />
                  ) : (
                    <video
                      className='h-52 w-full rounded-lg bg-slate-200 object-cover'
                      controls
                      preload='metadata'
                    >
                      <source src={mediaUrl} type={file.fileType} />
                      Trình duyệt không hỗ trợ video.
                    </video>
                  )}

                  <div className='mt-2 flex items-center justify-between gap-2'>
                    <p className='truncate text-xs font-medium text-slate-700'>
                      {displayName}
                    </p>
                    {mediaUrl && (
                      <a
                        href={mediaUrl}
                        target='_blank'
                        rel='noreferrer'
                        className='inline-flex items-center gap-1 text-xs font-semibold text-[#1d3557] hover:underline'
                      >
                        Mở link
                        <ExternalLink size={12} />
                      </a>
                    )}
                  </div>
                </article>
              );
            })}
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
