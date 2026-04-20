import { motion, AnimatePresence } from 'framer-motion';
import { JobAssignmentCard, type Assignment } from './JobAssignmentCard';
import { useFormContext } from 'react-hook-form';

interface JobAssignmentsSectionProps {
  assignments: Assignment[];
  onAdd: () => void;
  editingId: string | null;
  onStartEdit: (id: string) => void;
  onCancelEdit: (id: string) => void;
  onSave: (data: Assignment) => void;
  onRemove: (id: string) => void;
}
export function JobAssignmentsSection({
  assignments,
  onAdd,
  editingId,
  onStartEdit,
  onCancelEdit,
  onSave,
  onRemove,
}: JobAssignmentsSectionProps) {
  const {
    formState: { errors },
  } = useFormContext();
  const workProfileErrors = (errors as any)?.workProfileList;

  return (
    <section className='space-y-4'>
      <div>
        <h2 className='text-lg font-semibold text-gray-900'>Job Assignments</h2>
        <p className='text-sm text-gray-500'>
          Define the user's roles, departments, and facility access.
        </p>
      </div>

      <div className='space-y-6'>
        <AnimatePresence initial={false}>
          {assignments.map((assignment, index) => (
            <motion.div
              key={assignment.id}
              initial={{
                opacity: 0,
                height: 0,
                y: 20,
              }}
              animate={{
                opacity: 1,
                height: 'auto',
                y: 0,
              }}
              exit={{
                opacity: 0,
                height: 0,
                y: -20,
                overflow: 'hidden',
              }}
              transition={{
                duration: 0.3,
                ease: 'easeInOut',
              }}
            >
              <JobAssignmentCard
                errors={workProfileErrors?.[index]}
                assignment={assignment}
                index={index}
                canRemove={index > 0} // First assignment cannot be deleted
                onStartEdit={onStartEdit}
                onCancelEdit={onCancelEdit}
                onSave={onSave}
                onRemove={onRemove}
              />
            </motion.div>
          ))}
        </AnimatePresence>
      </div>
    </section>
  );
}
