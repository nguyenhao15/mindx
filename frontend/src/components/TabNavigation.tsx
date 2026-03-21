import React, { useEffect, useState, useRef } from 'react';
import { motion } from 'framer-motion';
import { ChevronRightIcon, ChevronLeftIcon } from 'lucide-react';
export interface TabItem {
  id: string;
  label: string;
  icon: React.ElementType;
}
interface TabNavigationProps {
  tabs: TabItem[];
  activeTab: string;
  onTabChange: (id: string) => void;
}
export function TabNavigation({
  tabs,
  activeTab,
  onTabChange,
}: TabNavigationProps) {
  const scrollContainerRef = useRef<HTMLDivElement>(null);
  const [showLeftScroll, setShowLeftScroll] = useState(false);
  const [showRightScroll, setShowRightScroll] = useState(false);
  const checkScroll = () => {
    if (scrollContainerRef.current) {
      const { scrollLeft, scrollWidth, clientWidth } =
        scrollContainerRef.current;
      setShowLeftScroll(scrollLeft > 0);
      setShowRightScroll(scrollLeft < scrollWidth - clientWidth - 1);
    }
  };
  useEffect(() => {
    checkScroll();
    window.addEventListener('resize', checkScroll);
    return () => window.removeEventListener('resize', checkScroll);
  }, [tabs]);
  const scroll = (direction: 'left' | 'right') => {
    if (scrollContainerRef.current) {
      const scrollAmount = 200;
      scrollContainerRef.current.scrollBy({
        left: direction === 'left' ? -scrollAmount : scrollAmount,
        behavior: 'smooth',
      });
      setTimeout(checkScroll, 300);
    }
  };
  return (
    <div className='relative border-b border-slate-200 mb-8 bg-white px-8 pt-4'>
      {showLeftScroll && (
        <button
          onClick={() => scroll('left')}
          className='absolute left-0 top-1/2 -translate-y-1/2 z-10 bg-linear-to-r from-white 
          via-white to-transparent w-12 h-full flex items-center justify-start pl-2 text-slate-400 hover:text-slate-700'
          aria-label='Scroll tabs left'
        >
          <ChevronLeftIcon className='w-5 h-5' />
        </button>
      )}

      <div
        ref={scrollContainerRef}
        className='flex overflow-x-auto scrollbar-hide no-scrollbar relative'
        onScroll={checkScroll}
        role='tablist'
        aria-label='Management modules'
      >
        {tabs.map((tab) => {
          const isActive = activeTab === tab.id;
          const Icon = tab.icon;
          return (
            <button
              key={tab.id}
              role='tab'
              aria-selected={isActive}
              aria-controls={`panel-${tab.id}`}
              id={`tab-${tab.id}`}
              onClick={() => onTabChange(tab.id)}
              className={`
                relative flex items-center gap-2 px-4 py-3 text-sm font-medium whitespace-nowrap transition-colors focus:outline-none focus-visible:ring-2 focus-visible:ring-[#e31f20] focus-visible:ring-inset
                ${isActive ? 'text-[#e31f20]' : 'text-slate-500 hover:text-slate-800 hover:bg-slate-50'}
              `}
            >
              <Icon className='w-4 h-4' aria-hidden='true' />
              {tab.label}

              {isActive && (
                <motion.div
                  layoutId='activeTabIndicator'
                  className='absolute bottom-0 left-0 right-0 h-0.5 bg-[#e31f20]'
                  initial={false}
                  transition={{
                    type: 'spring',
                    stiffness: 500,
                    damping: 30,
                  }}
                />
              )}
            </button>
          );
        })}
      </div>

      {showRightScroll && (
        <button
          onClick={() => scroll('right')}
          className='absolute right-0 top-1/2 -translate-y-1/2 z-10 bg-linear-to-l from-white via-white to-transparent w-12 h-full flex items-center justify-end pr-2 text-slate-400 hover:text-slate-700'
          aria-label='Scroll tabs right'
        >
          <ChevronRightIcon className='w-5 h-5' />
        </button>
      )}

      <style
        dangerouslySetInnerHTML={{
          __html: `
        .no-scrollbar::-webkit-scrollbar { display: none; }
        .no-scrollbar { -ms-overflow-style: none; scrollbar-width: none; }
      `,
        }}
      />
    </div>
  );
}
