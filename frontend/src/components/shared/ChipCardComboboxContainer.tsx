import React from 'react';
import InputWithIcon from '../input-elements/InputWithIcon';
import { Button } from '../ui/button';
import { FaSearch } from 'react-icons/fa';
import { X } from 'lucide-react';

interface ChipCardComboboxContainerProps {
  children: React.ReactNode;
  selectedItems: { id: string; label: string }[];
  defaultKeyword?: string;
  handleSearchKeyWord: (value: string) => void;
  handleClear: () => void;
  handleApply: () => void;
  handleToggleTag?: (id: string) => void;
}

const ChipCardComboboxContainer = ({
  children,
  selectedItems,
  defaultKeyword,
  handleSearchKeyWord,
  handleClear,
  handleApply,
  handleToggleTag,
}: ChipCardComboboxContainerProps) => {
  const handleOnClickTag = (id: string) => {
    if (handleToggleTag) {
      handleToggleTag(id);
    }
  };

  return (
    <div className=' max-w-3xl min-w-4xl max-h-3xl p-4 bg-white rounded shadow-lg flex flex-col gap-4'>
      <InputWithIcon
        placeholder='Type tag...'
        Icon={FaSearch}
        defaultValue={defaultKeyword}
        onChange={handleSearchKeyWord}
      />

      {selectedItems.length > 0 && (
        <>
          <div className='flex flex-row flex-wrap'>
            {selectedItems.map((item) => {
              return (
                <div
                  key={item.id}
                  onClick={() => handleOnClickTag(item.id)}
                  className='px-3 cursor-pointer flex items-center flex-nowrap gap-2 py-1 m-1 bg-gray-200 rounded-full text-sm'
                >
                  {item.label} <X size={15} className='self-center' />
                </div>
              );
            })}
          </div>
        </>
      )}
      {selectedItems.length === 0 && <div>No tags selected</div>}

      <div>
        {/* list of tags */}
        <div className='flex justify-end gap-2'>
          <Button
            className='px-4 py-2 rounded bg-gray-200 hover:bg-gray-300 cursor-pointer'
            onClick={handleClear}
            variant='outline'
          >
            Clear
          </Button>
          <Button
            className='px-4 py-2 rounded bg-blue-500 text-white cursor-pointer hover:bg-blue-600'
            onClick={handleApply}
            variant='positive'
          >
            Apply
          </Button>
        </div>
      </div>
      {children}
    </div>
  );
};

export default ChipCardComboboxContainer;
