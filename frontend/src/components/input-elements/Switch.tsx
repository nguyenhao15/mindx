import { motion } from 'framer-motion';

interface SwitchProps {
  checked: boolean;
  onChange: (checked: boolean) => void;
  label?: string;
  disabled?: boolean;
  id?: string;
}
export function Switch({
  checked,
  onChange,
  label,
  id,
  disabled,
  ...rest
}: SwitchProps) {
  return (
    <div className='flex items-center gap-3'>
      <button
        type='button'
        id={id}
        disabled={disabled}
        role='switch'
        aria-checked={checked}
        onClick={() => onChange(!checked)}
        className={`relative inline-flex h-6 w-11 shrink-0 cursor-pointer rounded-full border-2 border-transparent transition-colors duration-200 ease-in-out focus:outline-none focus:ring-2 focus:ring-[#e31f20]/20 focus:border-[#e31f20] ${checked ? 'bg-brand-primary' : 'bg-gray-300'}`}
        {...rest}
      >
        <span className='sr-only'>Use setting</span>
        <motion.span
          layout
          initial={false}
          animate={{
            x: checked ? 20 : 0,
          }}
          transition={{
            type: 'spring',
            stiffness: 500,
            damping: 30,
          }}
          className='pointer-events-none inline-block h-5 w-5 transform rounded-full bg-white shadow ring-0'
        />
      </button>
      {label && (
        <label
          htmlFor={id}
          className='text-sm font-medium text-gray-700 cursor-pointer'
          onClick={() => onChange(!checked)}
        >
          {label}
        </label>
      )}
    </div>
  );
}
