import React, { useEffect, useRef, useState } from 'react';
import { ComboboxDropdownList, ComboboxInputField } from './combobox-parts';

interface Option {
  value: string | number;
  label: string;
  [key: string]: any; // Cho phép các thuộc tính khác nếu cần
}

interface UpdateType {
  type: 'value' | 'object';
}
interface ManualCustomComboboxProps {
  options: Option[];
  multiple?: boolean;
  onChange?: (value: any) => void;
  onChangeInput?: (value: string) => void;
  onBlur?: (e: React.FocusEvent) => void;
  onFocus?: () => void;
  label?: string;
  id: string;
  defaultValue?: any;
  value?: any;
  name?: string;
  isLoading?: boolean;
  required?: boolean;
  errors?: string | null;
  placeholder?: string;
  updateType?: UpdateType;
  disabled?: boolean;
}
interface ComboboxState {
  isOpen: boolean;
  selectedIndex: number;
  inputValue: string;
  selectedValues?: Option[];
}

const ManualCustomCombobox = ({
  options = [],
  multiple = false,
  onChange,
  onBlur,
  onChangeInput,
  onFocus,
  isLoading = false,
  label = '',
  defaultValue,
  value,
  id = '',
  errors = '',
  required = false,
  placeholder = '',
  updateType = { type: 'value' },
  disabled = false,
}: ManualCustomComboboxProps) => {
  const inputRef = useRef<HTMLInputElement | null>(null);
  const itemRefsArray = useRef<(HTMLLIElement | null)[]>([]);

  const [state, setState] = useState<ComboboxState>(() => {
    let initialSelected: Option[] = [];
    const val = value !== undefined ? value : defaultValue;
    if (val !== undefined && val !== null && val !== '') {
      let targetValues: any[] = [];
      if (Array.isArray(val)) {
        targetValues = val.map((v) =>
          typeof v === 'object' && v !== null && 'value' in v ? v.value : v,
        );
      } else if (typeof val === 'object' && val !== null && 'value' in val) {
        targetValues = [val.value];
      } else {
        targetValues = [val];
      }
      initialSelected = options.filter((opt) =>
        targetValues.includes(opt.value),
      );
    }
    return {
      isOpen: false,
      selectedIndex: -1,
      inputValue: '',
      selectedValues: initialSelected || [],
    };
  });

  useEffect(() => {
    const val = value !== undefined ? value : defaultValue;

    if (val !== undefined && val !== null && val !== '') {
      let targetValues: any[] = [];
      if (Array.isArray(val)) {
        targetValues = val.map((v) =>
          typeof v === 'object' && v !== null && 'value' in v ? v.value : v,
        );
      } else if (typeof val === 'object' && val !== null && 'value' in val) {
        targetValues = [val.value];
      } else {
        targetValues = [val];
      }

      const matchedOptions = options?.filter((opt) =>
        targetValues.includes(opt.value),
      );

      setState((prev) => {
        const currentVals = prev.selectedValues || [];
        const isDifferent =
          currentVals.length !== matchedOptions.length ||
          !currentVals.every((v, i) => v.value === matchedOptions[i]?.value);

        if (isDifferent) {
          return { ...prev, selectedValues: matchedOptions };
        }
        return prev;
      });
    } else {
      setState((prev) => {
        if (prev.selectedValues && prev.selectedValues.length > 0) {
          return { ...prev, selectedValues: [] };
        }
        return prev;
      });
    }
  }, [value, defaultValue, options]);

  useEffect(() => {
    if (
      state.selectedIndex >= 0 &&
      itemRefsArray.current[state.selectedIndex]
    ) {
      itemRefsArray.current[state.selectedIndex]?.scrollIntoView({
        behavior: 'smooth',
        block: 'nearest',
      });
    }
  }, [state.selectedIndex]);

  const handleBlur = (e: React.FocusEvent) => {
    if (!e.currentTarget.contains(e.relatedTarget)) {
      setState((prev) => ({ ...prev, isOpen: false, selectedIndex: -1 }));
    }
    onBlur && onBlur(e);
  };

  const handleSelectItem = (item: Option, index: number) => {
    // Phản hồi UI tức thì
    setState((prev) => ({ ...prev, selectedIndex: index }));

    requestAnimationFrame(() => {
      setState((prev) => {
        const currentValues = prev.selectedValues || [];
        const isSelected = currentValues.some((v) => v.value === item.value);

        // Luôn thao tác trên mảng các Option object
        const nextValues = multiple
          ? isSelected
            ? currentValues.filter((v) => v.value !== item.value)
            : [...currentValues, item]
          : [item];

        // Tối ưu hóa: So sánh sâu đơn giản
        const hasChanged =
          JSON.stringify(nextValues) !== JSON.stringify(currentValues);
        requestAnimationFrame(() => {
          if (onChange && hasChanged) {
            const payload =
              updateType.type === 'value'
                ? multiple
                  ? nextValues.map((v) => v.value)
                  : nextValues[0]?.value
                : multiple
                  ? nextValues
                  : nextValues[0];
            onChange(payload);
          }
        });

        return {
          ...prev,
          selectedValues: nextValues,
          inputValue: '',
          isOpen: multiple,
          selectedIndex: multiple ? index : -1,
        };
      });
    });
  };

  const handleOnFocus = () => {
    if (isLoading) return;
    setState((prev) => ({ ...prev, isOpen: true, selectedIndex: -1 }));
    onFocus && onFocus();
  };

  useEffect(() => {
    if (state.isOpen && inputRef.current) {
      const timer = setTimeout(() => {
        inputRef.current?.focus();
      }, 50);
      return () => clearTimeout(timer);
    }
  }, [state.isOpen]); // Chạy lại mỗi khi isOpen thay đổi

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    onChangeInput && onChangeInput(value);
    if (!state.isOpen) {
      setState((prev) => ({ ...prev, isOpen: true }));
    }
    setState((prev) => ({ ...prev, inputValue: value }));
  };

  const handleRemoveItem = (itemToRemove: Option) => {
    setState((prev) => {
      const currentValues = prev.selectedValues || [];
      const nextValues = currentValues.filter(
        (v) => v.value !== itemToRemove.value,
      );

      if (onChange) {
        const payload =
          updateType.type === 'value'
            ? multiple
              ? nextValues.map((v) => v.value)
              : nextValues[0]?.value
            : multiple
              ? nextValues
              : nextValues[0];

        onChange(payload || (multiple ? [] : null));
      }

      return {
        ...prev,
        selectedValues: nextValues,
      };
    });
  };

  const filteredItems = options?.filter((item) =>
    item.label.toLowerCase().includes(state.inputValue?.toLowerCase() || ''),
  );

  const handleKeyDown = (
    e: React.KeyboardEvent,
    state: ComboboxState,
    setState: React.Dispatch<React.SetStateAction<ComboboxState>>,
    filteredItems: Option[],
  ) => {
    if (isLoading) return;
    switch (e.key) {
      case 'ArrowDown':
        e.preventDefault(); // Ngăn trình duyệt cuộn trang
        setState((prev: ComboboxState) => ({
          ...prev,
          isOpen: true,
          // Nếu ở cuối danh sách, quay lại 0. Nếu chưa chọn thì chọn 0.
          selectedIndex:
            prev.selectedIndex >= filteredItems.length - 1
              ? 0
              : prev.selectedIndex + 1,
        }));
        break;

      case 'ArrowUp':
        e.preventDefault();
        setState((prev: ComboboxState) => {
          // Nếu ở đầu danh sách (0), di chuyển về Input (selectedIndex = -1)
          if (prev.selectedIndex <= 0) {
            return { ...prev, selectedIndex: -1 };
          }
          return { ...prev, selectedIndex: prev.selectedIndex - 1 };
        });
        break;

      case 'Enter':
        e.preventDefault();
        if (state.selectedIndex >= 0) {
          // Xử lý chọn item dựa trên index hiện tại
          const selected = filteredItems[state.selectedIndex];
          if (selected) {
            handleSelectItem(selected, state.selectedIndex);
          }
        }
        break;
      case 'Escape':
        e.preventDefault();
        setState((prev) => ({ ...prev, isOpen: false }));
        inputRef.current?.blur(); // Tùy chọn: bỏ focus nếu user muốn thoát hẳn
        break;

      default:
        break;
    }
  };

  return (
    <div className='flex flex-col gap-1'>
      <div className='relative w-full max-w-3xl'>
        <ComboboxInputField
          id={id}
          disabled={disabled}
          inputRef={inputRef as React.RefObject<HTMLInputElement>}
          value={state.inputValue}
          onChange={handleInputChange}
          required={required}
          onFocus={handleOnFocus}
          onBlur={handleBlur}
          onKeyDown={(e) => handleKeyDown(e, state, setState, filteredItems)}
          isOpen={state.isOpen}
          selectedValues={state.selectedValues || []}
          multiple={multiple}
          placeholder={placeholder}
          onToggleDropdown={(e) => {
            if (isLoading) return;
            e.preventDefault();
            setState((prev) => ({
              ...prev,
              isOpen: !prev.isOpen,
            }));
          }}
          label={label}
          errors={errors}
          isLoading={isLoading}
          onRemoveItem={handleRemoveItem}
        />
        <ComboboxDropdownList
          isOpen={state.isOpen}
          filteredItems={filteredItems}
          selectedValues={state.selectedValues || []}
          selectedIndex={state.selectedIndex}
          multiple={multiple}
          onItemClick={handleSelectItem}
          itemRefs={itemRefsArray}
        />
      </div>
    </div>
  );
};

export default ManualCustomCombobox;
