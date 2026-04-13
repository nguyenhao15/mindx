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
import { useMemo, useState } from 'react';
import CustomInputCard from './CustomInputCard';

type DynamicOption = Record<string, unknown>;

type NormalizedOption = {
  label: string;
  value: string | number;
};

interface ComboboxComponentProps {
  options?: DynamicOption[];
  onChange?: (value: (string | number)[]) => void;
  defaultValue?: (string | number)[] | (string | number) | null;
  label?: string;
  placeholder?: string;
  required?: boolean;
  limit?: number;
  disabled?: boolean;
  props: {};
  errors?: string | null;
  isMultiple?: boolean;
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
  ...props
}: ComboboxComponentProps) {
  const anchor = useComboboxAnchor();
  const [value, setValue] = useState<NormalizedOption[]>(
    Array.isArray(defaultValue)
      ? defaultValue.map((val) => ({ label: String(val), value: val }))
      : [],
  );

  const [open, setOpen] = useState(false);

  const handleValueChange = (nextValue: NormalizedOption[]) => {
    setValue(nextValue);
    onChange?.(nextValue.map((item) => item.value));
  };

  return (
    <CustomInputCard
      label={label}
      required={required}
      description={'Thông tin'}
      errorMessage={errors}
    >
      <Combobox
        items={options}
        multiple
        value={value}
        onValueChange={handleValueChange}
        onOpenChange={setOpen}
        itemToStringLabel={(item) => item.label}
        itemToStringValue={(item) => String(item.value)}
        {...props}
      >
        <ComboboxChips
          className={`w-full ${open ? 'ring-2 ring-primary' : 'ring-1 ring-slate-300'}`}
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
  options,
  onChange,
  defaultValue,
  label = 'Single Combobox Example',
  placeholder = 'Select option',
  required = false,
  errors = null,
  disabled = false,
  ...props
}: Omit<ComboboxComponentProps, 'isMultiple'>) {
  const defaultValueNormalized: NormalizedOption | null = useMemo(() => {
    if (defaultValue === null || defaultValue === undefined) return null;
    if (typeof defaultValue === 'string' || typeof defaultValue === 'number') {
      return { label: String(defaultValue), value: defaultValue };
    }
    return null;
  }, [defaultValue]);
  const anchor = useComboboxAnchor();
  const [value, setValue] = useState<NormalizedOption | null>(
    defaultValueNormalized,
  );

  const handleValueChange = (nextValue: NormalizedOption | null) => {
    setValue(nextValue);
    onChange?.(nextValue ? [nextValue.value] : []);
  };

  return (
    <CustomInputCard
      label={label}
      required={required}
      description={'Thông tin'}
      errorMessage={errors}
    >
      <Combobox
        items={options}
        disabled={disabled}
        value={value}
        onValueChange={handleValueChange}
        itemToStringLabel={(item) => item.label}
        itemToStringValue={(item) => String(item.value)}
        {...props}
      >
        <ComboboxInput showTrigger={false} placeholder={placeholder} />
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

export { MultipleComboboxComponent, SingleComboboxComponent };
