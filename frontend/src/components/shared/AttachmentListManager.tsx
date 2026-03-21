import { useEffect, useMemo, useRef, useState } from 'react';
import { Button } from '@/components/ui/button';
import { formatFileSize } from '@/utils/formatValue';
import { FaSync } from 'react-icons/fa';
import { BsTrash } from 'react-icons/bs';

export interface AttachmentListItem {
  id: string;
  documentName: string;
  fileUrl?: string;
  fileSize?: number;
  file?: File;
  isDeleted?: boolean; // Flag to indicate if the attachment is marked for deletion
}

interface AttachmentListManagerProps {
  // Initial attachment list from parent.
  onAttachments: AttachmentListItem[];
  onAttachmentsChange?: (attachments: AttachmentListItem[]) => void;
  className?: string;
  isMultiFile?: boolean;
  supportedFileTypes?: string[];
}

const buildAttachmentFromFile = (file: File): AttachmentListItem => ({
  id:
    typeof crypto !== 'undefined' && crypto.randomUUID
      ? crypto.randomUUID()
      : `${Date.now()}-${file.name}`,
  documentName: file.name,
  fileSize: file.size,
  file,
});

const AttachmentListManager = ({
  onAttachments,
  onAttachmentsChange,
  className,
  isMultiFile = true,
  supportedFileTypes = [
    'pdf',
    'doc',
    'docx',
    'xls',
    'xlsx',
    'png',
    'jpg',
    'jpeg',
  ],
}: AttachmentListManagerProps) => {
  const [attachments, setAttachments] = useState<AttachmentListItem[]>(
    onAttachments ?? [],
  );
  const inputRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    setAttachments(onAttachments ?? []);
  }, [onAttachments]);

  useEffect(() => {
    onAttachmentsChange?.(attachments);
  }, [attachments, onAttachmentsChange]);

  const accept = useMemo(
    () => supportedFileTypes.map((type) => `.${type.toLowerCase()}`).join(','),
    [supportedFileTypes],
  );

  const openFileDialog = () => {
    inputRef.current?.click();
  };

  const handleAddAttachment = (event: React.ChangeEvent<HTMLInputElement>) => {
    const files = event.target.files;
    if (!files || files.length === 0) return;

    const mapped = Array.from(files).map(buildAttachmentFromFile);

    setAttachments((prev) => (isMultiFile ? [...prev, ...mapped] : mapped));

    // Allow selecting the same file again.
    event.target.value = '';
  };

  const handleDeleteAttachment = (id: string) => {
    if (attachments.find((item) => item.id === id)?.fileUrl) {
      // If the file is from server, we can mark it as deleted so that the server can handle the deletion in the next update.
      const isDeleted = attachments.find((item) => item.id === id)?.isDeleted;
      setAttachments((prev) =>
        prev.map((item) =>
          item.id === id ? { ...item, isDeleted: !isDeleted } : item,
        ),
      );
    } else {
      // If the file is newly added and not uploaded to server yet, we can directly remove it from the list.
      setAttachments((prev) => prev.filter((item) => item.id !== id));
    }
  };

  return (
    <div
      className={[
        'rounded-xl border border-slate-200 bg-white shadow-sm',
        className ?? '',
      ].join(' ')}
    >
      <div className='border-b border-slate-200 px-4 py-3'>
        <p className='text-sm font-semibold text-slate-900'>
          Danh sách tài liệu đính kèm
        </p>
      </div>

      <div className='px-4 py-3'>
        <div className='grid grid-cols-[1fr_auto] gap-3 border-b border-slate-200 pb-2 text-xs font-semibold uppercase tracking-wide text-slate-500'>
          <span>Tên tài liệu</span>
        </div>

        <div className='divide-y divide-slate-100'>
          {attachments.length === 0 ? (
            <div className='py-4 text-sm text-slate-500'>
              Chưa có tài liệu nào.
            </div>
          ) : (
            attachments.map((item) => (
              <div
                key={item.id}
                className='grid grid-cols-[1fr_auto] items-center gap-3 py-3'
              >
                <div className='min-w-0'>
                  <p
                    className={`truncate text-sm font-medium ${item.isDeleted ? 'text-red-600' : 'text-slate-900'}`}
                  >
                    {item.documentName}
                  </p>
                  {typeof item.fileSize === 'number' && (
                    <p className='text-xs text-slate-500'>
                      Kích thước: {formatFileSize(item.fileSize)}
                    </p>
                  )}
                </div>

                <Button
                  type='button'
                  variant='ghost'
                  size='icon-sm'
                  onClick={() => handleDeleteAttachment(item.id)}
                  aria-label={`Xóa tài liệu ${item.documentName}`}
                  title='Xóa tài liệu'
                  className='cursor-pointer'
                >
                  {item.isDeleted ? (
                    <FaSync className='h-4 w-4 text-slate-600' />
                  ) : (
                    <BsTrash className='h-4 w-4 text-red-600' />
                  )}
                </Button>
              </div>
            ))
          )}
        </div>

        <div className='pt-4'>
          <input
            ref={inputRef}
            type='file'
            className='sr-only'
            multiple={isMultiFile}
            accept={accept}
            onChange={handleAddAttachment}
          />
          <Button
            type='button'
            className='cursor-pointer'
            variant='positive'
            onClick={openFileDialog}
          >
            Thêm tài liệu
          </Button>
        </div>
      </div>
    </div>
  );
};

export default AttachmentListManager;
