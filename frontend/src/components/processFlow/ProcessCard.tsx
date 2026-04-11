import React from 'react';
import { ProcessFlowCover } from '../shared/ProcessFlowCoverTemplates';

interface ProcessCardProps {
  data: any;
  onClick?: (data: any) => void;
}

const ProcessCard: React.FC<ProcessCardProps> = ({ data, onClick }) => {
  const handleOnClick = () => {
    if (onClick) {
      onClick(data);
    }
  };

  return (
    <div>
      {/* Header: Title & Status */}
      <div className='bg-white dark:bg-slate-800 border border-slate-100 dark:border-slate-700 rounded-2xl overflow-hidden group shadow-sm hover:shadow-lg transition-all'>
        <ProcessFlowCover onClick={handleOnClick} random />
        <div className='p-4 flex flex-col gap-2'>
          <h5 className='text-sm font-bold mb-1'>{data.title}</h5>
          <p className='text-xs text-slate-500'>{data.description}</p>
          <div className='flex flex-row flex-wrap gap-3 mt-3'>
            {data.tagIdValues.map((val: any) => (
              <span
                key={val.id}
                className='px-2 py-1 bg-blue-50 text-blue-600 text-xs rounded border border-blue-100'
              >
                {val.tagTitle}
              </span>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProcessCard;
