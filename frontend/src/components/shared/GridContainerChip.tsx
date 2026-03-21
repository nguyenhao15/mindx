const GridContainerChip = ({
  children,
  emptyMessage = 'No items to display',
}: {
  children: any;
  emptyMessage?: string;
}) => {
  return (
    <div className='flex flex-wrap gap-2 px-2 py-3 border-2 border-gray-200 rounded-lg max-h-3xl overflow-auto'>
      {children.length > 0 ? (
        children
      ) : (
        <div className='w-full text-center text-gray-500 py-4'>
          {emptyMessage}
        </div>
      )}
    </div>
  );
};

export default GridContainerChip;
