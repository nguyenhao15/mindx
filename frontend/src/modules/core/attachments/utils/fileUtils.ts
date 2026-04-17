import {
  FaFile,
  FaFileArchive,
  FaFileExcel,
  FaFileImage,
  FaFilePdf,
  FaFileWord,
} from 'react-icons/fa';
import type { AttachmentItem } from '../components/AttachmentCard';

export const IMAGE_EXTENSIONS = [
  'jpg',
  'jpeg',
  'png',
  'gif',
  'webp',
  'bmp',
  'svg',
];
export const VIDEO_EXTENSIONS = ['mp4', 'mov', 'avi', 'mkv', 'webm', 'm4v'];

export const getFileNameFromPath = (path: string) => {
  const pathSections = path.split('/').filter(Boolean);
  if (pathSections.length === 0) {
    return path;
  }
  return pathSections[pathSections.length - 1];
};

export const parseFileSize = (size: AttachmentItem['fileSize']) => {
  if (typeof size === 'number' || typeof size === 'string') {
    return Number(size);
  }
  return Number(size.$numberLong ?? 0);
};

export const isImageFile = (file: any) => {
  const normalizedType = String(file?.fileType || '').toLowerCase();
  if (normalizedType.startsWith('image/')) return true;
  const extension = getExtensionFromPath(
    String(file?.pathName || file?.fileName || ''),
  );
  return IMAGE_EXTENSIONS.includes(extension);
};

export const isVideoFile = (file: any) => {
  const normalizedType = String(file?.fileType || '').toLowerCase();
  if (normalizedType.startsWith('video/')) return true;
  const extension = getExtensionFromPath(
    String(file?.pathName || file?.fileName || ''),
  );
  return VIDEO_EXTENSIONS.includes(extension);
};

export const getExtensionFromPath = (pathName: string) => {
  if (!pathName) return '';
  const sanitized = pathName.split('?')[0];
  const parts = sanitized.split('.');
  return parts.length > 1 ? parts[parts.length - 1].toLowerCase() : '';
};

export const getFileVisual = (fileType: string) => {
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
