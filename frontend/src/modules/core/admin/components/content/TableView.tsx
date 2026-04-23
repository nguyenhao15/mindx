import { ActionHeader } from '@/components/shared/ActionHeder';
import { DataTable } from '@/components/shared/DataTable';
import { EmptyState } from '@/components/shared/EmtyState';
import Loader from '@/components/shared/Loader';
import React from 'react';

interface TableViewProps {
  pagination: any;
  isLoading: boolean;
  ModuleIcon: any;
  columns: any[];
  rows: any[];
  handleEdit: (row: any) => void;
  handlePageChange: (page: number) => void;
  onSearchAction?: (query: string) => void;
  ctaLabel: string;
  onCtaClick: () => void;
}

const TableView = ({
  pagination,
  isLoading,
  ModuleIcon,
  columns,
  rows,
  handleEdit,
  handlePageChange,
  onSearchAction,
  ctaLabel,
  onCtaClick,
}: TableViewProps) => {
  return (
    <div className='m-2 animate-in fade-in duration-300'>
      <ActionHeader
        title='User Management'
        searchPlaceholder='Search users...'
        ctaLabel={ctaLabel}
        onSearch={onSearchAction}
        onCtaClick={onCtaClick}
      />
      {isLoading ? (
        <Loader text='Đang tải dữ liệu' />
      ) : rows.length === 0 ? (
        <EmptyState
          title='No Users Found'
          description='No users match your keyword. Try another search to continue.'
          icon={ModuleIcon}
        />
      ) : (
        <DataTable
          columns={columns}
          data={rows}
          actionLabel='Update'
          onEdit={handleEdit}
          pagination={pagination}
          handlePageChange={handlePageChange}
        />
      )}
    </div>
  );
};

export default TableView;
