import React, { useMemo } from 'react';
import {
  useGetPositionByDepartment,
  useGetPositions,
} from '../hooks/usePositionHook';
import { SingleComboboxComponent } from '@/components/input-elements/ComboboxComponent';

interface PositionComponentOptionControlProps {
  id: string;
  onChange: (value: any) => void;
  value: any;
  required?: boolean;
  disabled?: boolean;
  errorMessage?: string | null;
  label: string;
  placeholder?: string;
  isLoading?: boolean;
  departmentId: string;
}

const PositionComponentOptionControl = ({
  departmentId,
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
}: PositionComponentOptionControlProps) => {
  const { data, isLoading: isLoadingData } =
    useGetPositionByDepartment(departmentId);

  const positionOptions = useMemo(() => {
    if (!data) return [];

    return data?.map((item: any) => ({
      label: item?.positionName,
      value: item?.positionCode,
      ...item,
    }));
  }, [data]);

  const handleValueChange = (nextValue: any) => {
    onChange(nextValue);
  };
  return (
    <SingleComboboxComponent
      {...props}
      props={props}
      isLoading={isLoading || isLoadingData}
      required={required}
      disabled={disabled}
      errors={errorMessage || null}
      options={positionOptions}
      onChange={handleValueChange}
      defaultValue={value ?? null}
      label={label}
      placeholder={placeholder}
    />
  );
};

export default PositionComponentOptionControl;
