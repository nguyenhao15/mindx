import React from 'react';
import CustomInputCard from './CustomInputCard';
import { Popover, PopoverContent, PopoverTrigger } from '../ui/popover';
import { Button } from '../ui/button';
import { Calendar } from '../ui/calendar';
import { CalendarIcon } from 'lucide-react';

interface DatePickerComponentProps {
  // Define any props you need for the DatePickerComponent
  onChange?: (date: Date) => void;
  value?: Date;
  label?: string;
  required?: boolean;
  errors?: Record<string, any>;
}

const DatePickerComponent = ({
  onChange: onChangeAction,
  value: initialValue,
  label,
  required,
  errors,
  ...rest
}: DatePickerComponentProps) => {
  const { onChange, value, onBlur, ...inputProps } = rest as any;
  const [open, setOpen] = React.useState(false);
  const [date, setDate] = React.useState<Date | undefined>(
    initialValue || value,
  );

  const handleOnSelect = (selectedDate: any) => {
    setDate(selectedDate);
    onChangeAction?.(selectedDate);
    onChange?.(selectedDate);
    setOpen(false);
    onBlur?.();
  };

  const handleOnChange = () => {
    setOpen((prev) => !prev);
    open && onBlur?.();
  };

  return (
    <CustomInputCard
      label={label || 'Ngày'}
      labelSize='lg'
      required={required}
      {...inputProps}
      errorMessage={errors ? errors.message : null}
    >
      <Popover open={open} onOpenChange={handleOnChange}>
        <PopoverTrigger asChild>
          <Button
            variant={errors ? 'invalid' : 'outline'}
            id='date'
            className='flex justify-between gap-2 font-normal bg-input-background h-13 border-2 w-full cursor-pointer '
          >
            <span
              className={`text-md ${date ? 'font-semibold' : 'text-muted-foreground'}`}
            >
              {date ? date.toLocaleDateString() : 'Chọn ngày'}
            </span>
            <CalendarIcon className='text-muted-foreground' />
          </Button>
        </PopoverTrigger>
        <PopoverContent className='w-auto overflow-hidden p-0' align='start'>
          <Calendar
            mode='single'
            selected={date}
            defaultMonth={date}
            captionLayout='dropdown'
            onSelect={(date) => {
              handleOnSelect(date);
            }}
          />
        </PopoverContent>
      </Popover>
    </CustomInputCard>
  );
};

export default DatePickerComponent;
