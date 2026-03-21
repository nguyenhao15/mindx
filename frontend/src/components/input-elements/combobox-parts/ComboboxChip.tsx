import React from 'react';
import { X } from 'lucide-react';

interface ComboboxChipProps {
  label: string;
  onRemove: () => void;
}

export const ComboboxChip: React.FC<ComboboxChipProps> = ({
  label,
  onRemove,
}) => {
  return (
    <span
      tabIndex={-1}
      className='bg-blue-100 px-2 py-1 rounded flex items-center gap-1 text-sm'
      onClick={(e) => {
        e.stopPropagation();
        onRemove();
      }}
    >
      {label}
      <button className='hover:text-red-500'>
        <X className='size-3' />
      </button>
    </span>
  );
};
