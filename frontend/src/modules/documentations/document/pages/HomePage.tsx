import { SearchHero } from '../components/shared/SearchHero';
import { DepartmentGrid } from '../../../core/departments/components/DepartmentGrid';
import { ProcessSection } from '../components/shared/ProcessSection';

export function HomePage() {
  return (
    <div className='flex flex-1 flex-col overflow-y-auto'>
      <main className='flex flex-col bg-background-light dark:bg-background-dark text-slate-900 dark:text-slate-100'>
        <SearchHero />

        <section className='px-8 max-w-7xl mx-auto gap-10 pb-16'>
          {/* Top Row: Departments & Workstreams */}
          <DepartmentGrid />
        </section>

        <section className='px-8 mx-auto max-w-7xl pb-24'>
          <ProcessSection />
        </section>
      </main>
    </div>
  );
}
