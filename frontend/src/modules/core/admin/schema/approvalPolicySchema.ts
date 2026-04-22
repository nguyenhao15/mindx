import z from 'zod';

export const approvalPolicySchema = z.object({
  id: z.string(),
  module: z.string().min(1, 'Module is required'),
  targetStatus: z.string(),
  approverRoles: z.array(z.string()),
  requesterPosition: z.string(),
  allowType: z.string().min(1, 'Allow type is required'),
  isActive: z.boolean(),
  allowValue: z.string().min(1, 'Allow value is required'),
  description: z.string().nullable(),
  priority: z.number().int().nonnegative(),
  workFlowId: z.number().int().nonnegative(),
});

export const createApprovalPolicySchema = approvalPolicySchema
  .omit({
    id: true,
  })
  .pick({
    module: true,
    workFlowId: true,
    description: true,
    isActive: true,
    requesterPosition: true,
    priority: true,
    targetStatus: true,
    allowType: true,
    allowValue: true,
  });

export type ApprovalPolicy = z.infer<typeof approvalPolicySchema>;
export type CreateApprovalPolicy = z.infer<typeof createApprovalPolicySchema>;
