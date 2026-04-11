import React from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import Pagination from './Pagination';
export interface Column<T> {
  key: keyof T | string;
  label: string;
  width?: string;
  render?: (item: T) => React.ReactNode;
}
interface DataTableProps<T> {
  columns: Column<T>[];
  data: T[];
  onEdit?: (item: T) => void;
  actionLabel?: string;
  pagination?: any;
  handlePageChange?: (value: number) => void;
}
export function DataTable<
  T extends {
    id: string | number;
  },
>({
  columns,
  data,
  onEdit,
  actionLabel = 'Edit',
  pagination,
  handlePageChange,
}: DataTableProps<T>) {
  return (
    <div className='w-full gap-5 p-2 overflow-x-auto bg-white border border-slate-100 rounded-xl shadow-sm'>
      <table className='w-full text-left border-collapse min-w-170 md:min-w-full'>
        <thead>
          <tr className='border-b border-slate-100 bg-slate-50/50'>
            {columns.map((col) => (
              <th
                key={String(col.key)}
                className='py-4 px-6 text-xs font-semibold text-slate-500 uppercase tracking-wider'
                style={{
                  width: col.width,
                }}
                scope='col'
              >
                {col.label}
              </th>
            ))}
            {onEdit && (
              <th
                className='py-4 px-6 text-xs font-semibold text-slate-500 uppercase tracking-wider text-right'
                scope='col'
              >
                Actions
              </th>
            )}
          </tr>
        </thead>
        <tbody>
          <AnimatePresence>
            {data.map((item) => (
              <motion.tr
                key={item.id}
                layout
                initial={{ opacity: 0, y: 10 }}
                animate={{ opacity: 1, y: 0 }}
                exit={{ opacity: 0, y: -10 }}
                transition={{ duration: 0.2 }}
                className='border-b border-slate-50 hover:bg-slate-50/80 transition-colors group'
              >
                {columns.map((col) => (
                  <td
                    key={String(col.key)}
                    className='py-4 px-6 text-sm text-slate-700'
                  >
                    {col.render
                      ? col.render(item)
                      : String(item[col.key as keyof T] || '')}
                  </td>
                ))}
                {onEdit && (
                  <td className='py-4 px-6 text-sm text-right'>
                    <button
                      onClick={() => onEdit(item)}
                      className='text-[#e31f20] font-medium px-3 py-1.5 rounded-md hover:bg-red-50 transition-colors opacity-0 group-hover:opacity-100 focus:opacity-100 focus:outline-none focus:ring-2 focus:ring-[#e31f20]/20'
                      aria-label='Edit item'
                    >
                      {actionLabel}
                    </button>
                  </td>
                )}
              </motion.tr>
            ))}
          </AnimatePresence>
        </tbody>
      </table>
      {pagination && handlePageChange && (
        <Pagination
          paginationData={pagination}
          onPageChange={handlePageChange}
        />
      )}
    </div>
  );
}
