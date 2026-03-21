import { useEffect, useState } from 'react';
import { JobAssignmentsSection } from './JobAssignmentSection';
import type { Assignment } from './JobAssignmentCard';
import { useFormContext } from 'react-hook-form';

const JobAssignmentManagment = ({
  initialForm,
}: {
  initialForm: Assignment[];
}) => {
  const { setValue } = useFormContext();

  const [assignments, setAssignments] = useState<Assignment[]>(
    initialForm.length > 0
      ? initialForm
      : [
          {
            id: '1',
            isPrimary: true,
            departmentId: '',
            positionCode: '',
            positionLevel: 0,
            isMainPosition: false,
            buAllowedList: '',
          },
        ],
  );

  const handleAddAssignment = () => {
    const newId = Math.random().toString(36).substr(2, 9);
    setAssignments((prev) => [
      ...prev,
      {
        id: newId,
        isPrimary: false,
        departmentId: '',
        positionCode: '',
        positionLevel: 0,
        isMainPosition: false,
        buAllowedList: '',
      },
    ]);
  };

  const handleUpdateAssignment = (
    id: string,
    field: keyof Assignment,
    value: any,
  ) => {
    setAssignments((prev) => {
      return prev.map((assignment) => {
        // Handle primary toggle logic: only one can be primary
        if (field === 'isPrimary' && value === true) {
          if (assignment.id === id) {
            return {
              ...assignment,
              [field]: value,
              isMainPosition: value,
            };
          }
          return {
            ...assignment,
            isPrimary: false,
            isMainPosition: false,
          };
        }
        // Handle normal updates
        if (assignment.id === id) {
          return {
            ...assignment,
            [field]: value,
          };
        }
        return assignment;
      });
    });
  };

  const handleRemoveAssignment = (id: string) => {
    setAssignments((prev) => {
      const filtered = prev.filter((a) => a.id !== id);
      // If we removed the primary assignment, make the first remaining one primary
      const hasPrimary = filtered.some((a) => a.isPrimary);
      if (!hasPrimary && filtered.length > 0) {
        filtered[0].isPrimary = true;
      }
      return filtered;
    });
  };

  useEffect(() => {
    setValue('workProfileList', assignments);
  }, [assignments, setValue]);

  return (
    <div className='p-4'>
      <JobAssignmentsSection
        assignments={assignments}
        onAdd={handleAddAssignment}
        onUpdate={handleUpdateAssignment}
        onRemove={handleRemoveAssignment}
      />
    </div>
  );
};

export default JobAssignmentManagment;
