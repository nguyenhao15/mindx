import React, { useMemo } from 'react';
import { useGetActiveDepartments } from '../hooks/useDepartmentHook';
import { ComboboxComponent } from '@/components/input-elements/ComboboxComponent';
import { BsSuitcase } from 'react-icons/bs';

interface DepartmentOptionsControlProps {
  id: string;
  onChange: (value: any) => void;
  value: any;
  required?: boolean;
  disabled?: boolean;
  errorMessage?: string | null;
  label: string;
  placeholder?: string;
  isLoading?: boolean;
}

const DepartmentOptionsControl = ({
  id,
  onChange,
  value,
  isLoading = false,
  required = true,
  disabled = false,
  errorMessage,
  label,
  placeholder,
  ...props
}: DepartmentOptionsControlProps) => {
  const { data, isLoading: isLoadingData } = useGetActiveDepartments();

  const departmentOptions = useMemo(() => {
    if (!data) return [];

    return data?.map((item: any) => ({
      label: item?.departmentName,
      value: item?.departmentCode,
      ...item,
    }));
  }, [data]);

  const handleValueChange = (nextValue: any) => {
    onChange(nextValue);
  };

  return (
    <ComboboxComponent
      {...props}
      IconNode={BsSuitcase}
      props={props}
      isLoading={isLoading || isLoadingData}
      required={required}
      disabled={disabled}
      errors={errorMessage || null}
      options={departmentOptions}
      onChange={handleValueChange}
      defaultValue={value ?? null}
      label={label}
      placeholder={placeholder}
    />
  );
};

export default DepartmentOptionsControl;
