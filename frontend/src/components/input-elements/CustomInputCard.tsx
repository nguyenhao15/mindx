import React from 'react';
import { Field, FieldDescription, FieldLabel } from '../ui/field';

interface CustomInputCardProps {
  label: string;
  className?: string;
  labelSize?: 'sm' | 'md' | 'lg';
  children?: React.ReactNode;
  required?: boolean;
  description?: string | null;
  errorMessage?: string | null;
  disabled?: boolean;
}

const CustomInputCard = ({
  label,
  className,
  children,
  labelSize = 'md',
  required = false,
  description,
  errorMessage,
  disabled = false,
  ...props
}: CustomInputCardProps) => {
  return (
    <Field
      {...props}
      className={`min-w-full text-sm font-bold overflow-x-visible text-gray-700 gap-1  ${className}`}
    >
      <FieldLabel
        className={`text-${labelSize} font-bold ${disabled ? 'text-gray-400' : 'text-gray-700'}`}
      >
        {label}
        {required && <span className='m-1 text-red-500'>*</span>}
      </FieldLabel>
      {children}
      {description && <FieldDescription>{description}</FieldDescription>}
      {errorMessage && (
        <FieldDescription className='w-full min-w-full whitespace-normal wrap-break-word text-sm font-semibold text-red-600 animate-pulse'>
          {errorMessage}
        </FieldDescription>
      )}
    </Field>
  );
};

export default CustomInputCard;
