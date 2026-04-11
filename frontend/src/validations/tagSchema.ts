import z from 'zod';

export const tagSchema = z.object({
  tagName: z.string().min(3, 'Tên tag phải có ít nhất 3 ký tự'),
  fullTagName: z.string().min(3, 'Tên tag đầy đủ phải có ít nhất 3 ký tự'),
  description: z.string().min(3, 'Mô tả phải có ít nhất 3 ký tự'),
  active: z.string().default('true'),
  status: z.string().default('ACTIVE'),
});

export type TagDTO = z.infer<typeof tagSchema>;

export interface TagInfo {
  id: string | number;
  tagName: string;
  fullTagName: string;
  description: string;
  status: string;
  active: string;
}

export const TagUpdateObjectSchema = z.object({
  id: z.string(),
  tagName: z.string().min(3, 'Tên tag phải có ít nhất 3 ký tự').optional(),
  fullTagName: z
    .string()
    .min(3, 'Tên tag đầy đủ phải có ít nhất 3 ký tự')
    .optional(),
  description: z.string().min(3, 'Mô tả phải có ít nhất 3 ký tự').optional(),
  active: z.string().optional(),
  status: z.string().optional(),
});

export const TagResponseObjectSchema = z.object({
  id: z.string(),
  tagName: z.string(),
  description: z.string(),
  fullTagName: z.string(),
  active: z.boolean(),
  status: z.string(),
});

export const TagResponseSchema = z.array(TagResponseObjectSchema);

export type TagResponse = z.infer<typeof TagResponseSchema>;
