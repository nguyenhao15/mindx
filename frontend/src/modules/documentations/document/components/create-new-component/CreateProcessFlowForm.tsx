import { Controller, useFormContext } from 'react-hook-form';
import TextInputField from '@/components/input-elements/TextInputField';
import ManualCustomCombobox from '@/components/input-elements/ManualCustomCombobox';
import { useMemo } from 'react';
import { useInitialValue } from '@/modules/core/utils/hook/useInitialValue';

interface CreateProcessFlowFormProps {
  errors: ReturnType<typeof useFormContext>['formState']['errors'];
  isLoading: boolean;
  initialData?: any;
}

const CreateProcessFlowForm = ({
  errors,
  isLoading,
  initialData,
}: CreateProcessFlowFormProps) => {
  const { control, register } = useFormContext();

  const defaultTagItems = useMemo(() => {
    if (initialData?.tagItems) {
      return initialData.tagItems.map((item: any) => ({
        value: item.id,
      }));
    }
    return [];
  }, [initialData?.tagItems]);

  const { tagOptions, tagValueOptions } = useInitialValue();

  console.log('Tag value options: ', tagValueOptions);

  return (
    <div className='@container w-full '>
      <div className='grid grid-cols-1 @lg:grid-cols-2 gap-4 p-5'>
        <Controller
          name='tagItems'
          control={control}
          render={({ field }) => (
            <ManualCustomCombobox
              {...field}
              label='Tag'
              id='tagItems'
              multiple
              isLoading={isLoading}
              errors={errors['tagItems']?.message}
              required
              options={tagOptions}
              defaultValue={defaultTagItems}
              updateType={{ type: 'object' }}
              placeholder='Select tag ID'
            />
          )}
        />
        <Controller
          name='tagIdValues'
          control={control}
          render={({ field }) => (
            <ManualCustomCombobox
              label='Tag Value'
              id='tagIdValues'
              {...field}
              multiple
              isLoading={isLoading}
              options={tagValueOptions}
              errors={errors['tagIdValues']?.message}
              required
              defaultValue={initialData?.tagIdValues || []}
              updateType={{ type: 'object' }}
              placeholder='Select tag value'
            />
          )}
        />

        <TextInputField
          id='title'
          type='textarea'
          register={register}
          label='Process Title'
          placeholder='Enter process title'
          required
          rows={4}
          errors={errors}
        />

        <TextInputField
          type='textarea'
          id='description'
          register={register}
          label='Process Description'
          placeholder='Enter process description'
          required
          errors={errors}
        />
      </div>
    </div>
  );
};

export default CreateProcessFlowForm;
