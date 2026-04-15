import { Check, Circle } from 'lucide-react';

interface Step {
  key: string;
  label: string;
}

interface ProgressStepperProps {
  steps: Step[];
  currentStep: number;
}

const ProgressStepper = ({ steps, currentStep }: ProgressStepperProps) => {
  return (
    <section className='w-full bg-white rounded-2xl border border-slate-100 p-5 sm:p-6'>
      <h2 className='text-base font-semibold text-slate-800'>Tiến Độ Xử Lý</h2>
      <div className='mt-5 grid grid-cols-1 gap-4 md:grid-cols-4'>
        {steps.map((step, index) => {
          const isDone = index < currentStep;
          const isCurrent = index === currentStep;

          return (
            <div key={step.key} className='relative'>
              <div className='flex items-start gap-3'>
                <span
                  className={[
                    'mt-0.5 flex self-center h-7 w-7 items-center justify-center rounded-full border text-xs font-semibold',
                    isDone
                      ? 'bg-emerald-500 border-emerald-500 text-white'
                      : isCurrent
                        ? 'bg-[#1d3557] border-[#1d3557] text-white'
                        : 'bg-slate-100 border-slate-200 text-slate-500',
                  ].join(' ')}
                >
                  {isDone ? <Check size={14} /> : <Circle size={12} />}
                </span>
                <div>
                  <p className='text-sm mb-2 font-semibold text-slate-800'>
                    {step.label}
                  </p>
                  <p className='text-xs text-slate-500 mt-0.5'>
                    {isDone
                      ? 'Đã hoàn tất'
                      : isCurrent
                        ? 'Đang thực hiện'
                        : 'Chờ xử lý'}
                  </p>
                </div>
              </div>
              {index < steps.length - 1 && (
                <div className='hidden md:block my-2 absolute top-3.5 left-8 right-0 h-0.5 bg-slate-100' />
              )}
            </div>
          );
        })}
      </div>
    </section>
  );
};

export default ProgressStepper;
