import ProcessCard from '@/modules/documentations/document/components/processFlow/ProcessCard';
import { useGetActiveProcessFlows } from '@/modules/documentations/document/hooks/useProcessFlowHooks';

import { useNavigate } from 'react-router-dom';

export function ProcessSection() {
  const navigate = useNavigate();
  const { data: processFlows } = useGetActiveProcessFlows(
    {
      filters: [],
      pagination: {
        page: 0,
        size: 4,
        sortOrder: [
          {
            property: 'createdDate',
            direction: 'DESC',
          },
        ],
      },
    },
    true,
  );

  const handleOnCardClick = (item: (typeof processFlows.content)[number]) => {
    navigate(`/documents/tag-flow/${item.id}`, {
      state: { processItem: item },
    });
  };

  return (
    <div className='w-full'>
      <div className='flex flex-col md:flex-row items-start md:items-center justify-between mb-8 gap-4'>
        <h3 className='text-xl font-bold'>Latest Process Flows</h3>
      </div>
      <div className='min-w-3xl grid grid-cols-1 sm:grid-cols-2  lg:grid-cols-4 gap-6'>
        {processFlows?.content?.map((flow: any) => (
          <ProcessCard
            key={flow.id}
            data={flow}
            onClick={() => handleOnCardClick(flow)}
          />
        ))}
      </div>
    </div>
  );
}
