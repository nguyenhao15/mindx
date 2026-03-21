import { useEffect, type ChangeEvent } from 'react';
import { TrashIcon } from 'lucide-react';
import { Switch } from '../../shared/Switch';
import ManualCustomCombobox from '@/components/input-elements/ManualCustomCombobox';
import TextInputField from '@/components/input-elements/TextInputField';
import { useDepartmentForForm } from '@/hookQueries/useDepartmentForForm';

export interface Assignment {
  id: string;
  isPrimary: boolean;
  departmentId: string;
  positionCode: string;
  positionLevel: number;
  isMainPosition: boolean;
  buAllowedList: string[];
}

interface JobAssignmentCardProps {
  errors: any; // You can replace 'any' with a more specific type based on your error structure
  assignment: Assignment;
  index: number;
  canRemove: boolean;
  onUpdate: (id: string, field: keyof Assignment, value: any) => void;
  onRemove: (id: string) => void;
}

export function JobAssignmentCard({
  errors,
  assignment,
  index,
  canRemove,
  onUpdate,
  onRemove,
}: JobAssignmentCardProps) {
  const departmentId = assignment.departmentId;
  const { buOptions, positionOptions, departmentOptions, isLoading } =
    useDepartmentForForm(departmentId);

  useEffect(() => {
    if (departmentId) {
      onUpdate(assignment.id, 'positionCode', '');
    }
  }, [departmentId, assignment.id]);

  return (
    <div className='bg-white rounded-xl shadow-sm border border-gray-200 p-8 transition-all hover:shadow-md'>
      {/* Header Row */}
      <div className='flex flex-col sm:flex-row sm:items-center justify-between gap-4 mb-6 pb-4 border-b border-gray-100'>
        <div className='flex flex-col sm:flex-row items-center gap-3'>
          <h3 className='text-lg font-semibold text-gray-900 my-auto'>
            Assignment #{index + 1}
          </h3>
          {assignment.isPrimary && (
            <span className='px-2.5 py-0.5 h-full rounded-full bg-red-100 text-[#e31f20] text-xs font-medium'>
              Primary
            </span>
          )}
        </div>

        <div className='flex items-center gap-6'>
          <Switch
            id={`primary-toggle-${assignment.id}`}
            checked={assignment.isPrimary}
            onChange={(checked) =>
              onUpdate(assignment.id, 'isPrimary', checked)
            }
            label='Set as Primary Position (Vị trí chính)'
          />

          {canRemove && (
            <button
              type='button'
              onClick={() => onRemove(assignment.id)}
              className='text-gray-400 hover:text-red-600 p-2 rounded-lg hover:bg-red-50 transition-colors focus:outline-none focus:ring-2 focus:ring-red-500/20'
              aria-label='Remove assignment'
            >
              <TrashIcon className='w-5 h-5' />
            </button>
          )}
        </div>
      </div>

      {/* Form Grid */}
      <div className='grid grid-cols-1 md:grid-cols-3 gap-6 mb-6'>
        <ManualCustomCombobox
          id={`departmentId`}
          label='Bộ phận làm việc'
          required
          isLoading={isLoading}
          onChange={(value) => {
            onUpdate(assignment.id, 'departmentId', value);
          }}
          defaultValue={assignment.departmentId}
          placeholder='Chọn mã phòng ban...'
          options={departmentOptions || []}
          errors={errors?.departmentId?.message}
        />

        <ManualCustomCombobox
          id={`positionCode`}
          label='Vị trí làm việc'
          required
          onChange={(value) => {
            onUpdate(assignment.id, 'positionCode', value);
          }}
          isLoading={isLoading}
          defaultValue={assignment.positionCode}
          options={positionOptions || []}
          placeholder='Mã vị trí...'
          errors={errors?.positionCode?.message}
        />

        <TextInputField
          id={`positionLevel`}
          label='Cấp bậc vị trí'
          required
          type='number'
          max={5}
          min={0}
          onChange={(event: ChangeEvent<HTMLInputElement>) => {
            const rawValue = Number(event.target.value);
            onUpdate(
              assignment.id,
              'positionLevel',
              Number.isNaN(rawValue) ? 0 : rawValue,
            );
          }}
          placeholder='Cấp bậc vị trí...'
          defaultValue={assignment.positionLevel}
          errors={errors}
        />
      </div>

      {/* Facility Multi-Select */}
      <div className='w-full items-center justify-center mx-auto '>
        <ManualCustomCombobox
          id={`buAllowedList`}
          label='Danh sách BU được phép'
          required
          multiple
          defaultValue={assignment.buAllowedList}
          isLoading={isLoading}
          options={buOptions || []}
          placeholder='Chọn danh sách BU được phép...'
          onChange={(value) => {
            onUpdate(assignment.id, 'buAllowedList', value);
          }}
          errors={errors?.buAllowedList?.message}
        />
      </div>
    </div>
  );
}
