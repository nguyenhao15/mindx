import ActivityTimeline from '@/modules/assetmanagement/maintances/components/Details/ActivityTimeline';
import DetailHeader from '@/modules/assetmanagement/maintances/components/Details/DetailHeader';
import ProgressStepper from '@/modules/assetmanagement/maintances/components/Details/ProgressStepper';
import TechnicalSolutionsCard from '@/modules/assetmanagement/maintances/components/Details/TechnicalSolutionsCard';
import { useParams } from 'react-router-dom';
import { useGetMaintanceDetailById } from '../hooks/useMaintenanceHooks';

import AttachmentsGallery from '@/modules/core/attachments/components/AttachmentsGallery';
import Loader from '@/components/shared/Loader';
import ErrorPage from '@/components/shared/ErrorPage';
import DetailInfo from '../components/Details/DetailInfo';

const DETAIL_STEPS = [
  { key: 'waiting', label: 'Chờ duyệt' },
  { key: 'processing', label: 'Đang sửa' },
  { key: 'checked', label: 'Đợi nghiệm thu' },
  { key: 'finished', label: 'Hoàn thành' },
];

const SOLUTIONS = [
  {
    id: 's1',
    title: 'Thay đoạn ống và gia cố mối nối',
    detail:
      'Thay mới đoạn ống khu vực rò rỉ, xử lý chống thấm cục bộ và kiểm tra áp suất toàn tuyến.',
    estimateHours: 6,
    materials: ['Ống PVC D34', 'Keo chống thấm', 'Kẹp nối inox'],
  },
  {
    id: 's2',
    title: 'Bổ sung lớp cách âm khoang kỹ thuật',
    detail:
      'Bọc vật liệu giảm rung để hạn chế tiếng ồn phát sinh trong giờ vận hành cao điểm.',
    estimateHours: 3,
    materials: ['Mút cách âm', 'Keo dán chuyên dụng'],
  },
];

const TIMELINE = [
  {
    id: 't1',
    actor: 'Nguyễn Văn An',
    role: 'Người báo cáo' as const,
    action: 'Tạo đề xuất sửa chữa',
    note: 'Phát hiện rò rỉ và tiếng ồn lớn tại khu kỹ thuật tầng 2.',
    timestamp: '09:15 - 15/04/2026',
  },
  {
    id: 't2',
    actor: 'Lê Minh Hùng',
    role: 'Quản lý' as const,
    action: 'Phê duyệt đề xuất',
    note: 'Ưu tiên xử lý trong ngày để không ảnh hưởng vận hành văn phòng.',
    timestamp: '09:40 - 15/04/2026',
  },
  {
    id: 't3',
    actor: 'Phạm Quốc Bảo',
    role: 'Kỹ thuật viên' as const,
    action: 'Cập nhật phương án kỹ thuật',
    note: 'Đề xuất thay đoạn ống và test áp suất sau khi hoàn tất.',
    timestamp: '10:10 - 15/04/2026',
  },
  {
    id: 't4',
    actor: 'Phạm Quốc Bảo',
    role: 'Kỹ thuật viên' as const,
    action: 'Chuyển trạng thái chờ nghiệm thu',
    note: 'Đã hoàn thành xử lý chính, chờ bộ phận vận hành kiểm tra lần cuối.',
    timestamp: '14:20 - 15/04/2026',
  },
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

const DetailPage = () => {
  const { id } = useParams<{ id: string }>();
  const { data, isLoading, error } = useGetMaintanceDetailById(Number(id));

  const { maintenanceDetailsInfo, files } = data || {};
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
          ticketCode={id ? `MNT-${id}` : 'MNT-2026-0042'}
          location={maintenanceDetailsInfo?.locationId || 'N/A'}
          reporter={maintenanceDetailsInfo?.createdBy || 'N/A'}
          issueDate={maintenanceDetailsInfo?.issueDate}
          status={detailStatus}
        />

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

        <div className='grid grid-cols-1 xl:grid-cols-12 gap-4'>
          <div className='xl:col-span-5'>
            <TechnicalSolutionsCard solutions={SOLUTIONS} />
          </div>
          <div className='xl:col-span-7'>
            <ActivityTimeline activities={TIMELINE} />
          </div>
        </div>
      </div>
    </div>
  );
};

export default DetailPage;
