import { useState } from 'react';
import {
  SimpleEditor,
  type UploadedFileItem,
} from '../tiptap-templates/simple/simple-editor';
import { Button } from '../ui/button';
import processTemplate from '@/components/tiptap-templates/simple/data/processTemplate.json';

const TextEditorMasterForm = ({
  submitAction,
  isLoading,
  updateAction,
  mode = 'create',
  content = processTemplate,
}: {
  submitAction: () => Promise<void>;
  isLoading: boolean;
  updateAction?: (data: any) => Promise<void>;
  mode?: 'create' | 'update';
  content?: any;
}) => {
  const [uploadedFiles, setUploadedFiles] = useState<UploadedFileItem[]>([]);

  const handleSubmit = async () => {
    await submitAction();
  };

  const handleUpdate = (data: {
    content: any;
    markdown: string;
    attachments: UploadedFileItem[];
  }) => {
    const { markdown, attachments } = data;
    let updatedAttachments;

    updatedAttachments = uploadedFiles.map((item) =>
      !attachments.find((url) => url.fileUrl === item.fileUrl)
        ? {
            ...item,
            isDeleted: true,
          }
        : { ...item },
    );

    if (updatedAttachments.length === 0 && attachments.length > 0) {
      updatedAttachments = attachments.map((it) => ({
        fileUrl: it.fileUrl,
        isDeleted: false,
      }));
    }

    if (updateAction) {
      const data = {
        content: markdown,
        attachments: updatedAttachments || attachments,
      };
      updateAction(data);
    }
  };

  return (
    <div className='max-w-5xl mx-auto p-8'>
      <h1 className='text-3xl font-bold mb-6'>
        {mode === 'create' ? 'Create Process' : 'Update Process'}
      </h1>
      <SimpleEditor
        onContentChange={handleUpdate}
        onUploadedFilesChange={setUploadedFiles}
        content={content as any}
      />
      {mode === 'create' && (
        <Button
          variant='default'
          size='default'
          disabled={isLoading}
          className='cursor-pointer'
          onClick={handleSubmit}
        >
          {isLoading
            ? 'Saving...'
            : mode === 'create'
              ? 'Save the process'
              : 'Update the process'}
        </Button>
      )}
    </div>
  );
};

export default TextEditorMasterForm;
