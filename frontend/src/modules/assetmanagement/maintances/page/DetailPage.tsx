import ActivityTimeline from '@/modules/assetmanagement/maintances/components/Details/ActivityTimeline';
import DetailHeader from '@/modules/assetmanagement/maintances/components/Details/DetailHeader';
import ProgressStepper from '@/modules/assetmanagement/maintances/components/Details/ProgressStepper';
import TechnicalSolutionsCard from '@/modules/assetmanagement/maintances/components/Details/TechnicalSolutionsCard';
import { useParams } from 'react-router-dom';
import { useGetMaintanceDetailById } from '../hooks/useMaintenanceHooks';
import { formatDate, formatDateTime, formatPrice } from '@/utils/formatValue';
import AttachmentsGallery from '@/modules/core/attachments/components/AttachmentsGallery';
import Loader from '@/components/shared/Loader';
import ErrorPage from '@/components/shared/ErrorPage';

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
          ticketCode={id ? `MNT-${id}` : 'MNT-2026-0042'}
          location={maintenanceDetailsInfo?.locationId || 'N/A'}
          reporter={maintenanceDetailsInfo?.createdBy || 'N/A'}
          issueDate={formatDate(maintenanceDetailsInfo?.issueDate)}
          status={detailStatus}
        />

        <ProgressStepper steps={DETAIL_STEPS} currentStep={currentStep} />

        <section className='w-full bg-white rounded-2xl border border-slate-100 p-5 sm:p-6'>
          <div className='flex items-center justify-between gap-3'>
            <h2 className='text-base font-semibold text-slate-800'>
              Thông Tin Sự Cố
            </h2>
            <span className='inline-flex rounded-full bg-[#1d3557]/10 px-3 py-1 text-xs font-semibold text-[#1d3557]'>
              ID #{maintenanceDetailsInfo?.id ?? id}
            </span>
          </div>

          <div className='mt-4 grid grid-cols-1 gap-3 sm:grid-cols-2 xl:grid-cols-3'>
            <article className='rounded-xl bg-slate-50 p-4'>
              <p className='text-xs text-slate-500'>Danh mục sửa chữa</p>
              <p className='mt-1 text-sm font-semibold text-slate-800'>
                {maintenanceDetailsInfo?.fixCategory?.categoryTitle || 'N/A'}
              </p>
            </article>

            <article className='rounded-xl bg-slate-50 p-4'>
              <p className='text-xs text-slate-500'>Hạng mục</p>
              <p className='mt-1 text-sm font-semibold text-slate-800'>
                {maintenanceDetailsInfo?.fixItem?.itemTitle || 'N/A'}
              </p>
            </article>

            <article className='rounded-xl bg-slate-50 p-4'>
              <p className='text-xs text-slate-500'>Tổng phương án</p>
              <p className='mt-1 text-sm font-semibold text-slate-800'>
                {maintenanceDetailsInfo?.totalProposals ?? 0}
              </p>
            </article>

            <article className='rounded-xl bg-slate-50 p-4'>
              <p className='text-xs text-slate-500'>Tổng chi phí</p>
              <p className='mt-1 text-sm font-semibold text-slate-800'>
                {formatPrice(maintenanceDetailsInfo?.totalCost ?? 0)}
              </p>
            </article>

            <article className='rounded-xl bg-slate-50 p-4'>
              <p className='text-xs text-slate-500'>Ngày tạo đơn</p>
              <p className='mt-1 text-sm font-semibold text-slate-800'>
                {formatDateTime(maintenanceDetailsInfo?.createdDate)}
              </p>
            </article>

            <article className='rounded-xl bg-slate-50 p-4'>
              <p className='text-xs text-slate-500'>Cập nhật gần nhất</p>
              <p className='mt-1 text-sm font-semibold text-slate-800'>
                {formatDateTime(maintenanceDetailsInfo?.lastModifiedDate)}
              </p>
            </article>
          </div>

          <article className='mt-3 rounded-xl bg-slate-50 p-4'>
            <p className='text-xs text-slate-500'>Mô tả sự cố</p>
            <p className='mt-1 text-sm text-slate-700'>
              {maintenanceDetailsInfo?.description || 'Chưa có mô tả'}
            </p>
            <p className='mt-2 text-xs text-slate-500'>
              Re-work: {maintenanceDetailsInfo?.reWork ? 'Có' : 'Không'}
            </p>
          </article>
        </section>

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
