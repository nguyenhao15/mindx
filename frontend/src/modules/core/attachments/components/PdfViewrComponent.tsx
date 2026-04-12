import React, { useState } from 'react';
import { Document, Page, pdfjs } from 'react-pdf';
import Loader from '../../../../components/shared/Loader';

// Link Worker từ CDN để giảm kích thước bundle của App
pdfjs.GlobalWorkerOptions.workerSrc = `//unpkg.com/pdfjs-dist@${pdfjs.version}/build/pdf.worker.min.mjs`;

const PdfViewerComponent = ({ fileUrl }: { fileUrl: string }) => {
  const [numPages, setNumPages] = useState<number | null>(null);

  const onDocumentLoadSuccess = ({ numPages }: { numPages: number }) => {
    setNumPages(numPages);
  };

  const onDocumentLoadError = (error: any) => {
    console.error('Lỗi tải PDF:', error);
    // Ghi log vào hệ thống hoặc thông báo cho User
  };
  const onLoading = () => {
    // Có thể hiển thị một spinner hoặc placeholder trong khi tải PDF
    return (
      <div className='bg-white p-5 rounded'>
        <Loader text={'Đang đọc file...'} />
      </div>
    );
  };

  return (
    <div
      className='pdf-container rounded border border-gray-300 bg-white p-4 shadow'
      style={{
        display: 'flex',
        overflow: 'scroll',
        flexDirection: 'column',
        alignItems: 'center',
      }}
    >
      <Document
        file={fileUrl}
        onLoadSuccess={onDocumentLoadSuccess}
        onLoadError={onDocumentLoadError}
        loading={onLoading()}
      >
        {/* Render tất cả các trang - Phù hợp cho báo cáo B2B */}
        {Array.from(new Array(numPages), (el, index) => (
          <Page
            key={`page_${index + 1}`}
            pageNumber={index + 1}
            renderTextLayer={false} // Tắt để tăng hiệu năng nếu không cần copy text
            renderAnnotationLayer={false}
            width={800} // Fix chiều rộng để đảm bảo UI đồng nhất
          />
        ))}
      </Document>
    </div>
  );
};

export default PdfViewerComponent;
