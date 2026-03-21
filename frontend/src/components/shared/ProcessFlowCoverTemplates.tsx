import React from 'react';

export interface ProcessFlowCoverTemplate {
  id: string;
  label: string;
  render: () => React.ReactNode;
}

/**
 * 10 mẫu cover cho Process Flow Card
 * Sử dụng brand-primary với các độ sáng tối khác nhau
 * Phù hợp với chủ đề quy trình làm việc
 */
export const ProcessFlowCoverTemplates: ProcessFlowCoverTemplate[] = [
  // Template 1: Văn bản chồng lấp (Document Stack)
  {
    id: 'document-stack',
    label: 'Document Stack',
    render: () => (
      <div className='flex gap-2'>
        <div className='w-12 h-16 bg-brand-primary/25 rounded border border-brand-primary/40'></div>
        <div className='w-12 h-16 bg-brand-primary/15 rounded border border-brand-primary/30 translate-y-4'></div>
        <div className='w-12 h-16 bg-brand-primary/8 rounded border border-brand-primary/15 translate-y-2'></div>
      </div>
    ),
  },

  // Template 2: Quy trình đơn tuyến (Linear Process)
  {
    id: 'linear-process',
    label: 'Linear Process',
    render: () => (
      <div className='flex items-center gap-1'>
        <div className='size-6 rounded-full border-2 border-brand-primary/50'></div>
        <div className='w-8 h-0.5 bg-brand-primary/35'></div>
        <div className='size-8 rounded border-2 border-brand-primary/40 rotate-45'></div>
        <div className='w-8 h-0.5 bg-brand-primary/35'></div>
        <div className='size-6 rounded-full border-2 border-brand-primary/50'></div>
      </div>
    ),
  },

  // Template 3: Lưới dữ liệu (Data Grid)
  {
    id: 'data-grid',
    label: 'Data Grid',
    render: () => (
      <div className='grid grid-cols-3 gap-2'>
        <div className='size-4 bg-brand-primary/25 rounded-sm'></div>
        <div className='size-4 bg-brand-primary/45 rounded-sm'></div>
        <div className='size-4 bg-brand-primary/25 rounded-sm'></div>
        <div className='size-4 bg-brand-primary/15 rounded-sm'></div>
        <div className='size-4 bg-brand-primary/35 rounded-sm'></div>
        <div className='size-4 bg-brand-primary/15 rounded-sm'></div>
      </div>
    ),
  },

  // Template 4: Quy trình ngang (Horizontal Bars)
  {
    id: 'horizontal-bars',
    label: 'Horizontal Bars',
    render: () => (
      <div className='flex flex-col gap-1.5'>
        <div className='w-16 h-2 bg-brand-primary/35 rounded-full'></div>
        <div className='w-12 h-2 bg-brand-primary/25 rounded-full'></div>
        <div className='w-14 h-2 bg-brand-primary/15 rounded-full'></div>
      </div>
    ),
  },

  // Template 5: Mạng lưới kết nối (Network Nodes)
  {
    id: 'network-nodes',
    label: 'Network Nodes',
    render: () => (
      <div className='relative w-16 h-16 flex items-center justify-center'>
        <div className='absolute size-2.5 rounded-full bg-brand-primary/50 left-0 top-0'></div>
        <div className='absolute size-2.5 rounded-full bg-brand-primary/35 right-0 top-0'></div>
        <div className='absolute size-3 rounded-full border-2 border-brand-primary/45 top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2'></div>
        <div className='absolute size-2.5 rounded-full bg-brand-primary/35 left-0 bottom-0'></div>
        <div className='absolute size-2.5 rounded-full bg-brand-primary/50 right-0 bottom-0'></div>
      </div>
    ),
  },

  // Template 6: Cột tăng giảm (Rising Columns)
  {
    id: 'rising-columns',
    label: 'Rising Columns',
    render: () => (
      <div className='flex items-end gap-1.5 h-12'>
        <div className='w-2 h-6 bg-brand-primary/25 rounded-t'></div>
        <div className='w-2 h-9 bg-brand-primary/40 rounded-t'></div>
        <div className='w-2 h-7 bg-brand-primary/30 rounded-t'></div>
        <div className='w-2 h-8 bg-brand-primary/20 rounded-t'></div>
        <div className='w-2 h-10 bg-brand-primary/35 rounded-t'></div>
      </div>
    ),
  },

  // Template 7: Vòng tròn kồng chồng (Concentric Circles)
  {
    id: 'concentric-circles',
    label: 'Concentric Circles',
    render: () => (
      <div className='relative w-16 h-16 flex items-center justify-center'>
        <div className='absolute rounded-full border-2 border-brand-primary/25 w-16 h-16'></div>
        <div className='absolute rounded-full border-2 border-brand-primary/40 w-12 h-12'></div>
        <div className='absolute rounded-full bg-brand-primary/35 w-4 h-4'></div>
      </div>
    ),
  },

  // Template 8: Mũi tên luân chuyển (Circular Arrows)
  {
    id: 'circular-arrows',
    label: 'Circular Arrows',
    render: () => (
      <div className='relative w-16 h-16 flex items-center justify-center'>
        <div className='absolute w-12 h-12 rounded-full border-2 border-dashed border-brand-primary/40'></div>
        <div className='absolute w-3 h-3 bg-brand-primary/50 rounded-full top-0 left-1/2 -translate-x-1/2'></div>
        <div className='absolute w-3 h-3 bg-brand-primary/35 rounded-full bottom-0 right-0'></div>
        <div className='absolute w-3 h-3 bg-brand-primary/25 rounded-full bottom-0 left-0'></div>
      </div>
    ),
  },

  // Template 9: Tầng lớp gradient (Layered Gradient)
  {
    id: 'layered-gradient',
    label: 'Layered Gradient',
    render: () => (
      <div className='flex flex-col justify-between h-12'>
        <div className='w-20 h-1.5 bg-brand-primary/50 rounded-full'></div>
        <div className='w-16 h-1.5 bg-brand-primary/40 rounded-full'></div>
        <div className='w-12 h-1.5 bg-brand-primary/25 rounded-full'></div>
        <div className='w-16 h-1.5 bg-brand-primary/30 rounded-full'></div>
      </div>
    ),
  },

  // Template 10: Hộp chứa lồng (Nested Boxes)
  {
    id: 'nested-boxes',
    label: 'Nested Boxes',
    render: () => (
      <div className='relative w-16 h-16 flex items-center justify-center'>
        <div className='absolute w-16 h-16 border-2 border-brand-primary/25 rounded'></div>
        <div className='absolute w-12 h-12 border-2 border-brand-primary/40 rounded'></div>
        <div className='absolute w-8 h-8 bg-brand-primary/30 rounded'></div>
      </div>
    ),
  },
];

