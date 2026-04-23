import { ComboboxComponent } from '@/components/input-elements/ComboboxComponent';
import React, { useMemo } from 'react';
import { useGetStaffProfileByDepartment } from '../hooks/useStaffProfileHook';

interface LoadUserListByDepartmentControlProps {
  departmentId: string;
  errors: any;
  label: string;
  value: any;
  onChange?: (value: any) => void;
  isLoading?: boolean;
  isDisabled?: boolean;
  isMultiple?: boolean;
}

const LoadUserListByDepartmentControl = ({
  departmentId,
  errors,
  label,
  value,
  isLoading,
  onChange,
  isDisabled,
  isMultiple = false,
}: LoadUserListByDepartmentControlProps) => {
  const { data, isLoading: isDataLoading } =
    useGetStaffProfileByDepartment(departmentId);

  const handleOptions = useMemo(() => {
    if (!data) return [];
    return data.map((item: any) => ({
      label: item.staffId,
      value: item.staffId,
    }));
  }, [data]);

  return (
    <ComboboxComponent
      label={label}
      defaultValue={value}
      onChange={onChange}
      options={handleOptions}
      isLoading={isLoading || isDataLoading}
      errors={errors}
      isMultiple={isMultiple}
      disabled={isDisabled}
    />
  );
};

export default LoadUserListByDepartmentControl;
