import ActivityTimeline from '@/modules/assetmanagement/maintances/components/Details/ActivityTimeline';
import DetailHeader from '@/modules/assetmanagement/maintances/components/Details/DetailHeader';
import ProgressStepper from '@/modules/assetmanagement/maintances/components/Details/ProgressStepper';
import TechnicalSolutionsCard from '@/modules/assetmanagement/maintances/components/Proposal/TechnicalSolutionsCard';
import AttachmentsGallery from '@/modules/core/attachments/components/AttachmentsGallery';
import Loader from '@/components/shared/Loader';
import ErrorPage from '@/components/shared/ErrorPage';
import { useGetAvailableActionUpdate } from '../../hooks/useMaintenanceHooks';
import { useMemo } from 'react';
import type { AvailableActionUpdate } from '@/modules/core/admin/schema/workFlowSchema';
import FinishedComponent from './FinishedComponent';
import { Button } from '@/components/ui/button';

const DETAIL_STEPS = [
  { key: 'waiting', label: 'Chờ duyệt' },
  { key: 'processing', label: 'Đang sửa' },
  { key: 'checked', label: 'Đợi nghiệm thu' },
  { key: 'finished', label: 'Hoàn thành' },
];

const STEP_INDEX_BY_STATUS: Record<string, number> = {
  WAITING: 0,
  APPROVED: 0,
  REJECTED: 0,
  PROCESSING: 1,
  CHECKED: 2,
  FINISHED: 3,
  COMPLETED: 3,
};

interface DetailMasterPageProps {
  item: any;
  isLoading: boolean;
  error: any;
  onUpdateOpen: (value: 'update' | 'rework' | 'finished') => void;
}

const DetailMasterPage = ({
  item,
  isLoading,
  error,
  onUpdateOpen,
}: DetailMasterPageProps) => {
  const { maintenanceDetailsInfo, files, updateHistory } = item || {};

  const { data: availableActions } = useGetAvailableActionUpdate(
    maintenanceDetailsInfo?.id,
    {
      enabled: !!maintenanceDetailsInfo?.id,
    },
  );

  const isCanAddSolution = useMemo(() => {
    if (!availableActions) return false;
    const haveProcessing = availableActions.some(
      (action: AvailableActionUpdate) => action.nextStatus === 'PROCESSING',
    );
    const haveChecked = availableActions.some(
      (action: AvailableActionUpdate) => action.nextStatus === 'CHECKED',
    );
    return haveProcessing || haveChecked;
  }, [availableActions]);

  const isCanFinish = useMemo(() => {
    if (!availableActions) return false;
    return availableActions.some(
      (action: AvailableActionUpdate) => action.nextStatus === 'FINISHED',
    );
  }, [availableActions]);

  const detailStatus = maintenanceDetailsInfo?.maintenancesStatus || 'WAITING';
  const currentStep = STEP_INDEX_BY_STATUS[detailStatus] ?? 0;

  if (isLoading) {
    return <Loader text='Đang tải chi tiết đơn bảo trì...' />;
  }

  if (error) {
    return <ErrorPage message='Không thể tải chi tiết đơn bảo trì.' />;
  }

  return (
    <div className='flex flex-col gap-4 bg-slate-50 min-h-full p-4 sm:p-6 lg:p-8'>
      <ProgressStepper steps={DETAIL_STEPS} currentStep={currentStep} />

      <div className='w-full mt-4 rounded-lg p-2 gap-4 flex flex-col-reverse lg:flex-row mx-auto space-y-4 sm:space-y-5'>
        <div className='flex-2/3 w-full flex flex-col gap-4'>
          <div className='p-5 bg-white rounded-lg shadow-sm flex flex-col gap-4'>
            <div className='flex flex-col gap-2'>
              <h2>Mô tả</h2>
              <p>{maintenanceDetailsInfo?.description || 'N/A'}</p>
            </div>

            <AttachmentsGallery attachments={files || []} />
          </div>

          {isCanFinish && (
            <Button
              className='self-end cursor-pointer'
              onClick={() => onUpdateOpen('finished')}
              variant={'positive'}
            >
              Hoàn thành sửa chữa
            </Button>
          )}
          <TechnicalSolutionsCard
            maintenanceId={maintenanceDetailsInfo?.id}
            canAddSolution={isCanAddSolution}
            solutions={maintenanceDetailsInfo?.maintenancesProposals || []}
          />
        </div>
        <div className='flex-1/3 flex flex-col gap-3 h-fit w-xl justify-center xl:justify-start'>
          <DetailHeader
            desrciption={
              maintenanceDetailsInfo?.description ||
              'Không có mô tả nào được cung cấp.'
            }
            ticketCode={
              maintenanceDetailsInfo?.id
                ? `MNT-${maintenanceDetailsInfo.id}`
                : 'N/A'
            }
            location={maintenanceDetailsInfo?.locationName || 'N/A'}
            reporter={maintenanceDetailsInfo?.createdBy || 'N/A'}
            issueDate={maintenanceDetailsInfo?.issueDate}
            status={detailStatus}
          />

          <ActivityTimeline limitItems={3} activities={updateHistory || []} />
        </div>
      </div>
    </div>
  );
};

export default DetailMasterPage;
