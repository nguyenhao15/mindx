import {
  Radio,
  RadioGroup,
  Field,
  Label,
  Description,
} from '@headlessui/react';
import { Controller } from 'react-hook-form';
import { motion } from 'framer-motion';
import clsx from 'clsx';

const RadioInputField = ({
  label,
  name,
  options = [],
  disabled = false,
  control,
  value: externalValue,
  onChange: externalOnChange,
  error,
  required = false,
  className,
}: {
  label?: string;
  name?: string;
  options: { label: string; value: string }[];
  control?: any; // React Hook Form control
  value?: string; // For external state management
  onChange?: (value: string) => void; // For external state management
  error?: string | { message: string };
  required?: boolean;
  disabled?: boolean;
  className?: string;
}) => {
  const normalizedExternalValue = externalValue ?? '';
  const normalizedExternalOnChange = externalOnChange ?? (() => {});

  const renderRadioGroup = (
    currentValue: string | undefined,
    handleChange: (value: string) => void,
  ) => (
    <Field className={clsx('flex flex-col gap-3', className)}>
      {label && (
        <Label className='text-xl font-semibold text-slate-800'>
          {label} {required && <span className='text-red-500'>*</span>}
        </Label>
      )}

      <RadioGroup
        value={currentValue ?? ''}
        onChange={handleChange}
        disabled={disabled}
        className='flex flex-wrap gap-4'
      >
        {options.map((option) => (
          <Radio
            key={option.value}
            value={option.value}
            className={({ checked }) =>
              clsx(
                'relative flex cursor-pointer rounded-lg border px-5 py-3 shadow-sm focus:outline-none transition-all',
                checked
                  ? 'border-blue-600 bg-blue-50 ring-1 ring-blue-600'
                  : 'border-slate-300 bg-white hover:bg-slate-50',
              )
            }
          >
            {({ checked }) => (
              <div className='flex items-center gap-3'>
                {/* Custom Radio Circle */}
                <div
                  className={clsx(
                    'flex h-5 w-5 items-center justify-center rounded-full border',
                    checked ? 'border-blue-600' : 'border-slate-400',
                  )}
                >
                  {checked && (
                    <motion.div
                      initial={{ scale: 0 }}
                      animate={{ scale: 1 }}
                      className='h-2.5 w-2.5 rounded-full bg-blue-600'
                    />
                  )}
                </div>

                <span
                  className={clsx(
                    'text-lg font-medium',
                    checked ? 'text-blue-900' : 'text-slate-700',
                  )}
                >
                  {option.label}
                </span>
              </div>
            )}
          </Radio>
        ))}
      </RadioGroup>

      {/* Hiển thị lỗi chuẩn Enterprise */}
      {error && (
        <Description className='text-sm font-semibold text-red-600 animate-pulse'>
          {typeof error === 'string' ? error : error.message}
        </Description>
      )}
    </Field>
  );

  // Trường hợp dùng với React Hook Form
  if (control && name) {
    return (
      <Controller
        name={name}
        control={control}
        defaultValue={normalizedExternalValue}
        render={({ field: { value, onChange } }) =>
          renderRadioGroup(value, onChange)
        }
      />
    );
  }

  // Trường hợp dùng độc lập
  return renderRadioGroup(normalizedExternalValue, normalizedExternalOnChange);
};

export default RadioInputField;
