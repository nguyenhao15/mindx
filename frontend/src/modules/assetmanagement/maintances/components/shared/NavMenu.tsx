import {
  LayoutDashboard,
  Wrench,
  ShoppingCart,
  Truck,
  type LucideIcon,
  ChevronRight,
  Settings,
} from 'lucide-react';
import { NavLink, useLocation } from 'react-router-dom';
import { MENU_ITEMS } from '@/modules/assetmanagement/constants/asset-const';

const ICON_MAP: Record<string, LucideIcon> = {
  LayoutDashboard,
  Wrench,
  ShoppingCart,
  Truck,
  Settings,
};

const NavMenu = () => {
  const location = useLocation();

  return (
    <aside className='w-64 bg-[#1d3557] text-slate-100 flex flex-col min-h-screen'>
      {/* Logo / Brand */}
      <div className='p-6 border-b border-white/10'>
        <div className='flex items-center gap-3'>
          <div className='w-8 h-8 bg-white/15 rounded-lg flex items-center justify-center'>
            <Settings size={18} className='text-white' />
          </div>
          <div>
            <p className='text-xs text-slate-400 leading-none'>Hệ thống</p>
            <h1 className='text-base font-bold text-white leading-tight'>
              Quản lý tài sản
            </h1>
          </div>
        </div>
      </div>

      {/* Navigation */}
      <nav className='flex-1 p-4 space-y-1'>
        <p className='text-xs text-slate-500 uppercase tracking-wider font-medium px-3 mb-3'>
          Phân hệ
        </p>
        {MENU_ITEMS.map((item) => {
          const Icon = ICON_MAP[item.icon] ?? LayoutDashboard;
          const isActive =
            item.path === '/assets'
              ? location.pathname === '/assets' ||
                location.pathname === '/assets/'
              : location.pathname.startsWith(item.path);

          return (
            <NavLink
              key={item.path}
              to={item.path}
              end={item.path === '/assets'}
              className={() =>
                [
                  'flex items-center justify-between gap-3 px-3 py-2.5 rounded-xl transition-all duration-150 group',
                  isActive
                    ? 'bg-white/15 text-white font-medium'
                    : 'text-slate-300 hover:bg-white/8 hover:text-white',
                ].join(' ')
              }
            >
              <span className='flex items-center gap-3'>
                <Icon
                  size={18}
                  className={
                    isActive
                      ? 'text-white'
                      : 'text-slate-400 group-hover:text-slate-200'
                  }
                />
                <span className='text-sm'>{item.label}</span>
              </span>
              {isActive && (
                <ChevronRight size={14} className='text-slate-300' />
              )}
            </NavLink>
          );
        })}
      </nav>

      {/* Footer */}
      <div className='p-4 border-t border-white/10'>
        <p className='text-xs text-slate-500 text-center'>
          Asset Management v1.0
        </p>
      </div>
    </aside>
  );
};

export default NavMenu;
