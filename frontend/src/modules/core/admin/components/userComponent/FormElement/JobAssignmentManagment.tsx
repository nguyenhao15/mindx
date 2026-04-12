import { useEffect, useMemo, useState } from 'react';
import { JobAssignmentsSection } from './JobAssignmentSection';
import type { Assignment } from './JobAssignmentCard';
import { useFormContext } from 'react-hook-form';
import type {
  UserManagementFormInput,
  WorkProfileType,
} from '@/modules/core/auth/schemas/userSchema';

const JobAssignmentManagment = ({
  initialForm,
}: {
  initialForm: WorkProfileType[];
}) => {
  const { setValue } = useFormContext<UserManagementFormInput>();
  const [editingId, setEditingId] = useState<string | null>(null);

  const emptyAssignment = (id = '1'): Assignment => ({
    id,
    isPrimary: true,
    departmentId: '',
    positionCode: '',
    positionLevel: 0,
    isMainPosition: true,
    buAllowedList: [],
    isNew: true,
  });

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
    isNew: false,
  });

  const initialFormKey = useMemo(
    () => JSON.stringify(initialForm ?? []),
    [initialForm],
  );

  const [assignments, setAssignments] = useState<Assignment[]>(() =>
    initialForm.length > 0
      ? initialForm.map(toAssignment)
      : [emptyAssignment()],
  );

  const normalizePrimaryState = (items: Assignment[]) => {
    let hasPrimary = false;

    const normalized = items.map((item) => {
      const nextIsPrimary = item.isPrimary && !hasPrimary;
      hasPrimary = hasPrimary || nextIsPrimary;

      return {
        ...item,
        isPrimary: nextIsPrimary,
        isMainPosition: nextIsPrimary,
      };
    });

    if (!hasPrimary && normalized.length > 0) {
      normalized[0] = {
        ...normalized[0],
        isPrimary: true,
        isMainPosition: true,
      };
    }

    return normalized;
  };

  const toWorkProfileList = (items: Assignment[]): WorkProfileType[] => {
    return items.map((assignment) => ({
      departmentId: assignment.departmentId,
      positionCode: assignment.positionCode,
      positionLevel: Number(assignment.positionLevel),
      isMainPosition: assignment.isPrimary,
      buAllowedList: assignment.buAllowedList,
    }));
  };

  const commitAssignments = (items: Assignment[]) => {
    const normalized = normalizePrimaryState(items);

    setAssignments(normalized);
    setValue('workProfileList', toWorkProfileList(normalized), {
      shouldDirty: true,
      shouldValidate: true,
    });
  };

  useEffect(() => {
    const initialAssignments =
      initialForm.length > 0
        ? initialForm.map(toAssignment)
        : [emptyAssignment()];

    const normalized = normalizePrimaryState(initialAssignments);

    setAssignments(normalized);
    setEditingId(null);
    setValue('workProfileList', toWorkProfileList(normalized), {
      shouldDirty: false,
      shouldValidate: false,
    });
  }, [initialFormKey, setValue]);

  const handleAddAssignment = () => {
    if (editingId) {
      return;
    }

    const newId = Math.random().toString(36).slice(2, 11);
    const hasMain = assignments.some((item) => item.isPrimary);

    const nextAssignment: Assignment = {
      ...emptyAssignment(newId),
      isPrimary: !hasMain,
      isMainPosition: !hasMain,
      isNew: true,
    };

    setAssignments((prev) => [...prev, nextAssignment]);
    setEditingId(newId);
  };

  const handleStartEdit = (id: string) => {
    setAssignments((prev) => {
      const target = prev.find((item) => item.id === id);
      if (!target) {
        return prev;
      }

      // If user edits an existing item, drop all unsaved new items.
      if (!target.isNew) {
        return prev.filter((item) => !item.isNew);
      }

      return prev;
    });

    setEditingId(id);
  };

  const handleCancelEdit = (id: string) => {
    const target = assignments.find((item) => item.id === id);

    if (target?.isNew) {
      const filtered = assignments.filter((item) => item.id !== id);
      commitAssignments(filtered.length > 0 ? filtered : [emptyAssignment()]);
    }

    setEditingId(null);
  };

  const handleSaveAssignment = (nextAssignment: Assignment) => {
    const updated = assignments.map((item) =>
      item.id === nextAssignment.id
        ? { ...nextAssignment, isNew: false }
        : item,
    );

    commitAssignments(updated);
    setEditingId(null);
  };

  const handleRemoveAssignment = (id: string) => {
    const filtered = assignments.filter((a) => a.id !== id);
    commitAssignments(filtered.length > 0 ? filtered : [emptyAssignment()]);

    if (editingId === id) {
      setEditingId(null);
    }
  };

  return (
    <div className='p-4'>
      <JobAssignmentsSection
        assignments={assignments}
        onAdd={handleAddAssignment}
        editingId={editingId}
        onStartEdit={handleStartEdit}
        onCancelEdit={handleCancelEdit}
        onSave={handleSaveAssignment}
        onRemove={handleRemoveAssignment}
      />
    </div>
  );
};

export default JobAssignmentManagment;
