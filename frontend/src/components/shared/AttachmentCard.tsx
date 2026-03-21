import { cn } from '@/lib/utils';
import { formatFileSize } from '@/utils/formatValue';
import {
  FaFile,
  FaFileArchive,
  FaFileExcel,
  FaFileImage,
  FaFilePdf,
  FaFileWord,
} from 'react-icons/fa';

type MongoNumberLong = {
  $numberLong: string;
};

export interface AttachmentItem {
  fileName: string;
  fileType: string;
  fileSize: number | string | MongoNumberLong;
  processFlowId: string;
  fileUrl?: string;
  _id: string;
}

interface AttachmentCardProps {
  attachment?: AttachmentItem;
  attachments?: AttachmentItem[];
  className?: string;
  emptyText?: string;
  onClickAttachment?: (attachment: AttachmentItem) => void;
}

const getFileNameFromPath = (path: string) => {
  const pathSections = path.split('/').filter(Boolean);
  if (pathSections.length === 0) {
    return path;
  }
  return pathSections[pathSections.length - 1];
};

const parseFileSize = (size: AttachmentItem['fileSize']) => {
  if (typeof size === 'number' || typeof size === 'string') {
    return Number(size);
  }
  return Number(size.$numberLong ?? 0);
};

const getFileVisual = (fileType: string) => {
  const normalizedType = fileType.toLowerCase();

  if (normalizedType === 'application/pdf') {
    return {
      icon: FaFilePdf,
      iconClassName: 'text-red-600',
      badgeClassName: 'bg-red-50 text-red-700 border border-red-100',
      label: 'PDF',
    };
  }

  if (normalizedType.startsWith('image/')) {
    return {
      icon: FaFileImage,
      iconClassName: 'text-emerald-600',
      badgeClassName:
        'bg-emerald-50 text-emerald-700 border border-emerald-100',
      label: 'IMAGE',
    };
  }

  if (
    normalizedType.includes('word') ||
    normalizedType.includes('officedocument.wordprocessingml')
  ) {
    return {
      icon: FaFileWord,
      iconClassName: 'text-blue-600',
      badgeClassName: 'bg-blue-50 text-blue-700 border border-blue-100',
      label: 'DOC',
    };
  }

  if (
    normalizedType.includes('excel') ||
    normalizedType.includes('spreadsheetml') ||
    normalizedType.includes('csv')
  ) {
    return {
      icon: FaFileExcel,
      iconClassName: 'text-green-700',
      badgeClassName: 'bg-green-50 text-green-800 border border-green-100',
      label: 'SHEET',
    };
  }

  if (
    normalizedType.includes('zip') ||
    normalizedType.includes('rar') ||
    normalizedType.includes('tar')
  ) {
    return {
      icon: FaFileArchive,
      iconClassName: 'text-amber-700',
      badgeClassName: 'bg-amber-50 text-amber-700 border border-amber-100',
      label: 'ARCHIVE',
    };
  }

  return {
    icon: FaFile,
    iconClassName: 'text-slate-600',
    badgeClassName: 'bg-slate-100 text-slate-700 border border-slate-200',
    label: 'FILE',
  };
};

const AttachmentCard = ({
  attachment,
  attachments,

  className,
  emptyText = 'No attachments',
  onClickAttachment,
}: AttachmentCardProps) => {
  const attachmentList = attachments ?? (attachment ? [attachment] : []);

  if (attachmentList.length === 0) {
    return (
      <div
        className={cn(
          'rounded-xl border border-dashed border-slate-300 bg-slate-50 p-4 text-sm text-slate-500',
          className,
        )}
      >
        {emptyText}
      </div>
    );
  }

  return (
    <div className={cn('space-y-3', className)}>
      {attachmentList.map((file, index) => {
        const visual = getFileVisual(file.fileType);
        const IconComponent = visual.icon;
        const displayName = getFileNameFromPath(file.fileName);
        const sizeInBytes = parseFileSize(file.fileSize);

        return (
          <div
            key={`${file.fileName}-${index}`}
            className={cn(
              'rounded-xl border border-slate-200 bg-white p-4 shadow-sm transition-colors',
              onClickAttachment
                ? 'cursor-pointer hover:border-slate-300 hover:bg-slate-50'
                : '',
            )}
            onClick={() => onClickAttachment?.(file)}
            role={onClickAttachment ? 'button' : undefined}
            tabIndex={onClickAttachment ? 0 : -1}
            onKeyDown={(event) => {
              if (!onClickAttachment) return;
              if (event.key === 'Enter' || event.key === ' ') {
                event.preventDefault();
                onClickAttachment(file);
              }
            }}
          >
            <div className='flex items-start gap-3'>
              <div className='flex h-11 w-11 shrink-0 items-center justify-center rounded-lg bg-slate-100'>
                <IconComponent
                  className={cn('h-6 w-6', visual.iconClassName)}
                />
              </div>

              <div className='min-w-0 flex-1'>
                <div className='mb-2 flex items-start justify-between gap-2'>
                  <p className='truncate text-sm font-semibold text-slate-900'>
                    {displayName}
                  </p>
                  <span
                    className={cn(
                      'inline-flex shrink-0 rounded-full px-2 py-0.5 text-[10px] font-semibold',
                      visual.badgeClassName,
                    )}
                  >
                    {visual.label}
                  </span>
                </div>

                <div className='space-y-1 text-xs text-slate-500'>
                  {/* <p className='truncate'>Folder: {folderPath}</p> */}
                  <p>
                    Size: {formatFileSize(sizeInBytes)} (
                    {sizeInBytes.toLocaleString('en-US')} B)
                  </p>
                  <p className='truncate'>Type: {file.fileType}</p>
                  {/* <p className='truncate'>
                    Process Flow ID: {file.processFlowId}
                  </p> */}
                </div>
              </div>
            </div>
          </div>
        );
      })}
    </div>
  );
};

export default AttachmentCard;
