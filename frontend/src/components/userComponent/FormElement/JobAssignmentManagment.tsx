import { useEffect, useState } from 'react';
import { JobAssignmentsSection } from './JobAssignmentSection';
import type { Assignment } from './JobAssignmentCard';
import { useFormContext } from 'react-hook-form';
import type {
  UserManagementDTO,
  WorkProfileType,
} from '@/validations/userSchema';

const JobAssignmentManagment = ({
  initialForm,
}: {
  initialForm: WorkProfileType[];
}) => {
  const { setValue } = useFormContext<UserManagementDTO>();

  const toAssignment = (
    profile: WorkProfileType,
    index: number,
  ): Assignment => ({
    id: String(index + 1),
    isPrimary: profile.isMainPosition ?? false,
    departmentId: profile.departmentId,
    positionCode: profile.positionCode,
    positionLevel: profile.positionLevel,
    isMainPosition: profile.isMainPosition ?? false,
    buAllowedList: profile.buAllowedList ?? [],
  });

  const [assignments, setAssignments] = useState<Assignment[]>(
    initialForm.length > 0
      ? initialForm.map(toAssignment)
      : [
          {
            id: '1',
            isPrimary: true,
            departmentId: '',
            positionCode: '',
            positionLevel: 0,
            isMainPosition: true,
            buAllowedList: [],
          },
        ],
  );

  const handleAddAssignment = () => {
    const newId = Math.random().toString(36).slice(2, 11);
    setAssignments((prev) => [
      ...prev,
      {
        id: newId,
        isPrimary: false,
        departmentId: '',
        positionCode: '',
        positionLevel: 0,
        isMainPosition: false,
        buAllowedList: [],
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
        filtered[0] = {
          ...filtered[0],
          isPrimary: true,
          isMainPosition: true,
        };
      }
      return filtered;
    });
  };

  useEffect(() => {
    const workProfileListPayload: WorkProfileType[] = assignments.map(
      (assignment) => ({
        departmentId: assignment.departmentId,
        positionCode: assignment.positionCode,
        positionLevel: Number(assignment.positionLevel),
        isMainPosition: assignment.isPrimary,
        buAllowedList: assignment.buAllowedList,
      }),
    );

    setValue('workProfileList', workProfileListPayload);
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
