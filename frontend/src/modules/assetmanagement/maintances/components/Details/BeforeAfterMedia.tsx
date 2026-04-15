import { Camera, ImageIcon, Video } from 'lucide-react';

type MediaItem = {
  id: string;
  title: string;
  type: 'image' | 'video';
  src: string;
};

interface BeforeAfterMediaProps {
  before: MediaItem[];
  after: MediaItem[];
}

const MediaCard = ({
  title,
  items,
  emptyText,
}: {
  title: string;
  items: MediaItem[];
  emptyText: string;
}) => {
  return (
    <article className='bg-white rounded-2xl border border-slate-100 p-5 sm:p-6'>
      <h3 className='text-sm font-semibold text-slate-800'>{title}</h3>
      {items.length === 0 ? (
        <div className='mt-4 rounded-xl border border-dashed border-slate-200 bg-slate-50 p-5 text-center'>
          <Camera className='mx-auto text-slate-300' size={20} />
          <p className='mt-2 text-xs text-slate-500'>{emptyText}</p>
        </div>
      ) : (
        <div className='mt-4 grid grid-cols-1 sm:grid-cols-2 gap-3'>
          {items.map((item) => (
            <div
              key={item.id}
              className='overflow-hidden rounded-xl bg-slate-100'
            >
              <img
                src={item.src}
                alt={item.title}
                className='h-36 w-full object-cover'
              />
              <div className='flex items-center gap-1.5 p-2.5'>
                {item.type === 'video' ? (
                  <Video size={14} className='text-slate-500' />
                ) : (
                  <ImageIcon size={14} className='text-slate-500' />
                )}
                <span className='text-xs text-slate-600 truncate'>
                  {item.title}
                </span>
              </div>
            </div>
          ))}
        </div>
      )}
    </article>
  );
};

const BeforeAfterMedia = ({ before, after }: BeforeAfterMediaProps) => {
  return (
    <section className='grid grid-cols-1 xl:grid-cols-2 gap-4'>
      <MediaCard
        title='Tình trạng khi báo cáo'
        items={before}
        emptyText='Chưa có hình ảnh/video ban đầu'
      />
      <MediaCard
        title='Nghiệm thu sau sửa chữa'
        items={after}
        emptyText='Chưa cập nhật media nghiệm thu'
      />
    </section>
  );
};

export default BeforeAfterMedia;
