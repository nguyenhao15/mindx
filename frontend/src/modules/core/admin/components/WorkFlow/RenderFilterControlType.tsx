import { ComboboxComponent } from '@/components/input-elements/ComboboxComponent';
import DatePickerComponent from '@/components/input-elements/DatePickerComponent';
import TextInputField from '@/components/input-elements/TextInputField';
import type { FilterConfig } from '@/validations/filterWithPagination';
import { Controller } from 'react-hook-form';

interface RenderFilterControlTypeProps {
  config: FilterConfig;
  control?: any; // Nếu cần dùng với react-hook-form
  register?: any; // Nếu cần dùng với react-hook-form
}

const RenderFilterControlType = ({
  config,
  control,
  register,
}: RenderFilterControlTypeProps) => {
  switch (config.type) {
    case 'TEXT':
      return (
        <TextInputField
          labelSize='lg'
          id={config.field}
          label={config.label}
          register={register}
          control={control}
          placeholder={`Enter ${config.label.toLowerCase()}...`}
          type={config.typeInput}
        />
      );
    case 'SELECT':
      // Render control cho SELECT, có thể là dropdown hoặc multi-select tùy isMultiple
      return (
        <Controller
          name={config.field}
          control={control}
          render={({ field: { onChange, value, ...rest } }) => (
            <ComboboxComponent
              label={config.label}
              {...rest}
              defaultValue={value}
              onChange={onChange}
              placeholder={`Select ${config.label.toLowerCase()}...`}
              options={config.options || []}
              isMultiple={config.isMultiple}
            />
          )}
        />
      );
    case 'DATE':
      // Render control cho DATE, có thể là date picker
      return (
        <Controller
          name={config.field}
          control={control}
          render={({ field: { onChange, value, ...rest } }) => (
            <DatePickerComponent
              label={config.label}
              {...rest}
              value={value}
              onChange={onChange}
            />
          )}
        />
      );
    default:
      return null;
  }
};

export default RenderFilterControlType;
