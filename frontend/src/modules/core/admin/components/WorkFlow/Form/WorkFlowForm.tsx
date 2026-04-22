import React from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import WorkFlowFormElements from './WorkFlowFormElements';

const WorkFlowForm = () => {
  const methods = useForm({
    mode: 'onBlur',
  });

  const {
    handleSubmit,
    control,
    register,
    formState: { errors },
  } = methods;

  return (
    <div>
      <FormProvider {...methods}>
        <form onSubmit={handleSubmit((data) => console.log(data))}>
          <div className='space-y-6'>
            <WorkFlowFormElements
              control={control}
              register={register}
              errors={errors}
            />
          </div>
          <button type='submit'>Submit</button>
        </form>
      </FormProvider>
    </div>
  );
};

export default WorkFlowForm;
