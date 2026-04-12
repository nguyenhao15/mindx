import { useState } from 'react';
import { ProcessFlowSchema } from '@/modules/documentations/document/schema/processFlowSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { FormProvider, useForm } from 'react-hook-form';
import CreateProcessFlowForm from './CreateProcessFlowForm';
import { useCreateProcessFlow } from '@/modules/documentations/document/hooks/useProcessFlowHooks';
import toast from 'react-hot-toast';
import AccessRuleFormComponent from './AccessRuleFormComponent';
import { Info } from 'lucide-react';
import { FaUserShield } from 'react-icons/fa';
import AttachmentControl from '@/modules/core/attachments/components/AttachmentControlComponent';
import { Button } from '@/components/ui/button';
import TextEditorMasterForm from '@/modules/documentations/document/components/textEditorForm/TextEditorMasterForm';
import FormSection from '@/components/shared/FormSection';
import { DialogComponent } from '@/components/shared/DialogComponent';
import Loader from '@/components/shared/Loader';

const CreateComponent = () => {
  const [step, setStep] = useState(0);
  const [attachedFiles, setAttachedFiles] = useState<File[]>([]);
  const methods = useForm({
    mode: 'onBlur',
    resolver: zodResolver(ProcessFlowSchema),
    defaultValues: {
      title: '',
      description: '',
      processStatus: 'INITIALIZED',
      active: true,
    },
  });

  const { mutateAsync: createProcessFlow, isPending: isLoading } =
    useCreateProcessFlow();

  const {
    handleSubmit,
    reset,
    trigger,
    formState: { errors },
    getValues,
    setValue,
  } = methods;

  const handleNextStep = () => {
    if (step === 0) {
      trigger(['description', 'tagIdValues', 'tagItems', 'title']).then(
        (isValid) => {
          if (!isValid) {
            toast.error('Please fill in all required fields');
            return;
          }
          if (attachedFiles.length === 0) {
            toast.error('Please attach at least one file');
            return;
          }
          setStep((prevStep) => prevStep + 1);
        },
      );
    }
  };

  const handlePreviousStep = () => {
    setStep((prevStep) => prevStep - 1);
  };

  const handleUpdateContent = async (value: any): Promise<void> => {
    setValue('processContent', value);
  };

  const onSubmit = async () => {
    const formValue = getValues();

    const finalData = {
      ...formValue,
    };

    const jsonBlob = new Blob([JSON.stringify(finalData)], {
      type: 'application/json',
    });

    const formData = new FormData();

    formData.append('data', jsonBlob);
    attachedFiles.forEach((file) => {
      formData.append('files', file);
    });

    try {
      await createProcessFlow(formData);
      reset();
      setStep(0);
      setAttachedFiles([]);
      toast.success('Process flow created successfully');
    } catch (error) {
      console.error('Error creating process flow:', error);
      toast.error('Failed to create process flow');
    }
  };

  return (
    <div className='p-5 flex flex-col h-full gap-2'>
      <div className='flex-1'>
        {step === 0 && (
          <div className='flex flex-col gap-2 p-10'>
            <div className='items-center px-2 md:px-5 xl:px-20 justify-center'>
              <FormProvider {...methods}>
                <form action='' onSubmit={handleSubmit(onSubmit)}>
                  <div className='@container flex flex-col gap-2'>
                    <FormSection headerLabel='Create Process Flow' icon={Info}>
                      <CreateProcessFlowForm
                        errors={errors}
                        isLoading={isLoading}
                      />
                    </FormSection>
                    <FormSection headerLabel='Access Rule' icon={FaUserShield}>
                      <AccessRuleFormComponent />
                    </FormSection>
                  </div>
                </form>
              </FormProvider>
            </div>
            <AttachmentControl
              attachedFile={attachedFiles}
              onFileAttach={setAttachedFiles}
              title='Tài liệu đính kèm'
              isMultiFile={true}
              supportedFileTypes={['PDF']}
              maxFileSize={5 * 1024 * 1024} // 5MB
            />
          </div>
        )}
        {step === 1 && (
          <TextEditorMasterForm
            isLoading={isLoading}
            submitAction={handleSubmit(onSubmit)}
            updateAction={handleUpdateContent}
            mode='create'
          />
        )}
      </div>
      <div>
        {step === 0 && (
          <Button
            variant={'positive'}
            className='cursor-pointer self-end'
            onClick={handleNextStep}
          >
            Tiếp tục
          </Button>
        )}
        {step === 1 && (
          <Button
            variant={'positive'}
            type='button'
            className='cursor-pointer self-end'
            onClick={handlePreviousStep}
          >
            Quay lại
          </Button>
        )}
      </div>
      <DialogComponent
        open={isLoading}
        onOpenChange={() => {}}
        description='Vui lòng chờ trong giây lát...'
      >
        <Loader text='Creating process flow...' />
      </DialogComponent>
    </div>
  );
};

export default CreateComponent;
