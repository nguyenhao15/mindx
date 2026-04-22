'use client';

import {
  Combobox,
  ComboboxContent,
  ComboboxEmpty,
  ComboboxItem,
  ComboboxList,
  ComboboxInput,
  useComboboxAnchor,
} from '@/components/ui/combobox';
import { useEffect, useMemo, useState, type JSX } from 'react';
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
  props?: {};
  errors?: string | null;
  description?: string;
  isMultiple?: boolean;
  isLoading?: boolean;
}

const handleOnChangeValueWhenMultiple = (
  nextValue: NormalizedOption | null,
  currentValue: NormalizedOption[],
) => {
  const isExisting = currentValue.some(
    (item) => item.value === nextValue?.value,
  );
  const resultValue = isExisting
    ? currentValue.filter((item) => item.value !== nextValue?.value)
    : [...currentValue, nextValue as NormalizedOption];
  return resultValue;
};

const handleSliceValueByLimit = (
  options: DynamicOption[] | undefined,
  value: NormalizedOption[] | null,
  limit?: number,
  handleRemoveChip?: (chipValue: string | number) => void,
): JSX.Element => {
  if (!Array.isArray(value) || value === null) {
    return <></>;
  }

  const labelingValue = value.slice(0, limit).map((val) => {
    const matchedOption = options?.find(
      (opt) => String(opt.value) === String(val.value),
    );
    return matchedOption
      ? { label: String(matchedOption.label), value: val.value }
      : val;
  });

  return (
    <div className='mt-2 flex flex-row flex-wrap gap-2'>
      {labelingValue.map((item: NormalizedOption) => (
        <div
          className='cursor-pointer bg-primary text-white rounded-md p-2 text-sm font-semibold flex items-center gap-1'
          key={String(item.value)}
          title='Remove'
          onClick={() => handleRemoveChip?.(item.value)}
        >
          <span>{item.label}</span>
        </div>
      ))}
      {limit && value.length > limit ? (
        <span className='text-sm self-center items-center text-gray-500'>
          +{value.length - limit} more
        </span>
      ) : null}
    </div>
  );
};
const ComboboxComponent = ({
  options,
  onChange,
  description,
  defaultValue,
  label = 'Multiple Combobox Example',
  placeholder = 'Add option',
  required = false,
  limit,
  errors = null,
  isMultiple = false,
  isLoading = false,
  disabled = false,
  IconNode,
  ...props
}: ComboboxComponentProps) => {
  const normalizedDefaultValue = useMemo(() => {
    if (isMultiple) {
      return Array.isArray(defaultValue)
        ? defaultValue.map((val) => ({ label: String(val), value: val }))
        : [];
    } else {
      if (defaultValue === null || defaultValue === undefined) return null;
      if (
        typeof defaultValue === 'string' ||
        typeof defaultValue === 'number'
      ) {
        const matchedOption = options?.find(
          (opt) => String(opt.value) === String(defaultValue),
        );
        return (matchedOption as NormalizedOption) || null;
      }
      return null;
    }
  }, [defaultValue, options, isMultiple]);

  const [value, setValue] = useState<
    NormalizedOption | null | NormalizedOption[]
  >(normalizedDefaultValue);

  const handleValueChange = (nextValue: any) => {
    const resultValue = isMultiple
      ? handleOnChangeValueWhenMultiple(
          nextValue,
          (value as NormalizedOption[]) || [],
        )
      : nextValue;
    setValue(resultValue);
    onChange?.(
      isMultiple
        ? (resultValue as NormalizedOption[]).map((item) => item.value)
        : (resultValue as NormalizedOption)?.value || null,
    );
  };

  const handleRemoveChip = (chipValue: string | number) => {
    if (!isMultiple || !Array.isArray(value)) return;
    const updatedValue = (value as NormalizedOption[]).filter(
      (item) => item.value !== chipValue,
    );
    setValue(updatedValue);
    onChange?.(updatedValue.map((item) => item.value));
  };

  const handleOnValueChange = (
    nextValue: NormalizedOption | NormalizedOption[] | null,
  ) => {
    if (
      nextValue === null ||
      (Array.isArray(nextValue) && nextValue.length === 0)
    ) {
      setValue(isMultiple ? [] : null);
      onChange?.(isMultiple ? [] : null);
    }
  };
  useEffect(() => {
    setValue(normalizedDefaultValue);
  }, [normalizedDefaultValue]);

  return (
    <CustomInputCard
      {...props}
      labelSize='lg'
      description={description}
      label={label}
      disabled={disabled}
      required={required}
      errorMessage={errors}
    >
      <Combobox
        items={options}
        multiple={isMultiple}
        disabled={disabled}
        onValueChange={handleOnValueChange}
        readOnly={isLoading || disabled}
        value={value}
        openOnInputClick={true}
        itemToStringLabel={(item) => item.label}
        itemToStringValue={(item) => String(item.value)}
      >
        <ComboboxInput
          aria-invalid={!!errors}
          showTrigger={!isLoading}
          readOnly={isLoading}
          disabled={disabled || isLoading}
          size={20}
          placeholder={placeholder}
          showClear
          className={`py-4 rounded-sm h-15 shadow border-2 bg-input-background ${errors ? 'border-red-500' : 'border-slate-300'} focus:ring-2 focus:ring-primary focus:outline-none`}
        >
          {IconNode && (
            <InputGroupAddon>
              <IconNode className='w-5 h-5 text-gray-400' />
            </InputGroupAddon>
          )}
        </ComboboxInput>

        <ComboboxContent className='w-full p-1'>
          <ComboboxEmpty>No items found.</ComboboxEmpty>
          <ComboboxList>
            {(item) => (
              <ComboboxItem
                className='cursor-pointer m-2 rounded-md p-2 hover:bg-gray-100'
                onClick={() => handleValueChange(item)}
                key={item.value}
                value={item}
              >
                {item.label}
              </ComboboxItem>
            )}
          </ComboboxList>
        </ComboboxContent>
        {isMultiple &&
          handleSliceValueByLimit(
            options,
            value as NormalizedOption[] | null,
            limit,
            handleRemoveChip,
          )}
      </Combobox>
    </CustomInputCard>
  );
};

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
  isLoading = false,
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

export { ComboboxComponent, SingleComboboxComponent };