/**
 * Component để render một mẫu cover theo id hoặc ngẫu nhiên
 */
interface ProcessFlowCoverProps {
  templateId?: string;
  random?: boolean;
  onClick?: () => void;
}

export const ProcessFlowCover: React.FC<ProcessFlowCoverProps> = ({
  templateId,
  onClick,
  random = false,
}) => {
  let template: ProcessFlowCoverTemplate;

  if (random) {
    const randomIndex = Math.floor(
      Math.random() * ProcessFlowCoverTemplates.length,
    );
    template = ProcessFlowCoverTemplates[randomIndex];
  } else if (templateId) {
    template =
      ProcessFlowCoverTemplates.find((t) => t.id === templateId) ||
      ProcessFlowCoverTemplates[0];
  } else {
    template = ProcessFlowCoverTemplates[0];
  }

  return (
    <div
      className='h-32 bg-slate-50 cursor-pointer dark:bg-slate-900 relative p-4 flex items-center justify-center overflow-hidden'
      onClick={onClick}
    >
      {template.render()}
      <div className='absolute inset-0 bg-linear-to-t from-white/80 dark:from-slate-800/80 to-transparent opacity-0 group-hover:opacity-100 transition-opacity flex items-center justify-center'>
        <span className='bg-white dark:bg-slate-700 px-3 py-1.5 rounded-full text-[10px] font-bold shadow-sm'>
          VIEW FLOW
        </span>
      </div>
    </div>
  );
};

/**
 * Utility function để lấy template ngẫu nhiên
 */
export const getRandomTemplate = (): ProcessFlowCoverTemplate => {
  return ProcessFlowCoverTemplates[
    Math.floor(Math.random() * ProcessFlowCoverTemplates.length)
  ];
};

/**
 * Utility function để lấy template theo id
 */
export const getTemplateById = (
  id: string,
): ProcessFlowCoverTemplate | undefined => {
  return ProcessFlowCoverTemplates.find((t) => t.id === id);
};
