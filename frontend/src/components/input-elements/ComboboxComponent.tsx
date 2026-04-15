'use client';

import {
  Combobox,
  ComboboxChip,
  ComboboxChips,
  ComboboxChipsInput,
  ComboboxContent,
  ComboboxEmpty,
  ComboboxItem,
  ComboboxList,
  ComboboxValue,
  ComboboxInput,
  useComboboxAnchor,
} from '@/components/ui/combobox';
import { useEffect, useMemo, useState } from 'react';
import CustomInputCard from './CustomInputCard';
import { InputGroupAddon } from '../ui/input-group';

type DynamicOption = Record<string, unknown>;

type NormalizedOption = {
  label: string;
  value: string | number;
};

interface ComboboxComponentProps {
  IconNode?: any;
  options?: DynamicOption[];
  onChange?: (value: (string | number)[] | string | number | null) => void;
  defaultValue?: (string | number)[] | (string | number) | null;
  label?: string;
  placeholder?: string;
  required?: boolean;
  limit?: number;
  disabled?: boolean;
  props: {};
  errors?: string | null;
  isMultiple?: boolean;
  isLoading?: boolean;
}

function MultipleComboboxComponent({
  options,
  onChange,
  defaultValue = [],
  label = 'Multiple Combobox Example',
  placeholder = 'Add option',
  required = false,
  limit,
  errors = null,
  isMultiple = false,
  isLoading = false,
  disabled = false,
  ...props
}: ComboboxComponentProps) {
  const anchor = useComboboxAnchor();
  const normalizedDefaultValue = useMemo(
    () =>
      Array.isArray(defaultValue)
        ? defaultValue.map((val) => ({ label: String(val), value: val }))
        : [],
    [defaultValue],
  );
  const [value, setValue] = useState<NormalizedOption[]>(
    normalizedDefaultValue,
  );

  const [open, setOpen] = useState(false);

  useEffect(() => {
    setValue(normalizedDefaultValue);
  }, [normalizedDefaultValue]);

  const handleValueChange = (nextValue: NormalizedOption[]) => {
    setValue(nextValue);
    onChange?.(nextValue.map((item) => item.value));
  };

  return (
    <CustomInputCard
      label={label}
      {...props}
      required={required}
      description={'Thông tin'}
      errorMessage={errors}
    >
      <Combobox
        items={options}
        multiple
        disabled={disabled}
        readOnly={isLoading || disabled}
        value={value}
        onValueChange={handleValueChange}
        onOpenChange={setOpen}
        itemToStringLabel={(item) => item.label}
        itemToStringValue={(item) => String(item.value)}
      >
        <ComboboxChips
          className={`w-full ${open ? 'ring-2 ring-primary' : 'ring-1 ring-slate-300'} py-2 rounded-sm h-15 shadow bg-input-background`}
          ref={anchor}
          aria-label='Select options'
        >
          {!open && (
            <ComboboxValue>
              {value.map((item) => (
                <ComboboxChip
                  key={String(item.value)}
                  className='py-1 text-sm font-semibold'
                >
                  {item.label}
                </ComboboxChip>
              ))}
            </ComboboxValue>
          )}
          <ComboboxChipsInput
            className='py-1 text-base'
            aria-invalid={!!errors}
            placeholder={placeholder}
          />
        </ComboboxChips>

        <ComboboxContent anchor={anchor} className='w-full p-1'>
          <ComboboxEmpty>No items found.</ComboboxEmpty>
          <ComboboxList>
            {(item) => (
              <ComboboxItem
                className='cursor-pointer'
                key={item.value}
                value={item}
              >
                {item.label}
              </ComboboxItem>
            )}
          </ComboboxList>
        </ComboboxContent>
      </Combobox>
    </CustomInputCard>
  );
}

function SingleComboboxComponent({
  IconNode,
  options,
  onChange,
  defaultValue,
  label = 'Single Combobox Example',
  placeholder = 'Select option',
  required = false,
  errors = null,
  disabled = false,
  isLoading,
  ...props
}: Omit<ComboboxComponentProps, 'isMultiple'>) {
  const defaultValueNormalized: NormalizedOption | null = useMemo(() => {
    if (defaultValue === null || defaultValue === undefined) return null;
    if (typeof defaultValue === 'string' || typeof defaultValue === 'number') {
      const matchedOption = options?.find(
        (opt) => String(opt.value) === String(defaultValue),
      );
      return (matchedOption as NormalizedOption) || null;
    }
    return null;
  }, [defaultValue, options]);
  const anchor = useComboboxAnchor();
  const [value, setValue] = useState<NormalizedOption | null>(
    defaultValueNormalized,
  );

  useEffect(() => {
    setValue(defaultValueNormalized);
  }, [defaultValueNormalized]);

  const handleValueChange = (nextValue: NormalizedOption | null) => {
    setValue(nextValue);
    onChange?.(nextValue ? nextValue.value : null);
  };

  return (
    <CustomInputCard
      {...props}
      labelSize='lg'
      label={label}
      required={required}
      errorMessage={errors}
    >
      <Combobox
        items={options}
        disabled={disabled}
        value={value}
        readOnly={isLoading}
        onValueChange={handleValueChange}
        itemToStringLabel={(item) => item.label}
        itemToStringValue={(item) => String(item.value)}
        {...props}
      >
        <ComboboxInput
          aria-invalid={!!errors}
          showTrigger={!isLoading}
          readOnly={isLoading}
          disabled={disabled || isLoading}
          size={20}
          placeholder={placeholder}
          className={`py-4 rounded-sm h-15 shadow border-2 bg-input-background ${errors ? 'border-red-500' : 'border-slate-300'} focus:ring-2 focus:ring-primary focus:outline-none`}
        >
          {IconNode && (
            <InputGroupAddon>
              <IconNode className='w-5 h-5 text-gray-400' />
            </InputGroupAddon>
          )}
        </ComboboxInput>
        <ComboboxContent anchor={anchor} className='mt-3 w-full p-1'>
          <ComboboxEmpty>No items found.</ComboboxEmpty>
          <ComboboxList>
            {(item) => (
              <ComboboxItem
                className='cursor-pointer'
                key={item.value}
                value={item}
              >
                {item.label}
              </ComboboxItem>
            )}
          </ComboboxList>
        </ComboboxContent>
      </Combobox>
    </CustomInputCard>
  );
}

export { MultipleComboboxComponent, SingleComboboxComponent };
