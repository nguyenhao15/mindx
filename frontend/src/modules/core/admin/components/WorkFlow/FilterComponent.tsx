import type {
  FilterConfig,
  FilterInput,
} from '@/validations/filterWithPagination';
import { FormProvider, useForm } from 'react-hook-form';
import RenderFilterControlType from './RenderFilterControlType';
import { Button } from '@/components/ui/button';

interface Props {
  configs: FilterConfig[];
  onFilterChange: (value: FilterInput[]) => void;
}

const FilterComponent = ({ configs, onFilterChange }: Props) => {
  const methods = useForm();
  const { control, register, reset } = methods;

  const onSubmit = (data: Record<string, any>) => {
    const payload = configs
      .filter(
        (config) =>
          data[config.field] !== undefined &&
          data[config.field] !== '' &&
          data[config.field] !== null,
      )
      .map((config) => ({
        field: config.field,
        operator: config.operator,
        value: data[config.field],
      }));

    onFilterChange(payload);
  };

  const handleReset = () => {
    reset();
    onFilterChange([]); // Gửi mảng rỗng để xóa tất cả filter
  };

  return (
    <div className='w-full rounded-2xl border border-gray-200 bg-white p-10 shadow-sm'>
      <FormProvider {...methods}>
        <form onSubmit={methods.handleSubmit(onSubmit)}>
          <div className='mb-4 grid grid-cols-1 gap-3 md:grid-cols-2 xl:grid-cols-5'>
            {configs.map((config) => (
              <div key={config.field} className='min-w-0 max-w-full'>
                <RenderFilterControlType
                  config={config}
                  control={control}
                  register={register}
                />
              </div>
            ))}
          </div>
          <div className='flex justify-end'>
            <Button
              variant='secondary'
              type='button'
              onClick={handleReset}
              className='mr-2 cursor-pointer'
            >
              Reset
            </Button>
            <Button variant='positive' type='submit' className='cursor-pointer'>
              Apply Filters
            </Button>
          </div>
        </form>
      </FormProvider>
    </div>
  );
};

export default FilterComponent;
