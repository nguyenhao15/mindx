import { getFileNameFromPath, isImageFile } from '../utils/fileUtils';
import { SmartImage } from '@/components/shared/SmartImage';
import { ExternalLink } from 'lucide-react';
import AttachmentCard from './AttachmentCard';

const MediaCard = ({
  file,
  onSelectItem,
}: {
  file: any;
  onSelectItem: (file: any) => void;
}) => {
  const mediaUrl = file.fileUrl || '';
  const displayName = getFileNameFromPath(
    file.pathName || file.fileName || 'attachment',
  );
  const fileKey = file.id || file._id || file.pathName;

  const isValidUrl = (url: string) => {
    try {
      new URL(url);
      return true;
    } catch {
      return false;
    }
  };

  const isPreviewable = isValidUrl(file.fileUrl);

  if (!isPreviewable) {
    return (
      <AttachmentCard attachment={file} onClickAttachment={onSelectItem} />
    );
  }

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
};

export default MediaCard;
