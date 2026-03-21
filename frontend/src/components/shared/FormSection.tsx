import React from 'react';

interface FormSectionProps {
  headerLabel: string;
  icon: React.ElementType;
  children: React.ReactNode;
}

const FormSection = ({
  children,
  headerLabel,
  icon: Icon,
}: FormSectionProps) => {
  return (
    <section className='flex flex-1 flex-col gap-3 shadow-lg rounded-2xl py-6 px-9 bg-white '>
      <div className=' border-b border-slate-100 pb-2 flex items-center my-auto gap-2 h-fit'>
        <Icon size={20} className={`text-brand-primary`} />
        <h3 className='text-lg font-bold text-slate-900 h-full items-center my-auto'>
          {headerLabel}
        </h3>
      </div>
      <div className='flex-1'>{children}</div>
    </section>
  );
};

export default FormSection;
