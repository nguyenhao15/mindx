import { useMemo } from 'react';
import { useGetCanAccessDepartments } from '../../departments/hooks/useDepartmentHook';
import { useGetActivePositions } from '../../positions/hooks/usePositionHook';
import { useGetActiveWorkingFieldList } from '../../workingFields/hooks/useWorkingFieldHook';
import { useActiveBuItems } from '../../basement/hooks/useBasementHook';

export const useDepartmentForForm = (departmentId: string) => {
  const { data: departments, isLoading: isDepartmentsLoading } =
    useGetCanAccessDepartments();
  const { data: positions, isLoading: isPositionsLoading } =
    useGetActivePositions();
  const { data: workingFields, isLoading: isWorkingFieldsLoading } =
    useGetActiveWorkingFieldList();
  const { data: buList, isLoading: isBuListLoading } = useActiveBuItems();

  const departmentOptions = useMemo(() => {
    if (!departments) return [];
    return departments?.map((dept: any) => ({
      label: dept.departmentName,
      value: dept.departmentCode,
      ...dept,
    }));
  }, [departments]);

  const positionOptions = useMemo(() => {
    if (!positions) return [];
    return positions
      ?.filter((pos: any) => pos.departmentCode === departmentId)
      ?.map((pos: any) => ({
        label: pos.positionName,
        value: pos.positionCode,
        ...pos,
      }));
  }, [positions, departmentId]);

  const workingFieldOptions = useMemo(() => {
    if (!workingFields) return [];
    return workingFields?.map((field: any) => ({
      label: field.fieldName,
      value: field.fieldCode,
      ...field,
    }));
  }, [workingFields]);

  const buOptions = useMemo(() => {
    if (!buList) return [];
    return buList?.data?.map((bu: any) => ({
      label: bu.buFullName,
      value: bu.buId,
      ...bu,
    }));
  }, [buList]);

  const loading =
    isDepartmentsLoading ||
    isPositionsLoading ||
    isWorkingFieldsLoading ||
    isBuListLoading;

  return {
    departmentOptions,
    positionOptions,
    workingFieldOptions,
    buOptions,
    isLoading: loading,
  };
};
