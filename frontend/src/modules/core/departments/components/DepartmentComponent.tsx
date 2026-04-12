import { useGetAllDepartments } from '@/modules/core/departments/hooks/useDepartmentHook';
import { safeString, toArray } from '@/utils/formatValue';
import { useMemo, useState } from 'react';
import { DataTable, type Column } from '@/components/shared/DataTable';
import Status from '@/components/shared/Status';
import { ActionHeader } from '@/components/shared/ActionHeder';
import Loader from '@/components/shared/Loader';
import ModalComponent from '@/components/shared/ModalComponent';
import { EmptyState } from '@/components/shared/EmtyState';
import { BriefcaseBusiness } from 'lucide-react';
import { useTypeQueryState } from '@/hooks/useTypeQueryState';
import { useDebouncedFilterSearch } from '@/utils/utilActions';
import DepartmentForm from './DepartmentForm';
import type { WorkingFieldObject } from '@/validations/workingFieldSchema';

type DepartmentFieldRow = {
  id: string;
  departmentName: string;
  departmentCode: string;
  description: string;
  active: boolean;
  status: string;
  isSecurity: boolean;
  workingFieldToString: string;
  workingFields: WorkingFieldObject[];
  iconSvg?: string;
};

const DepartmentComponent = () => {
  const { state, updateState } = useTypeQueryState();
  const [isModalOpen, setIsModalOpen] = useState(false);

  const { handleSearch: handleOnSearching } = useDebouncedFilterSearch({
    updateState,
    field: 'departmentName',
    operator: 'LIKE',
  });

  const { data, isLoading } = useGetAllDepartments(state);
  const [selectedField, setSelectedField] = useState<DepartmentFieldRow | null>(
    null,
  );

  const handlePageChange = (pageValue: number) => {
    updateState((prev) => ({
      ...prev,
      pagination: {
        ...prev.pagination,
        page: pageValue, // Convert to 0-based index for API
      },
    }));
  };

  const { content, ...rest } = data || {};

  const onEditAction = (item: DepartmentFieldRow) => {
    const itemHandler = {
      ...item,
      workingFields: item.workingFields.map((field) => ({
        value: safeString(field.id),
        ...field,
      })),
    };
    setIsModalOpen(true);
    setSelectedField(itemHandler);
  };

  const afterUpdate = () => {
    setIsModalOpen(false);
    setSelectedField(null);
  };

  const rows = useMemo<DepartmentFieldRow[]>(() => {
    const source = toArray(data);
    return source.map((item) => {
      const record = item as Record<string, unknown>;

      return {
        id: safeString(record.id ?? record._id),
        departmentName: safeString(record.departmentName),
        departmentCode: safeString(record.departmentCode),
        description: safeString(record.description),
        status: record.active ? 'Active' : 'Inactive',
        workingFieldToString: toArray(record.workingFields)
          .map((field) =>
            safeString((field as Record<string, unknown>).fieldName, ''),
          )
          .filter(Boolean)
          .join(', '),
        workingFields: toArray(record.workingFields),
        iconSvg: safeString(record.iconSvg, undefined),
        active: record.active as boolean,
        isSecurity: record.isSecurity as boolean,
      };
    });
  }, [data]);

  const columns: Column<DepartmentFieldRow>[] = [
    {
      key: 'departmentName',
      label: 'Department Name',
      render: (item) => (
        <span className='font-medium text-slate-900'>
          {item.departmentName}
        </span>
      ),
    },
    {
      key: 'departmentCode',
      label: 'Department Code',
    },
    {
      key: 'status',
      label: 'Status',
      render: (item) => <Status status={item.status} />,
    },
    {
      key: 'description',
      label: 'Description',
    },
  ];

  return (
    <div className='animate-in fade-in duration-300'>
      <ActionHeader
        title='Departments'
        searchPlaceholder='Search departments...'
        ctaLabel='Add Department'
        onSearch={handleOnSearching}
        onCtaClick={() => setIsModalOpen(true)}
      />
      {isLoading ? (
        <Loader text='Đang tải dữ liệu' />
      ) : rows.length === 0 ? (
        <EmptyState
          title='No Departments Found'
          description='No departments match your keyword. Try another search to continue.'
          icon={BriefcaseBusiness}
        />
      ) : (
        <>
          <DataTable
            onEdit={onEditAction}
            columns={columns}
            data={rows}
            actionLabel='Update'
            pagination={rest}
            handlePageChange={handlePageChange}
          />
        </>
      )}
      <ModalComponent
        open={isModalOpen}
        onClose={() => {
          setIsModalOpen(false);
          setSelectedField(null);
        }}
      >
        <DepartmentForm
          initialData={selectedField || undefined}
          onUpdate={afterUpdate}
        />
      </ModalComponent>
    </div>
  );
};

export default DepartmentComponent;
