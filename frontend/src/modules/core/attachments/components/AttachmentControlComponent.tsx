import { formatFileSize } from '@/utils/formatValue';
import React, { useEffect, useRef, useState } from 'react';
import toast from 'react-hot-toast';
import { BsFileText, BsUpload, BsX } from 'react-icons/bs';
import { FaImage } from 'react-icons/fa';
import { Input } from '@/components/ui/input';

interface AttachementControlProps {
  attachedFile?: File[];
  onFileAttach: (files: File[]) => void;
  title: string;
  isMultiFile?: boolean;
  supportedFileTypes?: string[];
  maxFileSize?: number;
}

const AttachmentControl = ({
  attachedFile = [],
  onFileAttach,
  title,
  isMultiFile = true,
  supportedFileTypes = ['PDF', 'DOCX', 'PNG', 'JPG', 'JPEG'],
  maxFileSize = 10 * 1024 * 1024, // 10MB
}: AttachementControlProps) => {
  const [isDragging, setIsDragging] = useState(false);
  const [localAttachedFile, setLocalAttachedFile] = useState(attachedFile);
  const [isFocus, setIsFocus] = useState(false);
  const [filePreviews, setFilePreviews] = useState<{
    [key: string]: string | ArrayBuffer | null;
  }>({});

  const dragCounter = useRef(0);

  // Dọn dẹp bộ nhớ khi component unmount
  useEffect(() => {
    return () => {
      Object.values(filePreviews).forEach(
        (url: string | ArrayBuffer | null) => {
          if (typeof url === 'string') {
            URL.revokeObjectURL(url);
          }
        },
      );
    };
  }, [filePreviews]);

  const handleDragOver = (e: React.DragEvent<HTMLDivElement>) => {
    e.preventDefault();
    setIsDragging(true);
    e.stopPropagation();
  };

  const handleDragLeave = (e: React.DragEvent<HTMLDivElement>) => {
    e.preventDefault();
    e.stopPropagation();
    dragCounter.current -= 1;
    if (dragCounter.current === 0) {
      setIsDragging(false);
    }
  };

  const handleDragEnter = (e: React.DragEvent<HTMLDivElement>) => {
    e.preventDefault();
    e.stopPropagation();
    dragCounter.current += 1;
    if (e.dataTransfer.items && e.dataTransfer.items.length > 0) {
      setIsDragging(true);
    }
  };

  const handleDrop = (e: React.DragEvent<HTMLDivElement>) => {
    e.preventDefault();
    e.stopPropagation();
    setIsDragging(false);
    dragCounter.current = 0;

    const files = e.dataTransfer.files;
    if (files.length > 0) {
      addFiles(files);
    }
  };

  const handleFileSelect = (e: React.ChangeEvent<HTMLInputElement>) => {
    const files = e.target.files;

    if (files && files.length > 0) {
      addFiles(files);
    }
  };

  const addFiles = (newFiles: FileList) => {
    const filesArray = Array.from(newFiles || []);

    const validFiles = filesArray.filter((file: any) => {
      const extension = file.name.split('.').pop().toUpperCase();
      const isValidType = supportedFileTypes.includes(extension);
      const isValidSize = file.size <= maxFileSize;
      if (!isValidType) {
        toast.error(`File type not supported: ${file.name}`);
      } else if (!isValidSize) {
        toast.error(
          `File size exceeds limit (${formatFileSize(maxFileSize)}): ${file.name}`,
        );
      }
      return isValidType && isValidSize;
    });

    if (validFiles.length === 0) return;

    const uploadedFiles = isMultiFile
      ? [...localAttachedFile, ...filesArray]
      : filesArray.slice(0, 1);

    const newPreviewsFile = { ...filePreviews };
    validFiles.forEach((file) => {
      if (file.type.startsWith('image/')) {
        newPreviewsFile[file.name] = URL.createObjectURL(file);
      }
    });

    setFilePreviews(newPreviewsFile);
    onFileAttach(uploadedFiles);
    setLocalAttachedFile(uploadedFiles);
  };

  const removeFile = (index: number) => {
    const fileToRemove = localAttachedFile[index];
    const updatedFiles = localAttachedFile.filter((_, i) => i !== index);
    onFileAttach(updatedFiles);
    setLocalAttachedFile(updatedFiles);
    // Remove preview
    setFilePreviews((prev) => {
      const newPreviews = {
        ...prev,
      };
      delete newPreviews[fileToRemove.name];
      return newPreviews;
    });
  };

  const getFileIcon = (file: File) => {
    if (file.type.startsWith('image/')) {
      return filePreviews[file.name] ? (
        (() => {
          const previewUrl = filePreviews[file.name] as string;
          return (
            <img
              src={previewUrl}
              alt={file.name}
              className='w-full h-full object-cover rounded-lg'
            />
          );
        })()
      ) : (
        <FaImage className='w-6 h-6' />
      );
    }
    return <BsFileText className='w-6 h-6' />;
  };

  return (
    <div className='bg-white p-8 rounded-xl border border-slate-200 shadow-sm'>
      <h3 className='text-lg font-semibold text-slate-900 mb-6'>{title}</h3>

      {(isMultiFile || localAttachedFile.length === 0) && (
        <div
          onDragOver={handleDragOver}
          onDragLeave={handleDragLeave}
          onDragEnter={handleDragEnter}
          onDrop={handleDrop}
          className={`
                  relative border-2 border-dashed rounded-xl p-12 text-center transition-all duration-200
                  ${isDragging ? 'border-[#e31f20] bg-red-50/20' : 'border-slate-300 hover:border-slate-400 bg-slate-50'}
                  ${isFocus ? 'border-2' : ''}
                `}
        >
          <Input
            type='file'
            id='file-upload'
            onFocus={() => setIsFocus(true)}
            onBlur={() => setIsFocus(false)}
            onChange={handleFileSelect}
            className='sr-only'
            accept={supportedFileTypes
              .map((type) => `.${type.toLowerCase()}`)
              .join(',')}
            multiple={isMultiFile}
          />
          <label htmlFor='file-upload' className='cursor-pointer'>
            <div
              className={`w-16 h-16 mx-auto mb-4 focus:outline-none rounded-full flex items-center justify-center ${isDragging ? 'bg-[#e31f20] text-white' : 'bg-slate-200 text-slate-500'}`}
            >
              <BsUpload className='w-8 h-8' />
            </div>
            <p className='text-lg font-medium text-slate-900 mb-2'>
              {isDragging ? 'Drop file here' : 'Drag & drop your file here'}
            </p>
            <p className='text-sm text-slate-500 mb-4'>or click to browse</p>
            <p className='text-xs text-slate-400'>
              Supported formats: {supportedFileTypes.join(', ')}. Max file size:{' '}
              {formatFileSize(maxFileSize)}.
            </p>
          </label>
        </div>
      )}

      {localAttachedFile?.length > 0 && (
        <div className='space-y-3 mt-6'>
          {localAttachedFile.map((file, index) => (
            <div
              key={index}
              className='flex items-center justify-between p-4 bg-slate-50 rounded-xl border border-slate-200 hover:border-slate-300'
            >
              <div className='flex items-center space-x-4 flex-1 min-w-0'>
                <div className='w-12 h-12 bg-blue-100 text-blue-600 rounded-lg flex items-center justify-center shrink-0 overflow-hidden'>
                  {getFileIcon(file)}
                </div>
                <div className='flex-1 min-w-0'>
                  <p className='font-medium text-slate-900 truncate'>
                    {file.name}
                  </p>
                  <p className='text-sm text-slate-500'>
                    {formatFileSize(file.size)}
                  </p>
                </div>
              </div>
              <button
                onClick={() => removeFile(index)}
                className='p-2 text-slate-400 cursor-pointer hover:text-[#e31f20] hover:bg-red-50 rounded-lg transition-all shrink-0 ml-4'
                title='Remove file'
              >
                <BsX className='w-5 h-5' />
              </button>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default AttachmentControl;
