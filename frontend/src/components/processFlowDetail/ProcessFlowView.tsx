import AttachmentsGallery from '../shared/AttachmentsGallery';
import ReadOnlyEdior from './ReadOnlyEdior';
import AcessDetailComponent from './AcessDetailComponent';
import { Button } from '../ui/button';
import { useNavigate } from 'react-router-dom';
import { useAuthStore } from '@/stores/AuthStore';
import { useActivateProcessFlow } from '@/hookQueries/useProcessFlowHooks';

interface ProcessFlowViewProps {
  data: any;
  isLoading: boolean;
  setEdit: (value: boolean) => void;
  viewMode?: 'admin' | 'user';
}

const ProcessFlowView = ({
  data,
  isLoading,
  setEdit,
  viewMode = 'user',
}: ProcessFlowViewProps) => {
  const navigate = useNavigate();
  const user = useAuthStore((state) => state.user);

  const { mutate: activateProcessFlow, isPending } = useActivateProcessFlow(
    data?.processFlowDto?.id || '',
  );

  const handleEdit = () => {
    setEdit(true);
  };

  const handleOnBack = () => {
    navigate(-1);
  };

  const canEdit =
    user?.systemRole === 'ADMIN' ||
    data?.processFlowDto?.createdBy === user?.staffId;

  const handleActivate = () => {
    activateProcessFlow();
  };

  return (
    <div>
      <div className='flex flex-col gap-4 px-2 md:px-15 lg:px-40 py-4 dark:bg-slate-900 rounded'>
        <div className='self-start w-full flex-row gap-2 flex justify-end mb-4'>
          {viewMode === 'user' && (
            <Button
              className='self-center cursor-pointer'
              variant={'default'}
              onClick={handleOnBack}
            >
              Quay lại
            </Button>
          )}
          {canEdit && viewMode === 'user' && (
            <Button
              className='self-center cursor-pointer'
              variant={'secondary'}
              onClick={handleEdit}
              disabled={isLoading || !canEdit}
            >
              Chỉnh sửa
            </Button>
          )}
          {viewMode === 'admin' && (
            <Button
              className='self-center cursor-pointer'
              variant={data.processFlowDto?.active ? 'destructive' : 'positive'}
              onClick={handleActivate}
              disabled={isPending}
            >
              {data.processFlowDto?.active ? 'Tạm dừng' : 'Kích hoạt'}
            </Button>
          )}
        </div>
        <AttachmentsGallery
          attachments={data?.attachments || []}
          editable={false}
        />
        <div className='flex flex-row gap-2 items-start p-3'>
          <div className='flex-1'>
            <ReadOnlyEdior
              loading={isLoading}
              content={data?.processContent?.content || ''}
            />
          </div>
          <div className='w-96'>
            <AcessDetailComponent
              accessRule={data?.processFlowDto?.accessRule}
            />
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProcessFlowView;
