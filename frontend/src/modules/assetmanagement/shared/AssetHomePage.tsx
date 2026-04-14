import {
  Package,
  Wrench,
  ShoppingCart,
  Truck,
  Wrench as WrenchIcon,
  CheckCircle2,
  Clock,
  AlertTriangle,
} from 'lucide-react';
import ModuleCard from '../components/ModuleCard';
import AssetHealthBar from '../components/AssetHealthBar';
import RecentActivityList, {
  type ActivityItem,
} from '../components/RecentActivityList';

// ─── Dummy Data ────────────────────────────────────────────────────────────────
// NOTE: Replace with real API calls. Data is intentionally static/memoizable to
// avoid re-renders on a 20,000-item dataset — heavy lists should use virtualised
// tables (e.g. TanStack Virtual) inside their own module pages.

const MODULE_CARDS = [
  {
    title: 'Quản lý tài sản',
    description: 'Theo dõi và kiểm soát toàn bộ tài sản công ty',
    path: '/assets/inventory',
    icon: Package,
    accentColor: 'text-[#1d3557]',
    bgColor: 'bg-[#1d3557]/10',
    stats: [
      {
        label: 'Tổng tài sản',
        value: '20.000',
        trend: { value: 2.4, positive: true },
      },
      {
        label: 'Đang hoạt động',
        value: '18.342',
        trend: { value: 1.1, positive: true },
      },
      { label: 'Cần kiểm tra', value: '847' },
      { label: 'Đã thanh lý', value: '811' },
    ],
  },
  {
    title: 'Bảo trì & Sửa chữa',
    description: 'Quản lý yêu cầu và tiến độ sửa chữa',
    path: '/assets/maintance',
    icon: Wrench,
    accentColor: 'text-amber-600',
    bgColor: 'bg-amber-50',
    stats: [
      {
        label: 'Yêu cầu mới',
        value: '38',
        trend: { value: 5.2, positive: false },
      },
      {
        label: 'Đang xử lý',
        value: '14',
        trend: { value: 3.0, positive: true },
      },
      { label: 'Hoàn thành T4', value: '62' },
      { label: 'Tổng chi phí', value: '42M₫' },
    ],
  },
  {
    title: 'Mua sắm bổ sung',
    description: 'Theo dõi đơn mua và ngân sách',
    path: '/assets/procurement',
    icon: ShoppingCart,
    accentColor: 'text-sky-600',
    bgColor: 'bg-sky-50',
    stats: [
      {
        label: 'Đơn chờ duyệt',
        value: '7',
        trend: { value: 2.0, positive: false },
      },
      { label: 'Đang giao hàng', value: '3' },
      {
        label: 'Hoàn thành T4',
        value: '15',
        trend: { value: 8.0, positive: true },
      },
      { label: 'Ngân sách còn', value: '185M₫' },
    ],
  },
  {
    title: 'Vận chuyển',
    description: 'Điều phối và theo dõi di chuyển tài sản',
    path: '/assets/transportation',
    icon: Truck,
    accentColor: 'text-emerald-600',
    bgColor: 'bg-emerald-50',
    stats: [
      {
        label: 'Chuyến hôm nay',
        value: '5',
        trend: { value: 1.0, positive: true },
      },
      { label: 'Đang vận chuyển', value: '2' },
      {
        label: 'Hoàn thành T4',
        value: '28',
        trend: { value: 4.5, positive: true },
      },
      { label: 'Tổng km', value: '1.240' },
    ],
  },
] as const;

const HEALTH_SEGMENTS = [
  {
    label: 'Hoạt động tốt',
    count: 16520,
    color: 'bg-emerald-400',
    bgColor: 'bg-emerald-50',
    textColor: 'text-emerald-700',
  },
  {
    label: 'Cần bảo dưỡng',
    count: 1822,
    color: 'bg-amber-400',
    bgColor: 'bg-amber-50',
    textColor: 'text-amber-700',
  },
  {
    label: 'Đang sửa chữa',
    count: 847,
    color: 'bg-red-400',
    bgColor: 'bg-red-50',
    textColor: 'text-red-700',
  },
  {
    label: 'Ngừng hoạt động',
    count: 811,
    color: 'bg-slate-300',
    bgColor: 'bg-slate-50',
    textColor: 'text-slate-600',
  },
];

