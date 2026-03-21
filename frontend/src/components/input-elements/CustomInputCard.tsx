import React from 'react';
import { Field, FieldDescription, FieldLabel } from '../ui/field';

interface CustomInputCardProps {
  label: string;
  className?: string;
  children?: React.ReactNode;
  required?: boolean;
  description: string | null;
  errorMessage?: string | null;
}

const CustomInputCard = ({
  label,
  className,
  children,
  required = false,
  description,
  errorMessage,
}: CustomInputCardProps) => {
  return (
    <Field className={`text-sm font-bold text-gray-700 gap-1 ${className}`}>
      <FieldLabel className='text-sm font-bold text-gray-700 '>
        {label}
        {required && <span className='m-1 text-red-500'>*</span>}
      </FieldLabel>
      {children}
      {description && <FieldDescription>{description}</FieldDescription>}
      {errorMessage && (
        <FieldDescription className='text-sm font-semibold text-red-600 animate-pulse'>
          {errorMessage}
        </FieldDescription>
      )}
    </Field>
  );
};

export default CustomInputCard;
