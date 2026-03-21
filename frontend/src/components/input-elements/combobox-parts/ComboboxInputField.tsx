import React from 'react';
import { FaChevronDown } from 'react-icons/fa';
import { ComboboxChip } from './ComboboxChip';
import Spinner from '@/components/shared/Spinner';
import CustomInputCard from '../CustomInputCard';

interface Option {
  value: string | number;
  label: string;
}

interface ComboboxInputFieldProps {
  inputRef: React.RefObject<HTMLInputElement>;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  onFocus: () => void;
  onBlur: (e: React.FocusEvent) => void;
  onKeyDown: (e: React.KeyboardEvent) => void;
  isOpen: boolean;
  selectedValues: Option[];
  id: string;
  multiple: boolean;
  placeholder?: string;
  onToggleDropdown: (e: React.MouseEvent) => void;
  onRemoveItem: (item: Option) => void;
  isLoading?: boolean;
  errors: string | null;
  required?: boolean;
  label?: string;
}

export const ComboboxInputField: React.FC<ComboboxInputFieldProps> = ({
  inputRef,
  value,
  onChange,
  onFocus,
  onBlur,
  onKeyDown,
  id,
  isOpen,
  selectedValues,
  multiple,
  placeholder = 'Select options',
  onToggleDropdown,
  onRemoveItem,
  isLoading = false,
  errors,
  required = false,
  label = '',
}) => {
  return (
    <div className='flex flex-col gap-2'>
      <CustomInputCard
        label={label}
        required={required}
        description={''}
        errorMessage={errors}
      >
        <div
          className={`flex flex-row px-3 py-3 border-2 min-h-10 items-center focus-within:ring-2 focus-within:ring-blue-500 rounded ${errors ? 'border-red-500 border-2' : 'border-gray-300'}`}
        >
          <div className='flex flex-1 h-7 flex-row justify-start overflow-hidden'>
            <input
              id={id}
              type='text'
              disabled={isLoading}
              ref={inputRef}
              onBlur={onBlur}
              tabIndex={0}
              value={value}
              className={`flex-1 outline-none ${
                !isOpen && selectedValues.length > 0
                  ? 'absolute opacity-0 pointer-events-none'
                  : 'opacity-100 relative'
              }`}
              // className={`${selectedValues.length === 0 ? 'flex-1' : ''} outline-none ${!isOpen && selectedValues.length > 0 ? 'opacity-0 w-0 h-0' : 'opacity-100 '}`}
              placeholder={
                isLoading ? 'Loading...' : placeholder || 'Select options'
              }
              onChange={onChange}
              onFocus={onFocus}
              onKeyDown={onKeyDown}
            />
            {!isOpen && selectedValues.length > 0 && (
              <div
                tabIndex={-1}
                className='flex w-fit overflow-x-auto no-scrollbar gap-1 cursor-pointer'
                //   onMouseDown={onToggleDropdown}
              >
                {multiple ? (
                  selectedValues.map((val) => (
                    <ComboboxChip
                      key={val.value}
                      label={val.label}
                      onRemove={() => onRemoveItem(val)}
                    />
                  ))
                ) : (
                  <span
                    tabIndex={-1}
                    className='w-full select-none truncate my-auto text-gray-700'
                  >
                    {selectedValues[0]?.label || placeholder}
                  </span>
                )}
              </div>
            )}
          </div>

          {isLoading ? (
            <Spinner />
          ) : (
            <FaChevronDown
              tabIndex={-1}
              onMouseDown={onToggleDropdown}
              onFocus={onFocus}
              className={`size-4 fill-black group-data-hover:fill-gray-600 ${
                isOpen ? 'rotate-180' : ''
              } transition-transform duration-500 cursor-pointer`}
            />
          )}
        </div>
      </CustomInputCard>
    </div>
  );
};
