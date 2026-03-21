import { Controller } from 'react-hook-form';
import BaseNumericFormat from './BaseNumericFormat';

interface SmartNumericFormatProps {
  name?: string;
  control?: any;
  rules?: any;
  defaultValue?: number | string;
  value?: number | string;
  onChange?: (value: number) => void;
  min?: number;
  max?: number;
  styleInput?: string;
  isLoading?: boolean;
  placeholder?: string;
}

const SmartNumericFormat = ({
  name,
  control,
  rules,
  defaultValue,
  isLoading = false,
  value,
  onChange,
  min,
  max,
  styleInput,
  placeholder,
  ...rest
}: SmartNumericFormatProps) => {
  if (control && name) {
    return (
      <Controller
        name={name}
        control={control}
        rules={rules}
        defaultValue={defaultValue ?? ''}
        render={({
          field: {
            onChange: rhfOnChange,
            onBlur: rhfOnBlur,
            value: rhfValue,
            ref,
            ...fieldRest
          },
        }) => (
          <BaseNumericFormat
            {...rest}
            value={rhfValue}
            onChange={rhfOnChange}
            onBlur={rhfOnBlur}
            min={min || undefined}
            max={max || undefined}
            getInputRef={ref}
            placeholder={placeholder}
            className={styleInput}
            isLoading={isLoading}
            {...fieldRest}
          />
        )}
      />
    );
  }

  return (
    <BaseNumericFormat
      value={value ?? defaultValue ?? ''}
      onChange={onChange ? onChange : () => {}}
      min={min || undefined}
      max={max || undefined}
      placeholder={placeholder}
      {...rest}
      className={styleInput}
      isLoading={isLoading}
    />
  );
};

export default SmartNumericFormat;
