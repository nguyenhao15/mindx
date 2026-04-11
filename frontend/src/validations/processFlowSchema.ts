import { z } from 'zod';
// 1. Định nghĩa Schema

const processTagSchema = z.object({
  id: z.string(),
  tagName: z.string(),
  fullTagName: z.string(),
});

const tagValueSchema = z.object({
  id: z.string(),
  tagTitle: z.string(),
});

const attachementItemSchema = z.object({
  fileUrl: z.string(),
  isDeleted: z.boolean().default(false),
});

const processContentSchema = z.object({
  attachments: z.array(attachementItemSchema).optional(),
  content: z.string().min(1, 'Nội dung không được để trống'),
});

export const accessRule = z.object({
  accessType: z.enum(['PUBLIC', 'LOOSEN', 'RESTRICTED']).default('PUBLIC'),
  departmentCodes: z.array(z.string()).optional().default([]),
  positionCodes: z.array(z.string()).optional().default([]),
  buIds: z.array(z.string()).optional().default([]),
  fieldIds: z.array(z.string()).optional().default([]),
  minLevel: z.number().optional().default(0),
  allowedUserIds: z.array(z.string()).optional().default([]),
});

export const ProcessFlowSchema = z.object({
  title: z.string().min(3, 'Tiêu đề quá ngắn'),
  description: z.string().min(10, 'Mô tả không được để trống'),
  processContent: processContentSchema.nonoptional(
    'Nội dung quy trình là bắt buộc',
  ),
  tagItems: z
    .array(processTagSchema, 'Vui lòng chọn ít nhất một tag')
    .nonempty('Phải có ít nhất một tag'),
  active: z.boolean().default(true),
  processStatus: z
    .enum(['INITIALIZED', 'RUNNING', 'STOPPED', 'HOLD', 'APPROVED', 'REJECTED'])
    .default('INITIALIZED'),
  tagIdValues: z
    .array(tagValueSchema, 'Vui lòng chọn ít nhất một giá trị tag')
    .nonempty('Phải có ít nhất một giá trị tag'),
  accessRule: accessRule.nonoptional('Quy tắc truy cập là bắt buộc'),
});

// 2. Tự động suy ra Type từ Schema (dùng cho TS)
export type AccessRule = z.infer<typeof accessRule>;  
export type ProcessFlowDTO = z.infer<typeof ProcessFlowSchema>;
