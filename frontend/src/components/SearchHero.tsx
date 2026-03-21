import { useSearchDocument } from '@/hookQueries/useProcessFlowHooks';
import { useState } from 'react';
import { FaSearch } from 'react-icons/fa';
import Spinner from './shared/Spinner';
import { AnimatePresence, motion } from 'framer-motion';
import { useNavigate } from 'react-router-dom';

export function SearchHero() {
  const [keyword, setKeyword] = useState('');
  const navigate = useNavigate();

  const { data, isLoading } = useSearchDocument(keyword, {
    enabled: keyword.length >= 2, // Disable automatic query on mount
  });

  const handleOnSelect = (item: any) => {
    navigate(`/documents/tag-flow/${item.id}`, {
      state: { processItem: item },
    });
  };

  return (
    <section className='py-16 px-8 max-w-5xl mx-auto flex flex-col items-center text-center'>
      <h2 className='text-4xl font-black text-brand-primary tracking-tight mb-8'>
        What are you looking for today?
      </h2>
      <div className='relative w-full'>
        <div className='w-full max-w-3xl relative group'>
          <div className='absolute inset-y-0 left-5 flex items-center pointer-events-none'>
            {isLoading ? (
              <Spinner />
            ) : (
              <FaSearch className='text-slate-400 group-focus-within:text-brand-primary transition-colors' />
            )}
          </div>
          <input
            className='w-full h-16 pl-14 pr-6 rounded-2xl border-none bg-white dark:bg-slate-800 shadow-xl shadow-slate-200/50 dark:shadow-none focus:ring-2 focus:ring-brand-primary text-lg transition-all placeholder:text-slate-400'
            placeholder='Search for processes, documents, or policies...'
            type='text'
            onChange={(e) => setKeyword(e.target.value)}
          />
        </div>
        <AnimatePresence>
          {(data?.length > 0 || isLoading) && (
            <motion.div
              initial={{
                opacity: 0,
                y: 10,
              }}
              animate={{
                opacity: 1,
                y: 0,
              }}
              exit={{
                opacity: 0,
                y: 10,
              }}
              className='absolute w-full mt-2 bg-white rounded-xl p-5 shadow-xl border border-slate-100 overflow-hidden z-20'
            >
              {isLoading ? (
                <div className='p-4 text-center text-slate-500'>
                  Searching...
                </div>
              ) : (
                data.slice(0, 5).map((flowItem: any) => (
                  <button
                    key={flowItem.id}
                    onClick={() => handleOnSelect(flowItem)}
                    className='w-full flex items-center p-4 rounded hover:bg-slate-100 transition-colors text-left border-b border-slate-50 last:border-0 cursor-pointer'
                  >
                    <div className='ml-4'>
                      <p className='font-medium text-slate-900'>
                        {flowItem?.title}
                      </p>
                      <p className='text-sm text-slate-500'>
                        {flowItem?.description || 'No description available.'}
                      </p>
                    </div>
                  </button>
                ))
              )}
            </motion.div>
          )}
        </AnimatePresence>
      </div>
    </section>
  );
}
