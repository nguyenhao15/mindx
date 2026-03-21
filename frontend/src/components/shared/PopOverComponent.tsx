import { Button } from '@/components/ui/button';
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from '@/components/ui/popover';

interface PopOverComponentProps {
  children: React.ReactNode;
  buttonLabel: string;
  variant?: 'outline' | 'default' | 'positive';
  Icon?: React.ElementType;
  isOpen: boolean;
  onToggle: () => void;
  isLoading?: boolean;
}

const PopOverComponent = ({
  children,
  buttonLabel,
  variant,
  Icon,
  isOpen,
  onToggle,
  isLoading,
}: PopOverComponentProps) => {
  return (
    <Popover open={isOpen}>
      <PopoverTrigger asChild>
        <Button
          disabled={isLoading}
          variant={variant}
          onClick={onToggle}
          className='cursor-pointer'
        >
          {Icon && <Icon />}
          {buttonLabel}
        </Button>
      </PopoverTrigger>
      <PopoverContent align='start' className='w-full'>
        {children}
      </PopoverContent>
    </Popover>
  );
};

export default PopOverComponent;
