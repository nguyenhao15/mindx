import { useMemo, useState } from 'react';
import { useActiveBuItems } from '../hooks/useBasementHook';
import {
  ComboboxComponent,
  MultipleComboboxComponent,
  SingleComboboxComponent,
} from '@/components/input-elements/ComboboxComponent';
import { Controller, useFormContext } from 'react-hook-form';
import { Building } from 'lucide-react';

interface MaintanceCategoryOptionsProps {
  label: string;
  placeholder?: string;
  name: string;
  required?: boolean;
  disabled?: boolean;
  value?: any;
  defaultValue?: any;
  errors?: string | null;
  isLoading?: boolean;
  multiple?: boolean;
  onChange?: (value: any) => void;
}

const BasementOptions = ({
  label,
  name,
  placeholder,
  required = true,
  disabled = false,
  value,
  defaultValue,
  errors,
  multiple = false,
  isLoading,
  onChange,
}: MaintanceCategoryOptionsProps) => {
  const [internalValue, setInternalValue] = useState(defaultValue);
  const { data, isLoading: isLoadingData } = useActiveBuItems();
  const { control } = useFormContext();

  const buOptions = useMemo(() => {
    if (!data) return [];

    return (data as any)?.data.map((item: any) => ({
      label: item?.buFullName,
      value: item?.buId,
      ...item,
    }));
  }, [data]);

  const renderCombobox = (
    selectedValue: any,
    handleValueChange: (nextValue: any) => void,
    errorMessage?: string,
    props?: any,
  ) => {
    return (
      <ComboboxComponent
        isLoading={isLoading || isLoadingData}
        IconNode={Building}
        required={required}
        limit={multiple ? 2 : undefined}
        disabled={disabled}
        isMultiple={multiple}
        errors={errorMessage || null}
        options={buOptions}
        onChange={handleValueChange}
        defaultValue={selectedValue ?? null}
        label={label}
        placeholder={placeholder}
        {...props}
      />
    );
    // if (multiple) {
    //   return (
    //     <MultipleComboboxComponent
    //       isLoading={isLoading || isLoadingData}
    //       {...props}
    //       required={required}
    //       disabled={disabled}
    //       IconNode={Building}
    //       errors={errorMessage || null}
    //       limit={2}
    //       options={buOptions}
    //       onChange={handleValueChange}
    //       defaultValue={selectedValue ?? []}
    //       label={label}
    //       placeholder={placeholder}
    //     />
    //   );
    // }
    // return (
    //   <SingleComboboxComponent
    //     isLoading={isLoading || isLoadingData}
    //     IconNode={Building}
    //     {...props}
    //     required={required}
    //     disabled={disabled}
    //     errors={errorMessage || null}
    //     options={buOptions}
    //     onChange={handleValueChange}
    //     defaultValue={selectedValue ?? null}
    //     label={label}
    //     placeholder={placeholder}
    //   />
    // );
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

export default BasementOptions;
