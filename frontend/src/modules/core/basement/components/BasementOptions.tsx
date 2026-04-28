import { useMemo, useState } from 'react';
import { useActiveBuItems } from '../hooks/useBasementHook';
import { ComboboxComponent } from '@/components/input-elements/ComboboxComponent';
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
  placeholder,
  required = true,
  disabled = false,
  value,
  errors,
  multiple = false,
  isLoading,
  onChange,
  ...props
}: MaintanceCategoryOptionsProps) => {
  const { data, isLoading: isLoadingData } = useActiveBuItems();

  const buOptions = useMemo(() => {
    if (!data) return [];

    return (data as any)?.data.map((item: any) => ({
      label: item?.buFullName,
      value: item?.buId,
    }));
  }, [data]);

  return (
    <ComboboxComponent
      props={props}
      isLoading={isLoading || isLoadingData}
      IconNode={Building}
      required={required}
      limit={multiple ? 2 : undefined}
      disabled={disabled}
      isMultiple={multiple}
      errors={errors || null}
      options={buOptions}
      onChange={onChange}
      defaultValue={value}
      label={label}
      placeholder={placeholder}
      {...props}
    />
  );
};

export default BasementOptions;
