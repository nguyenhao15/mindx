import PdfViewerComponent from './PdfViewrComponent';
import { SmartImage } from '../../../../components/shared/SmartImage';

interface FilePreviewerProps {
  type: string;
  fileUrl: string;
}

export const FilePreviewer = ({ type, fileUrl }: FilePreviewerProps) => {
  const normalizedType = String(type || '').toLowerCase();
  const extension = normalizedType.split('.').pop()?.toLowerCase() || '';
  const isImage =
    normalizedType.startsWith('image/') ||
    ['jpg', 'jpeg', 'png', 'gif', 'webp', 'bmp', 'svg'].includes(extension);
  const isVideo =
    normalizedType.startsWith('video/') ||
    ['mp4', 'mov', 'avi', 'mkv', 'webm', 'm4v'].includes(extension);
  const isPdf = normalizedType === 'application/pdf' || extension === 'pdf';

  if (!fileUrl) {
    return <p className='text-sm text-slate-500'>Không có URL để xem tệp.</p>;
  }

  if (isImage) return <SmartImage src={fileUrl} alt='Image preview' />;
  if (isVideo)
    return (
      <video className='w-full rounded-lg' controls preload='metadata'>
        <source
          src={fileUrl}
          type={
            normalizedType.startsWith('video/') ? normalizedType : undefined
          }
        />
        Trình duyệt không hỗ trợ video.
      </video>
    );
  if (isPdf) return <PdfViewerComponent fileUrl={fileUrl} />;

  return (
    <a className='bg-positive p-5 rounded' href={fileUrl} download>
      Tải về tài liệu
    </a>
  );
};
