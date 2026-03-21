import React, { useState } from 'react';
import Zoom from 'react-medium-image-zoom';
import 'react-medium-image-zoom/dist/styles.css';

interface SmartImageProps extends React.ImgHTMLAttributes<HTMLImageElement> {
  src: string;
  alt: string;
}

export const SmartImage = ({ src, alt, ...props }: SmartImageProps) => {
  const [status, setStatus] = useState('loading'); // loading, success, error
  const PLACEHOLDER_IMAGE = 'https://via.placeholder.com/150?text=No+Image';

  return (
    <div
      className='image-wrapper bg-white h-fit w-fit max-w-screen p-2 rounded-lg border border-slate-200 flex items-center justify-center'
      style={{ position: 'relative', overflow: 'hidden' }}
    >
      {status === 'loading' && (
        <div
          className='skeleton-loader'
          style={{ backgroundColor: '#eee', height: '100%' }}
        >
          Đang tải...
        </div>
      )}

      <Zoom>
        <img
          alt={alt}
          src={src || PLACEHOLDER_IMAGE}
          style={{
            width: '100%',
            borderRadius: '8px',
            cursor: 'pointer',
          }}
          // Kết hợp AWS S3 URL vào đây

          {...props}
          onLoad={() => setStatus('success')}
          onError={() => setStatus('error')}
        />
      </Zoom>
    </div>
  );
};
