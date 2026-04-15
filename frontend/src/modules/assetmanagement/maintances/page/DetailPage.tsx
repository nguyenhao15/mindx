import ActivityTimeline from '@/modules/assetmanagement/maintances/components/Details/ActivityTimeline';
import BeforeAfterMedia from '@/modules/assetmanagement/maintances/components/Details/BeforeAfterMedia';
import DetailHeader from '@/modules/assetmanagement/maintances/components/Details/DetailHeader';
import ProgressStepper from '@/modules/assetmanagement/maintances/components/Details/ProgressStepper';
import TechnicalSolutionsCard from '@/modules/assetmanagement/maintances/components/Details/TechnicalSolutionsCard';

const DETAIL_STEPS = [
  { key: 'waiting', label: 'Chờ duyệt' },
  { key: 'processing', label: 'Đang sửa' },
  { key: 'checked', label: 'Đợi nghiệm thu' },
  { key: 'finished', label: 'Hoàn thành' },
];

const BEFORE_MEDIA = [
  {
    id: 'b1',
    title: 'Ảnh hỏng trần khu kỹ thuật',
    type: 'image' as const,
    src: 'https://images.unsplash.com/photo-1581578731548-c64695cc6952?q=80&w=1200&auto=format&fit=crop',
  },
  {
    id: 'b2',
    title: 'Video tiếng ồn đường ống',
    type: 'video' as const,
    src: 'https://images.unsplash.com/photo-1489515217757-5fd1be406fef?q=80&w=1200&auto=format&fit=crop',
  },
];

const AFTER_MEDIA = [
  {
    id: 'a1',
    title: 'Ảnh nghiệm thu sau thay ống',
    type: 'image' as const,
    src: 'https://images.unsplash.com/photo-1487958449943-2429e8be8625?q=80&w=1200&auto=format&fit=crop',
  },
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

const DetailPage = () => {
  return (
    <div className='bg-slate-50 min-h-full p-4 sm:p-6 lg:p-8'>
      <div className='mx-auto max-w-7xl space-y-4 sm:space-y-5'>
        <DetailHeader
          ticketCode='MNT-2026-0042'
          location='Tòa A - Khu kỹ thuật tầng 2'
          reporter='Nguyễn Văn An'
          issueDate='15/04/2026'
          status='CHECKED'
        />

        <ProgressStepper steps={DETAIL_STEPS} currentStep={2} />

        <BeforeAfterMedia before={BEFORE_MEDIA} after={AFTER_MEDIA} />

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
