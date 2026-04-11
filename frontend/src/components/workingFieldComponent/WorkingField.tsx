import { useGetWorkingFieldList } from '@/hookQueries/useWorkingFieldHook';
import { safeString, toArray } from '@/utils/formatValue';
import React, { useMemo, useState } from 'react';
import { DataTable, type Column } from '../shared/DataTable';
import ModalComponent from '../shared/ModalComponent';
import { ActionHeader } from '../shared/ActionHeder';
import Loader from '../shared/Loader';
import { EmptyState } from '../shared/EmtyState';
import { BriefcaseBusiness } from 'lucide-react';

import Status from '../shared/Status';
import WorkingFieldForm from './WorkingFieldForm';

type WorkingFieldRow = {
  id: string | number;
  fieldName: string;
  fieldCode: string;
  active: string;
  description: string;
};

const WorkingField = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [query, setQuery] = useState('');
  const { data, isLoading } = useGetWorkingFieldList();
  const [selectedField, setSelectedField] = useState<WorkingFieldRow | null>(
    null,
  );

  const onEditAction = (item: WorkingFieldRow) => {
    const itemFormat = {
      ...item,
      active: item.active.toString() === 'Active' ? 'true' : 'false',
    };
    setSelectedField(itemFormat);
    setIsModalOpen(true);
  };

  const afterUpdate = () => {
    setIsModalOpen(false);
    setSelectedField(null);
  };

  const rows = useMemo<WorkingFieldRow[]>(() => {
    const source = toArray(data);
    return source.map((item) => {
      const record = item as Record<string, unknown>;
      const isActive = Boolean(record.active);

      return {
        id: safeString(record.id ?? record._id),
        fieldName: safeString(record.fieldName),
        fieldCode: safeString(record.fieldCode),
        active: isActive ? 'Active' : 'Inactive',
        description: safeString(record.description),
      };
    });
  }, [data]);

  const columns: Column<WorkingFieldRow>[] = [
    {
      key: 'fieldName',
      label: 'Field Name',
      render: (item) => (
        <span className='font-medium text-slate-900'>{item.fieldName}</span>
      ),
    },
    {
      key: 'fieldCode',
      label: 'Field Code',
    },
    {
      key: 'active',
      label: 'Status',
      render: (item) => <Status status={item.active} />,
    },
    {
      key: 'description',
      label: 'Description',
    },
  ];

  const filteredRows = useMemo(() => {
    const normalizedQuery = query.trim().toLowerCase();
    if (!normalizedQuery || normalizedQuery.length < 1) {
      return rows;
    }

    return rows.filter(
      (item) =>
        item.fieldName.toLowerCase().includes(normalizedQuery) ||
        item.fieldCode.toLowerCase().includes(normalizedQuery) ||
        item.active.toLowerCase().includes(normalizedQuery) ||
        item.description.toLowerCase().includes(normalizedQuery),
    );
  }, [rows, query]);

  return (
    <div className='animate-in fade-in duration-300'>
      <ActionHeader
        title='Working Fields'
        searchPlaceholder='Search working fields...'
        ctaLabel='Add Working Field'
        onSearch={setQuery}
        onCtaClick={() => setIsModalOpen(true)}
      />
      {isLoading ? (
        <Loader text='Đang tải dữ liệu' />
      ) : filteredRows.length === 0 ? (
        <EmptyState
          title='No Working Fields Found'
          description='No working fields match your keyword. Try another search to continue.'
          icon={BriefcaseBusiness}
        />
      ) : (
        <DataTable
          onEdit={onEditAction}
          columns={columns}
          data={filteredRows}
          actionLabel='Update'
        />
      )}
      <ModalComponent
        open={isModalOpen}
        onClose={() => {
          setIsModalOpen(false);
          setSelectedField(null);
        }}
      >
        <WorkingFieldForm initialData={selectedField} onUpdate={afterUpdate} />
        {/* Modal content for adding/editing working field goes here */}
      </ModalComponent>
    </div>
  );
};

export default WorkingField;
