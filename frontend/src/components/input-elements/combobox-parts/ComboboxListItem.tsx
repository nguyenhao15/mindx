import React from 'react';

interface Option {
  value: string | number;
  label: string;
}

interface ComboboxListItemProps {
  item: Option;
  index: number;
  isSelected: boolean;
  isKeyboardActive: boolean;
  multiple: boolean;
  onClick: (item: Option, index: number) => void;
  itemRef?: React.Ref<HTMLLIElement>;
}

export const ComboboxListItem: React.FC<ComboboxListItemProps> = ({
  item,
  index,
  isSelected,
  isKeyboardActive,
  multiple,
  onClick,
  itemRef,
}) => {
  const getItemClasses = () => {
    const baseClasses =
      'px-4 py-2 flex gap-2 rounded items-center cursor-pointer transition-colors duration-150';

    if (isSelected) {
      return `${baseClasses} duration-150 ease-in-out bg-green-500 text-white font-bold`;
    }

    if (isKeyboardActive) {
      return `${baseClasses} bg-gray-100 text-gray-900`;
    }
    return `${baseClasses} hover:bg-gray-100 hover:text-gray-900`;
  };

  return (
    <li
      ref={itemRef}
      key={item.value}
      onClick={() => onClick(item, index)}
      className={getItemClasses()}
    >
      {multiple ? (
        <input
          type='checkbox'
          tabIndex={-1}
          checked={isSelected}
          className='cursor-pointer'
          readOnly
        />
      ) : null}
      {item.label}
    </li>
  );
};
