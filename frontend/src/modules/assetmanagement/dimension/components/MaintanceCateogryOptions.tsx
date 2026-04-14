import { useMemo, useState } from 'react';
import { useGetCategoryOptions } from '../hooks/useCategoryHook';
import { Controller, useFormContext } from 'react-hook-form';
import { SingleComboboxComponent } from '@/components/input-elements/ComboboxComponent';

type ComboboxValue = string | number | null | undefined;

type CategoryOption = {
  id: string;
  categoryTitle: string;
};

interface MaintanceCategoryOptionsProps {
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

const MaintanceCategoryOptions = ({
  label,
  name,
  placeholder,
  required = true,
  disabled = false,
  value,
  defaultValue,
  errors,
  onChange,
}: MaintanceCategoryOptionsProps) => {
  const [internalValue, setInternalValue] =
    useState<ComboboxValue>(defaultValue);
  const { data, isLoading } = useGetCategoryOptions();

  const { control } = useFormContext();

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
    handleValueChange: (nextValue: any) => void,
    errorMessage?: string,
    props?: any,
  ) => {
    return (
      <SingleComboboxComponent
        {...props}
        required={required}
        disabled={disabled}
        errors={errorMessage || null}
        options={categoryOptions}
        onChange={handleValueChange}
        defaultValue={selectedValue ? [selectedValue] : []}
        label={label}
        placeholder={placeholder}
      />
    );
  };

  if (control) {
    return (
      <Controller
        name={name}
        control={control}
        render={({ field: { onChange, value, ...field }, fieldState }) =>
          renderCombobox(value, onChange, fieldState.error?.message, field)
        }
      />
    );
  }

  const isExternallyControlled = value !== undefined;
  const selectedValue = isExternallyControlled ? value : internalValue;

  return (
    <div className='w-full'>
      {renderCombobox(
        selectedValue,
        (nextValue) => {
          if (!isExternallyControlled) {
            setInternalValue(nextValue);
          }
          onChange?.(nextValue);
        },
        errors || undefined,
      )}
    </div>
  );
};

export default MaintanceCategoryOptions;
