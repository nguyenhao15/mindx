import { MODULE_NAME } from '@/constants/module-const';
import { Link } from 'react-router-dom';

const ModuleListComponents = () => {
  return (
    <div className='grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-3'>
      {MODULE_NAME.map((module) => (
        <Link
          key={module.path}
          to={module.path.startsWith('/') ? module.path : `/${module.path}`}
          className='flex flex-col group rounded-2xl border border-slate-200 bg-white p-5 shadow-sm transition-all hover:-translate-y-1 hover:border-cyan-300 hover:shadow-lg focus:outline-none focus:ring-2 focus:ring-cyan-400'
        >
          <div className='flex items-start justify-between'>
            <h2 className='text-xl font-semibold text-slate-900'>
              {module.title}
            </h2>
            <span className='rounded-full bg-slate-100 px-2.5 py-1 text-xs font-medium text-slate-600'>
              Open
            </span>
          </div>

          <p className='mt-3 text-sm text-slate-500 flex-1'>{module.desc}</p>

          <div className='mt-5 text-sm font-semibold text-cyan-700 group-hover:text-cyan-900'>
            Go to module
          </div>
        </Link>
      ))}
    </div>
  );
};

export default ModuleListComponents;
