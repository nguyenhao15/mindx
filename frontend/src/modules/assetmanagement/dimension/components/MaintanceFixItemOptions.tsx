import { useMemo, useState } from 'react';
import { useGetCategoryOptions } from '../hooks/useCategoryHook';
import { Controller, useFormContext } from 'react-hook-form';
import { SingleComboboxComponent } from '@/components/input-elements/ComboboxComponent';

type ComboboxValue = string | number | null | undefined;

type CategoryItemOption = {
  id: string;
  itemTitle: string;
  description: string;
};

interface MaintanceFixItemOptionsProps {
  label: string;
  categoryId: string;
  name: string;
  placeholder?: string;
  required?: boolean;
  disabled?: boolean;
  value?: ComboboxValue;
  defaultValue?: ComboboxValue;
  errors?: string | null;
  onChange?: (value: ComboboxValue) => void;
}

const MaintanceFixItemOptions = ({
  label,
  categoryId,
  name,
  placeholder,
  required = true,
  disabled = false,
  value,
  defaultValue,
  errors,
  onChange,
}: MaintanceFixItemOptionsProps) => {
  const [internalValue, setInternalValue] =
    useState<ComboboxValue>(defaultValue);
  const { data } = useGetCategoryOptions();
  const { control } = useFormContext();

  const itemOptions = useMemo(() => {
    if (!data) return [];
    const category = (data as any[]).find((cat) => cat.id === categoryId);
    if (!category || !category.maintenanceItems) return [];
    return (category.maintenanceItems as CategoryItemOption[]).map((item) => ({
      label: item.itemTitle,
      value: item.id,
      ...item,
    }));
  }, [data, categoryId]);

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
        options={itemOptions}
        onChange={handleValueChange}
        defaultValue={selectedValue ?? null}
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

export default MaintanceFixItemOptions;
