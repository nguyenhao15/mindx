import z, { any } from 'zod';

export const proposalSchema = z.object({
  id: z.number(),
  maintenance: z.object(any).optional(),
  proposalDescription: z.string().min(1, 'Mô tả không được để trống'),
  proposalCost: z.number().min(0, 'Chi phí phải là số dương'),
  proposedBy: z.string().min(1, 'Người đề xuất không được để trống'),
  maintenanceId: z.number().min(1, 'Vui lòng chọn một bảo trì hợp lệ'),
  proposalStatus: z
    .enum([
      'PROPOSAL_PENDING',
      'PROPOSAL_ACCEPTED',
      'PROPOSAL_REJECTED',
      'PROPOSAL_CANCELLED',
    ])
    .default('PROPOSAL_PENDING'),
  createdDate: z.string().optional(),
  lastModifiedDate: z.string().optional(),
  createdBy: z.string().optional(),
  lastModifiedBy: z.string().optional(),
});

export const createProposalSchema = proposalSchema.omit({
  id: true,
  createdDate: true,
  lastModifiedDate: true,
  createdBy: true,
  lastModifiedBy: true,
  maintenance: true,
});

export const updateProposalSchema = proposalSchema.partial().omit({
  id: true,
  maintenanceId: true,
  createdDate: true,
  lastModifiedDate: true,
  createdBy: true,
  lastModifiedBy: true,
});

export const proposalNestOject = proposalSchema.pick({
  id: true,
  proposalDescription: true,
  proposalCost: true,
  proposedBy: true,
  proposalStatus: true,
  createdBy: true,
  lastModifiedBy: true,
  lastModifiedDate: true,
  createdDate: true,
});

export type ProposalNestObject = z.infer<typeof proposalNestOject>;
export type Proposal = z.infer<typeof proposalSchema>;
export type CreateProposalRequestDTO = z.infer<typeof createProposalSchema>;
export type UpdateProposalRequestDTO = z.infer<typeof updateProposalSchema>;
