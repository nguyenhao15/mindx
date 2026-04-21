import {
  ClipboardList,
  Cog,
  LayoutDashboard,
  List,
  Plus,
  Wrench,
  type LucideIcon,
} from 'lucide-react';
import { NavLink, Outlet } from 'react-router-dom';
import { MAINTANCE_MENU_ITEMS } from './constants/maintanace-const.api';

const ICON_MAP: Record<string, LucideIcon> = {
  ClipboardList,
  Plus,
  LayoutDashboard,
  List,
  Wrench,

};

const MaintanceLayout = () => {
  return (
    <div className='flex flex-col h-full'>
      {/* Page Header */}
      <header className='bg-white border-b border-slate-100 px-8 pt-6 pb-0'>
        <div className='flex items-center gap-3 mb-4'>
          <div className='w-9 h-9 bg-[#1d3557]/10 rounded-xl flex items-center justify-center'>
            <Wrench size={18} className='text-[#1d3557]' />
          </div>
          <div>
            <h1 className='text-xl font-bold text-slate-800'>
              Bảo trì &amp; Sửa chữa
            </h1>
            <p className='text-sm text-slate-500'>
              Quản lý yêu cầu bảo trì và theo dõi tiến độ sửa chữa
            </p>
          </div>
        </div>

        {/* Tab Navigation */}
        <nav className='flex gap-1' aria-label='Maintance navigation'>
          {MAINTANCE_MENU_ITEMS.map((item) => {
            const Icon = ICON_MAP[item.icon] ?? ClipboardList;
            return (
              <NavLink
                key={item.path}
                to={item.path}
                end={item.end}
                className={({ isActive }) =>
                  [
                    'flex items-center gap-2 px-4 py-2.5 text-sm font-medium border-b-2 transition-all duration-150 -mb-px',
                    isActive
                      ? 'border-[#1d3557] text-[#1d3557]'
                      : 'border-transparent text-slate-500 hover:text-slate-700 hover:border-slate-300',
                  ].join(' ')
                }
              >
                <Icon size={16} />
                {item.label}
              </NavLink>
            );
          })}
        </nav>
      </header>

      {/* Content Area */}
      <div className='flex-1 overflow-y-auto p-8'>
        <Outlet />
      </div>
    </div>
  );
};

export default MaintanceLayout;