const RECENT_ACTIVITIES: ActivityItem[] = [
  {
    id: '1',
    icon: WrenchIcon,
    iconBg: 'bg-amber-50',
    iconColor: 'text-amber-500',
    title: 'Yêu cầu bảo trì #MNT-0841 được tạo',
    subtitle: 'Máy điều hòa P.203 — Tòa A · Nguyễn Minh Tuấn',
    timestamp: '10 phút trước',
    badge: { label: 'Chờ xử lý', color: 'bg-amber-100 text-amber-700' },
  },
  {
    id: '2',
    icon: CheckCircle2,
    iconBg: 'bg-emerald-50',
    iconColor: 'text-emerald-500',
    title: 'Đơn mua sắm #PO-2024 đã được duyệt',
    subtitle: '10 máy tính xách tay Dell XPS 15 · Phạm Thị Lan',
    timestamp: '1 giờ trước',
    badge: { label: 'Đã duyệt', color: 'bg-emerald-100 text-emerald-700' },
  },
  {
    id: '3',
    icon: Truck,
    iconBg: 'bg-sky-50',
    iconColor: 'text-sky-500',
    title: 'Vận chuyển tài sản hoàn tất #TRN-0312',
    subtitle: '3 bàn làm việc → Văn phòng Hà Nội · Lê Văn Hùng',
    timestamp: '3 giờ trước',
    badge: { label: 'Hoàn thành', color: 'bg-sky-100 text-sky-700' },
  },
  {
    id: '4',
    icon: AlertTriangle,
    iconBg: 'bg-red-50',
    iconColor: 'text-red-400',
    title: 'Tài sản #AST-1594 chuyển trạng thái Hỏng',
    subtitle: 'Máy chiếu Epson EB-X41 · Phòng hội thảo tầng 5',
    timestamp: '5 giờ trước',
    badge: { label: 'Cần xử lý', color: 'bg-red-100 text-red-600' },
  },
  {
    id: '5',
    icon: Clock,
    iconBg: 'bg-slate-100',
    iconColor: 'text-slate-500',
    title: 'Kiểm kê định kỳ tháng 4 bắt đầu',
    subtitle: 'Toàn bộ tài sản — Lịch tự động · Hệ thống',
    timestamp: 'Hôm qua',
  },
];

// ─── Page ──────────────────────────────────────────────────────────────────────

const AssetHomePage = () => {
  const today = new Date().toLocaleDateString('vi-VN', {
    weekday: 'long',
    year: 'numeric',
    month: 'long',
    day: 'numeric',
  });

  return (
    <div className='flex flex-col gap-8 p-8'>
      {/* Page header */}
      <div className='flex items-start justify-between'>
        <div>
          <h1 className='text-2xl font-bold text-slate-800'>
            Tổng quan hệ thống
          </h1>
          <p className='text-sm text-slate-400 mt-1 capitalize'>{today}</p>
        </div>
        <div className='flex items-center gap-2 bg-emerald-50 text-emerald-700 text-xs font-medium px-3 py-2 rounded-xl'>
          <span className='w-2 h-2 rounded-full bg-emerald-400 animate-pulse' />
          Hệ thống đang hoạt động
        </div>
      </div>

      {/* Asset health overview */}
      <AssetHealthBar total={20000} segments={HEALTH_SEGMENTS} />

      {/* Module cards */}
      <div>
        <h2 className='text-sm font-semibold text-slate-500 uppercase tracking-wider mb-4'>
          Phân hệ quản lý
        </h2>
        <div className='grid grid-cols-1 sm:grid-cols-2 xl:grid-cols-4 gap-4'>
          {MODULE_CARDS.map((card) => (
            <ModuleCard key={card.path} {...card} />
          ))}
        </div>
      </div>

      {/* Recent activity */}
      <RecentActivityList items={RECENT_ACTIVITIES} />
    </div>
  );
};

export default AssetHomePage;
