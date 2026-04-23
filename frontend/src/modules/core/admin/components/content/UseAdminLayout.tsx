import React from 'react';
import { AdminHomeStatCards } from '../../../../../../stitch/samples/homepage';
import type { ModuleStat } from '../../type';
import TableView from './TableView';

interface UseAdminLayoutProps {
  moduleTitle: string;
  columns: any[]; // Define columns for TableView
  rows: any;
  moduleDescription?: string;
  isLoading: boolean;
  pagination: any;
  ModuleIcon: any;
  handleEdit: any;
  handlePageChange: any;
  onSearchAction?: (searchTerm: string) => void;
  ctaLabel: string;
  onCtaClick: () => void;
  stats?: ModuleStat[]; // Add stats prop to pass to AdminHomeStatCards
}

const UseAdminLayout = ({
  moduleTitle,
  stats,
  moduleDescription,
  columns,
  rows,
  isLoading = false,
  pagination,
  ModuleIcon,
  handleEdit,
  handlePageChange,
  onSearchAction,
  ctaLabel,
  onCtaClick,
}: UseAdminLayoutProps) => {
  return (
    <div className='flex flex-col gap-4 p-4'>
      <div className='p-2  '>
        <h1>{moduleTitle}</h1>
        <p>{moduleDescription}</p>
      </div>
      <br />
      <div className='flex flex-col gap-4 p-2'>
        <AdminHomeStatCards stats={stats} />
        <TableView
          columns={columns}
          rows={rows}
          isLoading={isLoading}
          pagination={pagination}
          ModuleIcon={ModuleIcon}
          handleEdit={handleEdit}
          handlePageChange={handlePageChange}
          onSearchAction={onSearchAction}
          ctaLabel={ctaLabel}
          onCtaClick={onCtaClick}
        />
      </div>
    </div>
  );
};

export default UseAdminLayout;
