import TextEditorMasterForm from '../textEditorForm/TextEditorMasterForm';

import { useUpdateProcessFlow } from '@/hookQueries/useProcessFlowHooks';
import { useMemo, useState } from 'react';
import toast from 'react-hot-toast';
import { Button } from '../ui/button';
import { FormProvider, useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { ProcessFlowSchema } from '@/validations/processFlowSchema';
import CreateProcessFlowForm from '../create-new-component/CreateProcessFlowForm';
import AccessRuleFormComponent from '../create-new-component/AccessRuleFormComponent';
import { File } from 'lucide-react';
import AttachmentListManager, {
  type AttachmentListItem,
} from '@/modules/core/attachments/components/AttachmentListManager';

interface ProcessFlowEditProps {
  id: string;
  data: any;
  refetch: () => void;
  setEditMode?: (value: boolean) => void;
}

const ProcessFlowEdit = ({
  id,
  data,
  refetch,
  setEditMode,
}: ProcessFlowEditProps) => {
  const [attachements, setAttachments] = useState<any[]>([]);
  const [processFlowContent, setProcessFlowContent] = useState<any>(null);
  const { mutateAsync: update, isPending: isUpdating } = useUpdateProcessFlow(
    data?.processFlowDto?.id || '',
  );

  const methods = useForm({
    mode: 'onBlur',
    resolver: zodResolver(ProcessFlowSchema),
    defaultValues: {
      title: data?.processFlowDto?.title || '',
      description: data?.processFlowDto?.description || '',
      tagItems:
        data?.processFlowDto?.tagItems.map((item: any) => ({
          value: item.id,
          ...item,
        })) || [],
      tagIdValues:
        data?.processFlowDto?.tagIdValues.map((item: any) => ({
          value: item.id,
          ...item,
        })) || [],
      accessRule: data?.processFlowDto?.accessRule || {},
    },
  });

  const {
    handleSubmit,
    getValues,
    formState: { errors },
  } = methods;

  const initialAttachments = useMemo<AttachmentListItem[]>(() => {
    return (data?.attachments || []).map((item: any, index: number) => ({
      id: item?.id || `${item?.fileName || 'attachment'}-${index}`,
      documentName:
        item?.fileName?.split('/').pop() || item?.fileName || 'Untitled',
      fileUrl: item?.fileUrl,
      fileSize:
        typeof item?.fileSize === 'object'
          ? Number(item?.fileSize?.$numberLong || 0)
          : Number(item?.fileSize || 0),
      ...item,
    }));
  }, [data?.attachments]);

  const handleUpdateContent = (value: any): void => {
    setProcessFlowContent(value);
  };

  const handleUpdate = async () => {
    if (!processFlowContent) {
      toast.error('Vui lòng nhập nội dung quy trình trước khi lưu!');
      return;
    }

    const updateDataValue = getValues();

    const formData = new FormData();

    const attachmentsToUpload = attachements
      .filter((item) => item.file && item.file instanceof File)
      .map((item) => item.file as File);

    attachmentsToUpload.length > 0 &&
      attachmentsToUpload.forEach((item) => {
        formData.append('files', item);
      });

    const updatedAttachments = attachements
      .filter((item) => !item.file && item.isDeleted)
      .map((item) => ({
        fileUrl: item.fileUrl,
        isDeleted: true,
      }));

    processFlowContent.attachments.push(...updatedAttachments);

    const processFlowContentToSend = {
      ...processFlowContent,
      content: processFlowContent.content,
      processFlowId: id,
    };

    const dataToSend = {
      ...updateDataValue,
      processContent: processFlowContentToSend,
    };

    const jsonBlob = new Blob([JSON.stringify(dataToSend)], {
      type: 'application/json',
    });

    formData.append('data', jsonBlob);

    try {
      await update(formData);
      setEditMode && setEditMode(false);
      refetch();
      toast.success('Cập nhật quy trình thành công!');
    } catch (err) {
      console.error('Failed to update process flow:', err);
    }
  };

  return (
    <div className='flex flex-col gap-2'>
      <div className='self-start w-full flex-row gap-2 flex justify-end mb-4'>
        <Button
          className='self-center cursor-pointer'
          variant={'secondary'}
          onClick={() => setEditMode && setEditMode(false)}
        >
          Xem
        </Button>

        <Button
          onClick={handleUpdate}
          className='self-center cursor-pointer'
          variant={'positive'}
        >
          Lưu
        </Button>
      </div>

      <div className='flex flex-col gap-2 mb-4'>
        <div className='mb-4 p-4 rounded bg-yellow-50 text-yellow-800'>
          Bạn đang ở chế độ chỉnh sửa. Hãy chắc chắn lưu lại các thay đổi sau
          khi hoàn tất.
        </div>
      </div>

      <div className='flex flex-row gap-2 items-start'>
        <div className='flex-1'>
          <TextEditorMasterForm
            mode='update'
            isLoading={isUpdating}
            updateAction={handleUpdateContent}
            content={data?.processContent?.content}
          />
        </div>

        <div className='@container w-full flex flex-col gap-2 p-5 min-w-96 px-16'>
          <div className='bg-white rounded-lg shadow p-5'>
            <FormProvider {...methods}>
              <form onSubmit={handleSubmit(handleUpdate)}>
                <CreateProcessFlowForm
                  initialData={data?.processFlowDto}
                  isLoading={isUpdating}
                  errors={errors}
                />
                <AccessRuleFormComponent initialData={data?.processFlowDto} />
              </form>
            </FormProvider>
          </div>
          <AttachmentListManager
            onAttachments={
              attachements.length > 0 ? attachements : initialAttachments
            }
            onAttachmentsChange={setAttachments}
            supportedFileTypes={['pdf']}
          />
        </div>
      </div>
    </div>
  );
};

export default ProcessFlowEdit;
