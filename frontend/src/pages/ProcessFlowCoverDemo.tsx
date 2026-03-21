import {
  ProcessFlowCoverTemplates,
  ProcessFlowCover,
} from '@/components/shared/ProcessFlowCoverTemplates';

/**
 * Demo page để xem 10 mẫu cover
 */
export default function ProcessFlowCoverDemo() {
  return (
    <div className='p-8 bg-slate-50 min-h-screen'>
      <div className='max-w-7xl mx-auto'>
        <h1 className='text-3xl font-bold mb-2'>Process Flow Cover Templates</h1>
        <p className='text-slate-600 mb-8 text-lg'>
          10 mẫu cover khác nhau cho Process Flow Card
        </p>

        <div className='grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6'>
          {ProcessFlowCoverTemplates.map((template) => (
            <div
              key={template.id}
              className='bg-white dark:bg-slate-800 border border-slate-100 dark:border-slate-700 rounded-2xl overflow-hidden group cursor-pointer shadow-sm hover:shadow-lg transition-all'
            >
              <ProcessFlowCover templateId={template.id} />
              <div className='p-4'>
                <h5 className='text-sm font-bold mb-1'>{template.label}</h5>
                <p className='text-xs text-slate-500 font-mono'>{template.id}</p>
              </div>
            </div>
          ))}
        </div>

        <div className='mt-12 p-6 bg-white rounded-xl border border-slate-200 dark:bg-slate-900 dark:border-slate-700'>
          <h2 className='text-2xl font-bold mb-4'>Cách sử dụng</h2>
          <div className='space-y-3 text-slate-700 dark:text-slate-300'>
            <div>
              <h3 className='font-semibold mb-2'>1. Render cover ngẫu nhiên:</h3>
              <code className='block bg-slate-100 dark:bg-slate-800 p-2 rounded text-sm font-mono'>
                &lt;ProcessFlowCover random /&gt;
              </code>
            </div>

            <div>
              <h3 className='font-semibold mb-2'>
                2. Render cover với template cụ thể:
              </h3>
              <code className='block bg-slate-100 dark:bg-slate-800 p-2 rounded text-sm font-mono'>
                &lt;ProcessFlowCover templateId="document-stack" /&gt;
              </code>
            </div>

            <div>
              <h3 className='font-semibold mb-2'>3. Lấy template info:</h3>
              <code className='block bg-slate-100 dark:bg-slate-800 p-2 rounded text-sm font-mono'>
                import {'{'}
                getRandomTemplate, getTemplateById{'}'}
                from '@/components/shared/ProcessFlowCoverTemplates'
              </code>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
