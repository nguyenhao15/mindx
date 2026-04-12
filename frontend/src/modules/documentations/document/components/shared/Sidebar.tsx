import {
  FaBell,
  FaClock,
  FaCog,
  FaFileAlt,
  FaHandsHelping,
  FaHome,
  FaStar,
} from 'react-icons/fa';

const NAV_ITEMS = [
  {
    icon: FaHome,
    label: 'Dashboard',
    isActive: true,
  },
  {
    icon: FaFileAlt,
    label: 'My Documents',
    path: '/documents',
    isActive: false,
  },
  {
    icon: FaStar,
    label: 'Favorites',
    isActive: false,
  },
  {
    icon: FaClock,
    label: 'Recent History',
    isActive: false,
  },
];
export function Sidebar() {
  return (
    <aside className='hidden md:flex flex-col w-64 h-screen border-r border-slate-200 dark:border-slate-800 bg-white dark:bg-background-dark sticky top-0'>
      {/* Logo Area */}
      <div className='h-16 flex items-center px-6 border-b border-slate-100 dark:border-slate-700'>
        <div className='flex items-center gap-2'>
          <div className='w-8 h-8 bg-primary rounded-lg flex items-center justify-center'>
            <span className='text-white font-bold text-lg'>K</span>
          </div>
          <span className='font-semibold text-slate-900 dark:text-slate-100 text-lg tracking-tight'>
            KnowHub
          </span>
        </div>
      </div>

      {/* Main Navigation */}
      <nav
        className='flex-1 py-6 px-4 space-y-1 overflow-y-auto'
        aria-label='Main Navigation'
      >
        {NAV_ITEMS.map((item) => {
          const Icon = item.icon;
          return (
            <a
              key={item.label}
              href={item.path || '#'}
              className={`flex items-center gap-3 px-3 py-2.5 rounded-lg transition-colors group ${item.isActive ? 'bg-primary/10 text-primary font-medium' : 'text-slate-600 hover:bg-slate-50 hover:text-slate-900 dark:text-slate-400 dark:hover:bg-slate-700 dark:hover:text-slate-100'}`}
              aria-current={item.isActive ? 'page' : undefined}
            >
              <Icon
                className={`w-5 h-5 ${item.isActive ? 'text-primary' : 'text-slate-400 group-hover:text-slate-600 dark:text-slate-400 dark:group-hover:text-slate-100'}`}
                aria-hidden='true'
              />
              <span>{item.label}</span>
            </a>
          );
        })}
      </nav>

      {/* Bottom Section */}
      <div className='p-4 border-t border-slate-100 dark:border-slate-700 space-y-1'>
        <a
          href='#'
          className='flex items-center justify-between px-3 py-2.5 rounded-lg text-slate-600 hover:bg-slate-50 hover:text-slate-900 transition-colors group dark:text-slate-400 dark:hover:bg-slate-700 dark:hover:text-slate-100'
        >
          <div className='flex items-center gap-3'>
            <FaBell
              className='w-5 h-5 text-slate-400 group-hover:text-slate-600 dark:text-slate-400 dark:group-hover:text-slate-100'
              aria-hidden='true'
            />
            <span>Updates</span>
          </div>
          <span className='flex h-2 w-2 relative'>
            <span className='animate-ping absolute inline-flex h-full w-full rounded-full bg-primary opacity-75'></span>
            <span className='relative inline-flex rounded-full h-2 w-2 bg-primary'></span>
          </span>
        </a>
        <a
          href='#'
          className='flex items-center gap-3 px-3 py-2.5 rounded-lg text-slate-600 hover:bg-slate-50 hover:text-slate-900 transition-colors group dark:text-slate-400 dark:hover:bg-slate-700 dark:hover:text-slate-100'
        >
          <FaCog
            className='w-5 h-5 text-slate-400 group-hover:text-slate-600 dark:text-slate-400 dark:group-hover:text-slate-100'
            aria-hidden='true'
          />
          <span>Settings</span>
        </a>
        <a
          href='#'
          className='flex items-center gap-3 px-3 py-2.5 rounded-lg text-slate-600 hover:bg-slate-50 hover:text-slate-900 transition-colors group dark:text-slate-400 dark:hover:bg-slate-700 dark:hover:text-slate-100'
        >
          <FaHandsHelping
            className='w-5 h-5 text-slate-400 group-hover:text-slate-600 dark:text-slate-400 dark:group-hover:text-slate-100'
            aria-hidden='true'
          />
          <span>Help & Support</span>
        </a>
      </div>
    </aside>
  );
}
