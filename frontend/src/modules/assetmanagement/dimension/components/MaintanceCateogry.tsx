import { useMemo, useState } from 'react';
import { useGetCategoryOptions } from '../hooks/useCategoryHook';
import { Controller, type Control, type FieldValues } from 'react-hook-form';
import ManualCustomCombobox from '@/components/input-elements/ManualCustomCombobox';

type ComboboxValue = string | number | null | undefined;

type CategoryOption = {
  id: string;
  categoryTitle: string;
};

interface MaintanceCategoryProps {
  control?: Control<FieldValues>;
  label: string;
  placeholder?: string;
  name: string;
  required?: boolean;
  disabled?: boolean;
  value?: ComboboxValue;
  defaultValue?: ComboboxValue;
  errors?: string | null;
  onChange?: (value: ComboboxValue) => void;
}

const MaintanceCategory = ({
  control,
  label,
  name,
  placeholder,
  required = true,
  disabled = false,
  value,
  defaultValue,
  errors,
  onChange,
}: MaintanceCategoryProps) => {
  const [internalValue, setInternalValue] =
    useState<ComboboxValue>(defaultValue);
  const { data, isLoading } = useGetCategoryOptions();

  const categoryOptions = useMemo(() => {
    if (!data) return [];
    return (data as CategoryOption[]).map((item) => ({
      label: item.categoryTitle,
      value: item.id,
      ...item,
    }));
  }, [data]);

  const renderCombobox = (
    selectedValue: ComboboxValue,
    handleValueChange: (nextValue: ComboboxValue) => void,
    errorMessage?: string,
  ) => {
    return (
      <ManualCustomCombobox
        id={name}
        label={label}
        placeholder={placeholder}
        options={categoryOptions}
        value={selectedValue}
        onChange={handleValueChange}
        disabled={disabled}
        errors={errorMessage}
        required={required}
        isLoading={isLoading}
      />
    );
  };
  if (control) {
    return (
      <Controller
        name={name}
        control={control}
        render={({ field, fieldState }) =>
          renderCombobox(field.value, field.onChange, fieldState.error?.message)
        }
      />
    );
  }

  const isExternallyControlled = value !== undefined;
  const selectedValue = isExternallyControlled ? value : internalValue;

  return renderCombobox(
    selectedValue,
    (nextValue) => {
      if (!isExternallyControlled) {
        setInternalValue(nextValue);
      }
      onChange?.(nextValue);
    },
    errors || undefined,
  );
};

export default MaintanceCategory;
