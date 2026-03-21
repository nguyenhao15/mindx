import React from 'react';
import { AnimatePresence, motion } from 'framer-motion';
import { ComboboxListItem } from './ComboboxListItem';

interface Option {
  value: string | number;
  label: string;
}

interface ComboboxDropdownListProps {
  isOpen: boolean;
  filteredItems: Option[];
  selectedValues: Option[];
  selectedIndex: number;
  multiple: boolean;
  onItemClick: (item: Option, index: number) => void;
  itemRefs: React.MutableRefObject<(HTMLLIElement | null)[]>;
}

export const ComboboxDropdownList: React.FC<ComboboxDropdownListProps> = ({
  isOpen,
  filteredItems,
  selectedValues,
  selectedIndex,
  multiple,
  onItemClick,
  itemRefs,
}) => {
  return (
    <AnimatePresence>
      {isOpen && (
        <motion.ul
          initial={{ opacity: 0, height: 0, marginTop: 0 }}
          animate={{ opacity: 1, height: 'auto', marginTop: 4 }}
          exit={{ opacity: 0, height: 0, marginTop: 0 }}
          transition={{ duration: 0.1, ease: 'easeInOut' }}
          className='absolute z-10 w-full bg-white border border-gray-200 shadow-lg max-h-60 overflow-hidden'
          onMouseDown={(e) => e.preventDefault()}
        >
          <div className='overflow-auto max-h-60 p-3 rounded'>
            {filteredItems.length === 0 ? (
              <li className='px-4 py-2 text-gray-500'>No items found.</li>
            ) : (
              filteredItems.map((item, index) => {
                const isSelected = selectedValues.some(
                  (val) => val.value === item.value,
                );
                const isKeyboardActive = selectedIndex === index;

                return (
                  <ComboboxListItem
                    key={item.value}
                    item={item}
                    index={index}
                    isSelected={isSelected}
                    isKeyboardActive={isKeyboardActive}
                    multiple={multiple}
                    onClick={onItemClick}
                    itemRef={(el) => {
                      if (itemRefs.current) {
                        itemRefs.current[index] = el;
                      }
                    }}
                  />
                );
              })
            )}
          </div>
        </motion.ul>
      )}
    </AnimatePresence>
  );
};
