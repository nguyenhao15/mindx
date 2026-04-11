import { getPaginationRange } from '@/constants/getPagintaionRange';

interface PaginationProps {
  paginationData: {
    pageNumber: number;
    pageSize: number;
    totalElements: number;
    totalPages: number;
    lastPage: boolean;
  };
  onPageChange: (page: number) => void;
}

const Pagination = ({ paginationData, onPageChange }: PaginationProps) => {
  const { pageNumber, pageSize, totalElements, totalPages, lastPage } =
    paginationData;
  const currentPage = pageNumber + 1; // Convert to 1-based index for display
  const paginationRange =
    getPaginationRange({
      currentPage,
      totalPages,
    }) || [];

  if (currentPage === 0 || paginationRange.length < 2) return null;

  const handlePageClick = (page: number | 'DOTS') => {
    if (page === 'DOTS') return;
    onPageChange(page - 1);
  };
  return (
    <div className='flex items-center justify-between px-4 py-3 bg-white border-t border-gray-200 sm:px-6'>
      <div className='flex justify-between flex-1 sm:hidden'>
        <button
          onClick={() => handlePageClick(currentPage - 1)}
          disabled={currentPage === 1}
          className='relative inline-flex items-center px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gray-50 disabled:opacity-50'
        >
          Previous
        </button>
        <button
          onClick={() => handlePageClick(currentPage + 1)}
          disabled={lastPage}
          className='relative ml-3 inline-flex items-center px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gray-50 disabled:opacity-50'
        >
          Next
        </button>
      </div>

      <div className='hidden sm:flex-1 sm:flex sm:items-center sm:justify-between'>
        <div>
          <p className='text-sm text-gray-700'>
            Showing{' '}
            <span className='font-medium'>{pageNumber * pageSize + 1}</span> to{' '}
            <span className='font-medium'>
              {Math.min((pageNumber + 1) * pageSize, totalElements)}
            </span>{' '}
            of <span className='font-medium'>{totalElements}</span> results
          </p>
        </div>
        <div>
          <nav className='flex items-center gap-1 mx-2' aria-label='Pagination'>
            {paginationRange.map((page, index) => {
              if (page === 'DOTS') {
                return (
                  <span
                    key={`dots-${index}`}
                    className='relative inline-flex items-center px-4 py-2 bg-white text-sm font-medium text-gray-700'
                  >
                    ...
                  </span>
                );
              }
              return (
                <button
                  key={index}
                  onClick={() => handlePageClick(page)}
                  className={`h-8 w-8 flex items-center cursor-pointer justify-center rounded-lg  text-slate-700 text-sm font-medium ${
                    page === currentPage
                      ? 'z-10 bg-[#e31f20] text-white border-[#e31f20] hover:bg-[#e31f20]/50'
                      : 'bg-white border-gray-300 text-gray-500 hover:bg-gray-50'
                  }`}
                >
                  {page}
                </button>
              );
            })}
          </nav>
        </div>
      </div>
    </div>
  );
};

export default Pagination;
