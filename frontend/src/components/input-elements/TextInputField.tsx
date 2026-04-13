import clsx from 'clsx'; // Thư viện nhỏ gọn để merge class conditional
import SmartNumericFormat from './SmartNumericFormat';

import { Textarea } from '../ui/textarea';
import CustomInputCard from './CustomInputCard';

interface TextInputFieldProps {
  label?: string;
  id: string;
  type?: 'text' | 'email' | 'url' | 'number' | 'textarea' | 'password';
  errors?: Record<string, any>;
  required?: boolean;
  register?: any; // Tùy chỉnh kiểu nếu bạn dùng react-hook-form
  control?: any; // Tùy chỉnh kiểu nếu bạn dùng react-hook-form
  message?: string;
  defaultValue?: string | number;
  className?: string;
  max?: number;
  min?: number;
  refValue?: string | number;
  disabled?: boolean;
  labelSize?: 'sm' | 'md' | 'lg'; // Thêm prop labelSize để điều chỉnh kích thước label
  placeholder?: string;
  rows?: number; // Dành cho textarea
  isLoading?: boolean; // Thêm prop isLoading để kiểm soát trạng thái loading
}

const TextInputField = ({
  label,
  id,
  type = 'text',
  errors,
  required,
  register,
  defaultValue,
  control,
  message,
  className,
  max,
  min,
  refValue,
  disabled,
  labelSize,
  placeholder,
  rows,
  isLoading = false, // Mặc định không loading
  ...rest
}: TextInputFieldProps) => {
  // 1. Safe access error message
  const errorMessage = errors?.[id]?.message;
  const isError = !!errorMessage;

  // 2. Define Validation Rules (Clean Code: Tách logic rule ra cho dễ đọc)
  const validationRules = {
    required: { value: required, message: message || 'This field is required' },
    minLength:
      type !== 'number' && min !== undefined
        ? { value: min, message: `Minimum ${min} characters required` }
        : undefined,
    maxLength:
      type !== 'number' && max !== undefined
        ? { value: max, message: `Maximum ${max} characters required` }
        : undefined,
    min:
      type === 'number' && min !== undefined
        ? { value: min, message: `Giá trị tối thiểu là ${min}` }
        : undefined,
    max:
      type === 'number' && max !== undefined
        ? { value: max, message: `Giá trị tối đa là ${max}` }
        : undefined,

    pattern:
      type === 'email'
        ? {
            value: /^[a-zA-Z0-9]+@(?:[a-zA-Z0-9]+\.)+com+$/,
            message: 'Invalid email address',
          }
        : type === 'url'
          ? {
              value:
                /^(https?:\/\/)?(([a-zA-Z0-9\u00a1-\uffff-]+\.)+[a-zA-Z\u00a1-\uffff]{2,})(:\d{2,5})?(\/[^\s]*)?$/,
              message: 'Please enter a valid URL',
            }
          : undefined,
  };

  const styleInput = clsx(
    'px-3 py-4 mt-2 text-sm rounded border outline-none shadow-sm transition-colors',
    'bg-transparent text-slate-800 placeholder:text-gray-400',
    'hover:bg-gray-50 focus:ring-2 focus:ring-offset-1 focus:ring-blue-200',
    isError
      ? 'border-red-500 border-2 focus:border-red-500 focus:ring-red-200'
      : 'border-slate-300 focus:border-blue-500 focus:ring-blue-200',
    className,
  );

  // 3. Render View Mode (Read-only)
  if (disabled) {
    return (
      <div className='flex flex-col gap-1 w-full'>
        <span className='font-semibold text-xl text-slate-800'>{label}</span>
        <div className='px-1 py-2 text-lg text-slate-600 bg-gray-50 rounded border border-transparent'>
          {refValue || '-'}
        </div>
      </div>
    );
  }

  return (
    <CustomInputCard
      label={label || ''}
      required={required}
      errorMessage={isError ? errorMessage : null}
      description={''}
      labelSize={labelSize || 'md'}
      {...rest}
    >
      {type === 'number' ? (
        <SmartNumericFormat
          {...rest}
          name={id}
          control={control ? control : undefined}
          rules={validationRules}
          styleInput={styleInput}
          max={max ?? undefined}
          min={min ?? undefined}
          isLoading={isLoading}
          defaultValue={defaultValue}
          placeholder={placeholder}
        />
      ) : type === 'textarea' ? (
        <Textarea
          {...(register && { ...register(id, validationRules) })}
          id={id}
          placeholder={placeholder || 'Enter text'}
          maxLength={max ?? undefined}
          minLength={min ?? undefined}
          aria-invalid={isError}
          value={defaultValue}
          rows={rows || 1}
          className={clsx(
            'p-2 bg-transparent text-slate-800 placeholder:text-gray-400 rounded border outline-none shadow-sm transition-colors',
            isLoading && 'opacity-50 cursor-not-allowed',
            isError
              ? 'border-red-500 border-2 focus:border-red-500 focus:ring-red-200'
              : 'border-slate-300 focus:border-blue-500 focus:ring-blue-200',
            className,
          )}
          disabled={isLoading}
          {...rest}
        />
      ) : (
        <input
          {...(register && { ...register(id, validationRules) })}
          id={id}
          type={type}
          placeholder={placeholder || 'Enter text'}
          min={min ?? undefined}
          max={max ?? undefined}
          maxLength={max ?? undefined}
          minLength={min ?? undefined}
          className={styleInput}
          {...rest}
          disabled={isLoading}
          value={defaultValue}
        />
      )}
    </CustomInputCard>
  );
};

export default TextInputField;
