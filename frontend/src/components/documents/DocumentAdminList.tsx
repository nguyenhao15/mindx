import { useGetAllProcessFlows } from '@/hookQueries/useProcessFlowHooks';
import { useTypeQueryState } from '@/hooks/useTypeQueryState';
import { safeString, toArray } from '@/utils/formatValue';
import { useDebouncedFilterSearch } from '@/utils/utilActions';
import { useMemo, useState } from 'react';
import { DataTable, type Column } from '../shared/DataTable';
import Status from '../shared/Status';
import { ActionHeader } from '../shared/ActionHeder';
import { EmptyState } from '../shared/EmtyState';
import Loader from '../shared/Loader';
import { Workflow } from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import ModalComponent from '../shared/ModalComponent';
import ProcessDetail from '@/pages/ProcessDetail';

const DocumentAdminList = () => {
  const navigate = useNavigate();
  const { state, updateState } = useTypeQueryState();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const { data, isLoading } = useGetAllProcessFlows(state);
  const { handleSearch } = useDebouncedFilterSearch({
    updateState,
    field: 'title',
    operator: 'LIKE',
  });
  const { content, ...pagination } = data || {};
  const [selectedItem, setSelectedItem] = useState(null);
  const rows = useMemo(() => {
    const source = toArray<Record<string, any>>(content);
    return source.map((item) => ({
      ...item,
      id: safeString(item.id),
      title: safeString(item.title),
      description: safeString(item.description),
      active: Boolean(item.active),
      activeStatus: Boolean(item.active) ? 'ACTIVE' : 'INACTIVE',
      isOfficeial: Boolean(item.isOfficeial),
      accessRule: item.accessRule,
      processStatus: safeString(item.processStatus),
      createdBy: safeString(item.createdBy),
    }));
  }, [content]);

  const columns: Column<any>[] = [
    {
      key: 'title',
      label: 'Title',
      render: (item: any) => (
        <span className='font-medium text-slate-900'>{item.title}</span>
      ),
    },
    {
      key: 'description',
      label: 'Description',
      render: (item: any) => (
        <span className='font-medium text-slate-900'>{item.description}</span>
      ),
    },
    {
      key: 'active',
      label: 'Status',
      render: (item: any) => <Status status={item.activeStatus} />,
    },
    {
      key: 'createdBy',
      label: 'Author',
      render: (item: any) => (
        <span className='font-medium text-slate-900'>{item.createdBy}</span>
      ),
    },
  ];

  const handlePreview = (item: any) => {
    setSelectedItem(item);
    navigate('#', { state: { processItem: item } });
    setIsModalOpen(true);
  };

  return (
    <div className='animate-in fade-in duration-300'>
      <ActionHeader
        title='Documents'
        searchPlaceholder='Search documents...'
        ctaLabel='Add Document'
        onSearch={handleSearch}
        onCtaClick={() => navigate('/create-document')}
      />
      {isLoading ? (
        <Loader text='Đang tải dữ liệu' />
      ) : rows.length === 0 ? (
        <EmptyState
          title='No Documents Found'
          description='No documents match your keyword. Try another search to continue.'
          icon={Workflow}
        />
      ) : (
        <DataTable
          columns={columns}
          data={rows}
          pagination={pagination}
          onEdit={handlePreview}
        />
      )}
      <ModalComponent open={isModalOpen} onClose={() => setIsModalOpen(false)}>
        <div className='p-2 bg-white'>
          <ProcessDetail viewMode={'admin'} />
        </div>
      </ModalComponent>
    </div>
  );
};

export default DocumentAdminList;
