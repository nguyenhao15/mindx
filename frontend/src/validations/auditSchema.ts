import { CHANGE_TYPE, MODULENUM } from '@/constants/module-const';
import z from 'zod';

export const AuditUpdateJPASchema = z.object({
  identifier: z.string().min(1, 'Identifier is required'),
  changeType: z.enum(CHANGE_TYPE).default('UPDATE'),
  updateValue: z.string().min(1, 'Update value is required'),
  module: z.enum(MODULENUM),
  description: z.string().min(1, 'Description is required'),
});

export type AuditUpdateJPADTO = z.infer<typeof AuditUpdateJPASchema>;
