import {
  tagValueSchema,
  type TagValueDTO,
} from '@/modules/documentations/tag/schema/tagValueSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { Controller, FormProvider, useForm } from 'react-hook-form';
import TextInputField from '@/components/input-elements/TextInputField';
import ManualCustomCombobox from '@/components/input-elements/ManualCustomCombobox';
import { Button } from '@/components/ui/button';
import {
  useCreateTagValue,
  useUpdateTagValue,
} from '@/modules/documentations/tag/hooks/useProcessTagValueHooks';
import toast from 'react-hot-toast';
import RadioInputField from '@/components/shared/RadioInputField';
import { useInitialValue } from '@/modules/core/utils/hook/useInitialValue';

const CreateTagValueForm = ({
  updateMode = false,
  onSubmitAction,
  initialData,
}: {
  updateMode?: boolean;
  onSubmitAction: (data: TagValueDTO) => void;
  initialData?: TagValueDTO | TagValue;
}) => {
  const methods = useForm<TagValueDTO>({
    mode: 'onBlur',
    resolver: zodResolver(tagValueSchema),
    defaultValues: initialData,
  });
  const {
    handleSubmit,
    register,
    reset,
    control,
    formState: { errors },
  } = methods;

  const { tagOptions } = useInitialValue();

  const { mutateAsync: createTagValue, isPending: isCreating } =
    useCreateTagValue();

  const { mutateAsync: updateTagValue, isPending: isUpdating } =
    useUpdateTagValue(initialData?.id ?? '');

  const onSubmit = async (data: TagValueDTO) => {
    if (updateMode) {
      await updateTagValue(data, {
        onSuccess: () => {
          onSubmitAction(data);
          toast.success('Tag value updated successfully');
          reset();
        },
      });
    } else {
      createTagValue(data, {
        onSuccess: () => {
          onSubmitAction(data);
          toast.success('Tag value created successfully');
          reset();
        },
      });
    }
  };

  return (
    <div>
      <FormProvider {...methods}>
        <form action='' onSubmit={handleSubmit(onSubmit)}>
          <div className='flex flex-col gap-5'>
            <TextInputField
              label='Tag Title'
              id='tagTitle'
              type='text'
              register={register}
              errors={errors}
              isLoading={isCreating || isUpdating}
              required
              placeholder='Enter tag title'
            />
            <TextInputField
              label='Tag Value Code'
              id='tagValueCode'
              type='text'
              register={register}
              errors={errors}
              isLoading={isCreating || isUpdating}
              required
              placeholder='Enter tag value code'
            />
            <TextInputField
              label='Description'
              id='description'
              type='textarea'
              register={register}
              errors={errors}
              required
              isLoading={isCreating}
              placeholder='Enter description'
            />
            <Controller
              name='tagItems'
              control={control}
              render={({ field }) => (
                <ManualCustomCombobox
                  label='Tag ID'
                  id='tagItems'
                  {...field}
                  multiple={true}
                  isLoading={isCreating || isUpdating}
                  options={tagOptions}
                  errors={errors['tagItems']?.message}
                  required
                  updateType={{ type: 'object' }}
                  placeholder='Enter tag ID'
                />
              )}
            />
            <Controller
              name='active'
              control={control}
              render={({ field: { onChange, value, ...rest } }) => (
                <RadioInputField
                  label='Active Status'
                  options={[
                    { label: 'Active', value: 'true' },
                    { label: 'Inactive', value: 'false' },
                  ]}
                  value={value}
                  onChange={onChange}
                  {...rest}
                />
              )}
            />
            <Button
              disabled={isCreating || isUpdating}
              type='submit'
              className='cursor-pointer'
              variant='positive'
            >
              {isCreating || isUpdating ? 'Submitting...' : 'Submit'}
            </Button>
          </div>
        </form>
      </FormProvider>
    </div>
  );
};

export default CreateTagValueForm;
