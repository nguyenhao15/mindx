import { Search } from 'lucide-react';
import React from 'react';
import Spinner from '../shared/Spinner';

interface InputWithIconProps {
  defaultValue?: string;
  onChange?: (value: string) => void;
  onFocus?: () => void;
  onBlur?: () => void;
  Icon?: React.ComponentType;
  isLoading?: boolean;
  placeholder?: string;
  type?: string;
  className?: string;
}

const InputWithIcon = ({
  defaultValue,
  onChange,
  onFocus,
  isLoading,
  onBlur,
  placeholder,
  Icon = Search,
  type = 'text',
  className = '',
}: InputWithIconProps) => {
  return (
    <div
      className={` ${className ? className : 'rounded-md'} flex items-center gap-2 border-2 p-2 focus-within:ring-2 focus-within:ring-blue-500 ${isLoading ? 'border-gray-300' : 'border-gray-300'}`}
    >
      {isLoading ? <Spinner /> : <Icon />}
      <input
        type={type}
        defaultValue={defaultValue}
        disabled={isLoading}
        onChange={(e) => onChange && onChange(e.target.value)}
        onFocus={onFocus}
        placeholder={isLoading ? 'Loading...' : placeholder || 'Search...'}
        onBlur={onBlur}
        className='flex-1 outline-none'
      />
    </div>
  );
};

export default InputWithIcon;
