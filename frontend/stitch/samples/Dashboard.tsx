import {
  LayoutDashboard,
  FileText,
  CheckCircle,
  AlertTriangle,
  Plus,
} from 'lucide-react';

export default function Dashboard() {
  return (
    <div className='flex h-screen bg-slate-50 font-sans'>
      {/* Sidebar */}
      <aside className='w-64 bg-[#1d3557] text-slate-100 flex flex-col'>
        <div className='p-6 text-2xl font-bold border-b border-white/10'>
          Maintenance
        </div>
        <nav className='flex-1 p-4 space-y-2'>
          <a
            href='#'
            className='flex items-center gap-3 p-3 bg-white/10 rounded-xl text-white'
          >
            <LayoutDashboard size={20} /> Dashboard
          </a>
          <a
            href='#'
            className='flex items-center gap-3 p-3 hover:bg-white/5 rounded-xl transition'
          >
            <FileText size={20} /> Requests
          </a>
        </nav>
      </aside>

      {/* Main Content */}
      <main className='flex-1 p-8 overflow-y-auto'>
        <div className='flex justify-between items-center mb-8'>
          <h1 className='text-3xl font-bold text-slate-800'>Overview</h1>
          <button className='flex items-center gap-2 bg-[#1d3557] text-white px-5 py-3 rounded-xl hover:bg-[#152744] transition shadow-md'>
            <Plus size={20} /> New Request
          </button>
        </div>

        {/* Metrics */}
        <div className='grid grid-cols-3 gap-6 mb-8'>
          <div className='bg-white p-6 rounded-2xl border border-slate-100 flex flex-col gap-4'>
            <div className='flex items-center gap-3 text-slate-500'>
              <FileText className='text-blue-500' /> Total Requests
            </div>
            <div className='text-4xl font-bold text-slate-800'>124</div>
          </div>
          <div className='bg-white p-6 rounded-2xl border border-slate-100 flex flex-col gap-4'>
            <div className='flex items-center gap-3 text-slate-500'>
              <CheckCircle className='text-green-500' /> Operational
            </div>
            <div className='text-4xl font-bold text-slate-800'>89%</div>
          </div>
          <div className='bg-white p-6 rounded-2xl border border-slate-100 flex flex-col gap-4'>
            <div className='flex items-center gap-3 text-slate-500'>
              <AlertTriangle className='text-amber-500' /> Under Repair
            </div>
            <div className='text-4xl font-bold text-slate-800'>14</div>
          </div>
        </div>

        {/* Recent Requests */}
        <div className='bg-white rounded-2xl border border-slate-100 overflow-hidden'>
          <div className='p-6 border-b border-slate-100 font-bold text-lg text-slate-800'>
            Recent Repair Requests
          </div>
          <table className='w-full text-left'>
            <thead className='bg-slate-50 text-slate-500'>
              <tr>
                <th className='p-4 font-medium'>ID</th>
                <th className='p-4 font-medium'>Facility</th>
                <th className='p-4 font-medium'>Issue</th>
                <th className='p-4 font-medium'>Status</th>
                <th className='p-4 font-medium'>Date</th>
              </tr>
            </thead>
            <tbody>
              <tr className='border-b border-slate-50 last:border-0 hover:bg-slate-50/50 transition'>
                <td className='p-4 text-slate-600'>REQ-001</td>
                <td className='p-4 font-medium text-slate-800'>Main Office</td>
                <td className='p-4 text-slate-600'>AC Unit Malfunction</td>
                <td className='p-4'>
                  <span className='px-3 py-1 bg-amber-100 text-amber-700 rounded-full text-sm font-medium'>
                    Pending
                  </span>
                </td>
                <td className='p-4 text-slate-500'>Oct 24, 2026</td>
              </tr>
              <tr className='border-b border-slate-50 last:border-0 hover:bg-slate-50/50 transition'>
                <td className='p-4 text-slate-600'>REQ-002</td>
                <td className='p-4 font-medium text-slate-800'>Warehouse A</td>
                <td className='p-4 text-slate-600'>Broken Lighting</td>
                <td className='p-4'>
                  <span className='px-3 py-1 bg-blue-100 text-blue-700 rounded-full text-sm font-medium'>
                    In Progress
                  </span>
                </td>
                <td className='p-4 text-slate-500'>Oct 23, 2026</td>
              </tr>
              <tr className='hover:bg-slate-50/50 transition'>
                <td className='p-4 text-slate-600'>REQ-003</td>
                <td className='p-4 font-medium text-slate-800'>Branch 2</td>
                <td className='p-4 text-slate-600'>Plumbing Leak</td>
                <td className='p-4'>
                  <span className='px-3 py-1 bg-green-100 text-green-700 rounded-full text-sm font-medium'>
                    Completed
                  </span>
                </td>
                <td className='p-4 text-slate-500'>Oct 20, 2026</td>
              </tr>
            </tbody>
          </table>
        </div>
      </main>
    </div>
  );
}
