import React, { useMemo } from 'react';
import { useGetWorkflowByModule } from '../../../hooks/useWorkFlowHook';
import { ComboboxComponent } from '@/components/input-elements/ComboboxComponent';

interface WorkFlowComponentControlProps {
  isLoading?: boolean;
  module: string;
  onChange?: (value: any) => void;
  value?: any;
  errors?: string;
}

const WorkFlowComponentControl = ({
  isLoading,
  module,
  errors,
  value,
  onChange,
  ...rest
}: WorkFlowComponentControlProps) => {
  const { data, isLoading: isLoadingData } = useGetWorkflowByModule(module);

  const handleOptions = useMemo(() => {
    if (!data) return [];
    return data.map((item: any) => ({
      label: item.fromStatus + ' to ' + item.toStatus,
      value: item.id,
    }));
  }, [data]);

  return (
    <ComboboxComponent
      {...rest}
      onChange={onChange}
      defaultValue={value}
      disabled={isLoading || isLoadingData}
      label='Workflow Transitions'
      options={handleOptions}
      isLoading={isLoading}
      errors={errors}
    />
  );
};

export default WorkFlowComponentControl;
