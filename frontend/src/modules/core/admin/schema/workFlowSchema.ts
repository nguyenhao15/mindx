import z, { any } from 'zod';

export const workFlowSchema = z.object({
  id: z.number().optional(),
  module: z.string().min(1, 'Module is required'),
  fromStatus: z.string().min(1, 'From status is required'),
  toStatus: z.string().min(1, 'To status is required'),
  labelName: z.string().min(1, 'Label name is required'),
  operator: z.enum(['EQ', 'NEQ']).default('EQ'),
  actionType: z.string().min(1, 'Action type is required'),
  description: z.string().optional(),
  enabled: z.boolean().default(true),
  approvalPolicyEntity: z.array(any()).optional(),
});

export const createWorkFlowSchema = workFlowSchema.omit({ id: true }).pick({
  module: true,
  fromStatus: true,
  toStatus: true,
  labelName: true,
  actionType: true,
  description: true,
  enabled: true,
  operator: true,
});

export type WorkFlowFormData = z.infer<typeof workFlowSchema>;
export type CreateWorkFlowFormData = z.infer<typeof createWorkFlowSchema>;
