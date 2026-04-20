import {
  tagSchema,
  type TagDTO,
  type TagInfo,
} from '@/modules/documentations/tag/schema/tagSchema';
import { Controller, FormProvider, useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import TextInputField from '@/components/input-elements/TextInputField';
import { Button } from '@/components/ui/button';
import {
  useCreateProcessTag,
  useUpdateProcessTag,
} from '@/modules/documentations/tag/hooks/useProcessTagHooks';
import toast from 'react-hot-toast';
import RadioInputField from '@/components/shared/RadioInputField';

interface CreateTagFormProps {
  updateMode?: boolean; // Thêm prop để xác định chế độ tạo mới hay cập nhật
  initialData?: TagDTO; // Define the type based on your data structure
  onUpdateSuccess?: () => void; // Callback khi cập nhật thành côngs
}

const CreateTagForm = ({
  updateMode = false,
  initialData,
  onUpdateSuccess,
}: CreateTagFormProps) => {
  const methods = useForm({
    mode: 'onBlur',
    resolver: zodResolver(tagSchema),
    defaultValues: initialData,
  });

  const {
    handleSubmit,
    register,
    reset,
    formState: { errors },
  } = methods;

  const { mutateAsync: createTag, isPending: isCreating } =
    useCreateProcessTag();

  const { mutateAsync: updateTag, isPending: isUpdating } = useUpdateProcessTag(
    initialData?.id ?? '',
  );

  const onSubmit = async (data: TagDTO) => {
    try {
      if (updateMode && initialData) {
        await updateTag({ data });
        toast.success('Cập nhật tag thành công!');
      } else {
        await createTag(data);
        reset(); // Reset form sau khi tạo mới thành công
        toast.success('Tạo tag thành công!');
      }
      onUpdateSuccess && onUpdateSuccess(); // Gọi callback nếu có
    } catch (error) {
      toast.error('Có lỗi xảy ra. Vui lòng thử lại.');
    }
  };

  return (
    <div>
      <FormProvider {...methods}>
        <form action='' onSubmit={handleSubmit(onSubmit)}>
          <div className='flex flex-col gap-5'>
            <TextInputField
              label='Tag Name'
              id='tagName'
              type='text'
              register={register}
              isLoading={isCreating || isUpdating}
              errors={errors}
              required
              placeholder='Enter tag name'
            />
            <TextInputField
              label='Full Tag Name'
              id='fullTagName'
              type='text'
              register={register}
              isLoading={isCreating || isUpdating}
              errors={errors}
              required
              placeholder='Enter full tag name'
            />
            <TextInputField
              label='Description'
              id='description'
              type='textarea'
              rows={4}
              isLoading={isCreating || isUpdating}
              register={register}
              errors={errors}
              required
              placeholder='Enter description'
            />
            <Controller
              name='active'
              control={methods.control}
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
          </div>
          <Button
            disabled={isCreating || isUpdating}
            type='submit'
            className='mt-4 cursor-pointer'
            variant={'positive'}
          >
            Submit
          </Button>
        </form>
      </FormProvider>
    </div>
  );
};

export default CreateTagForm;
