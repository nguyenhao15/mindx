import ActivityTimeline from '@/modules/assetmanagement/maintances/components/Details/ActivityTimeline';
import DetailHeader from '@/modules/assetmanagement/maintances/components/Details/DetailHeader';
import ProgressStepper from '@/modules/assetmanagement/maintances/components/Details/ProgressStepper';
import TechnicalSolutionsCard from '@/modules/assetmanagement/maintances/components/Details/TechnicalSolutionsCard';
import AttachmentsGallery from '@/modules/core/attachments/components/AttachmentsGallery';
import Loader from '@/components/shared/Loader';
import ErrorPage from '@/components/shared/ErrorPage';
import DetailInfo from './DetailInfo';
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
  onUpdateOpen: () => void;
}

const DetailMasterPage = ({
  item,
  isLoading,
  error,
  onUpdateOpen,
}: DetailMasterPageProps) => {
  const { maintenanceDetailsInfo, files, updateHistory } = item || {};

  const detailStatus = maintenanceDetailsInfo?.maintenancesStatus || 'WAITING';
  const currentStep = STEP_INDEX_BY_STATUS[detailStatus] ?? 0;

  if (isLoading) {
    return <Loader text='Đang tải chi tiết đơn bảo trì...' />;
  }

  if (error) {
    return <ErrorPage message='Không thể tải chi tiết đơn bảo trì.' />;
  }

  return (
    <div className='bg-slate-50 min-h-full p-4 sm:p-6 lg:p-8'>
      <div className='mx-auto max-w-7xl space-y-4 sm:space-y-5'>
        <DetailHeader
          desrciption={
            maintenanceDetailsInfo?.description ||
            'Không có mô tả nào được cung cấp.'
          }
          ticketCode={
            maintenanceDetailsInfo?.id
              ? `MNT-${maintenanceDetailsInfo.id}`
              : 'MNT-2026-0042'
          }
          location={maintenanceDetailsInfo?.locationId || 'N/A'}
          reporter={maintenanceDetailsInfo?.createdBy || 'N/A'}
          issueDate={maintenanceDetailsInfo?.issueDate}
          status={detailStatus}
        />
        <div className='w-full flex justify-end items-end'>
          <Button
            variant={'default'}
            className='cursor-pointer w-fit self-end'
            onClick={onUpdateOpen}
          >
            Cập nhật
          </Button>
        </div>

        <ProgressStepper steps={DETAIL_STEPS} currentStep={currentStep} />

        <DetailInfo
          id={maintenanceDetailsInfo?.id || 0}
          fixCategory={maintenanceDetailsInfo?.fixCategory}
          fixItem={maintenanceDetailsInfo?.fixItem}
          totalProposals={maintenanceDetailsInfo?.totalProposals}
          totalCost={maintenanceDetailsInfo?.totalCost}
          createdDate={maintenanceDetailsInfo?.createdDate}
          lastModifiedDate={maintenanceDetailsInfo?.lastModifiedDate}
          description={maintenanceDetailsInfo?.description || 'N/A'}
          reWork={maintenanceDetailsInfo?.reWork || false}
        />

        <AttachmentsGallery attachments={files || []} />
        {/* <div className='bg-white p-3 rounded shadow'>
          <h2>Cập nhật thông tin</h2>
          <UpdateItemComponent
            id={item?.maintenanceDetailsInfo?.id || 0}
            maintenancesStatus={
              item?.maintenanceDetailsInfo?.maintenancesStatus || ''
            }
            reWork={item?.maintenanceDetailsInfo?.reWork || false}
            totalCost={item?.maintenanceDetailsInfo?.totalCost || 0}
          />
        </div> */}
        <div className='grid grid-cols-1 xl:grid-cols-12 gap-4'>
          <div className='xl:col-span-5'>
            <TechnicalSolutionsCard
              solutions={maintenanceDetailsInfo?.maintenancesProposals || []}
            />
          </div>
          <div className='xl:col-span-7'>
            <ActivityTimeline activities={updateHistory || []} />
          </div>
        </div>
      </div>
    </div>
  );
};

export default DetailMasterPage;
