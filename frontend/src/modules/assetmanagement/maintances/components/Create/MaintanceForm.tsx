import React from 'react';
import { useFormContext } from 'react-hook-form';

interface MaintanceFormProps {
  control: any; // Replace 'any' with the appropriate type for your form control
}

const MaintanceForm = ({ control }: MaintanceFormProps) => {
  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
  } = useFormContext();

  return <div>MaintanceForm</div>;
};

export default MaintanceForm;
