import { NumericFormat } from 'react-number-format';

interface BaseNumericFormatProps {
  value: number | string;
  onChange: (value: number) => void;
  onBlur?: () => void;
  min?: number;
  max?: number;
  className?: string;
  isLoading?: boolean;
  placeholder?: string;
}

const BaseNumericFormat = ({
  value,
  onChange,
  onBlur,
  isLoading = false,
  min,
  max,
  className,
  placeholder,
  ...props
}: BaseNumericFormatProps) => {
  return (
    <NumericFormat
      value={value}
      onValueChange={(values) => {
        onChange(values.floatValue ?? 0);
      }}
      thousandSeparator={true}
      decimalSeparator='.'
      onBlur={onBlur}
      allowNegative={false}
      className={className}
      {...props}
      min={min}
      placeholder={placeholder}
      disabled={isLoading}
      max={max}
    />
  );
};

export default BaseNumericFormat;
