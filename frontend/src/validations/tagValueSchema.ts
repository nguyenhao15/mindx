import z from 'zod';

const tagItems = z.object({
  id: z.string(),
  tagName: z.string(),
  fullTagName: z.string(),
});

export const tagValueSchema = z.object({
  tagTitle: z.string().min(1, 'Giá trị tag không được để trống'),
  description: z.string().min(3, 'Mô tả phải có ít nhất 3 ký tự'),
  tagItems: z.array(tagItems).min(1, 'Vui lòng nhập tag'),
  tagValueCode: z.string().min(1, 'Mã giá trị tag không được để trống'),
  active: z.string().default('true'),
  status: z.string().default('ACTIVE'),
});

export const TagItemResponseSchema = z.object({
  id: z.string(),
  tagTitle: z.string(),
  tagValueCode: z.string(),
  tagItems: z.array(tagItems),
  description: z.string(),
  active: z.boolean(),
  createdBy: z.string(),
});

export const TagArrayResponseSchema = z.array(TagItemResponseSchema);

export type TagValueObject = z.infer<typeof TagItemResponseSchema>;
export type TagValueList = z.infer<typeof TagArrayResponseSchema>;
export type TagValueResponse = z.infer<typeof TagItemResponseSchema>;
export type TagValueDTO = z.infer<typeof tagValueSchema>;
