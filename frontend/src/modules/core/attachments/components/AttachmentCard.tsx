import { cn } from '@/lib/utils';
import { formatFileSize } from '@/utils/formatValue';
import {
  getFileNameFromPath,
  getFileVisual,
  parseFileSize,
} from '../utils/fileUtils';

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
