import { PlusIcon } from 'lucide-react';
import { motion, AnimatePresence } from 'framer-motion';
import { JobAssignmentCard, type Assignment } from './JobAssignmentCard';
import { useFormContext } from 'react-hook-form';

interface JobAssignmentsSectionProps {
  assignments: Assignment[];
  onAdd: () => void;
  onUpdate: (id: string, field: keyof Assignment, value: any) => void;
  onRemove: (id: string) => void;
}
export function JobAssignmentsSection({
  assignments,
  onAdd,
  onUpdate,
  onRemove,
}: JobAssignmentsSectionProps) {
  const {
    formState: { errors },
  } = useFormContext();

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
                errors={errors.workProfileList?.[index]}
                assignment={assignment}
                index={index}
                canRemove={index > 0} // First assignment cannot be deleted
                onUpdate={onUpdate}
                onRemove={onRemove}
              />
            </motion.div>
          ))}
        </AnimatePresence>

        <motion.button
          type='button'
          onClick={onAdd}
          whileHover={{
            scale: 1.01,
          }}
          whileTap={{
            scale: 0.99,
          }}
          className='w-full py-4 flex items-center justify-center gap-2 bg-white border-2 border-dashed border-[#e31f20]/40 text-[#e31f20] rounded-xl font-medium hover:bg-red-50 hover:border-[#e31f20] transition-colors focus:outline-none focus:ring-2 focus:ring-[#e31f20]/20'
        >
          <PlusIcon className='w-5 h-5' />
          Add Another Assignment
        </motion.button>
      </div>
    </section>
  );
}
