import PdfViewerComponent from './PdfViewrComponent';
import { SmartImage } from './SmartImage';

interface FilePreviewerProps {
  type: string;
  fileUrl: string;
}

export const FilePreviewer = ({ type, fileUrl }: FilePreviewerProps) => {
  const extension = type.split('.').pop()?.toLowerCase() || '';
  const isImage = ['jpg', 'jpeg', 'png', 'gif', 'webp'].includes(extension);
  const isPdf = extension === 'pdf';

  console.log('Extentions: ', extension);

  if (isImage) return <SmartImage src={fileUrl} alt='Image preview' />;
  if (isPdf) return <PdfViewerComponent fileUrl={fileUrl} />;

  return (
    <a className='bg-positive p-5 rounded' href={fileUrl} download>
      Tải về tài liệu
    </a>
  );
};
