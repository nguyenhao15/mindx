import { z } from 'zod';

const coerceNumber = (schema: z.ZodNumber) =>
  z
    .preprocess((val) => {
      if (typeof val === 'string' && val.trim() === '') {
        return NaN;
      }
      return Number(val);
    }, schema)
    .refine((val) => !isNaN(Number(val)), {
      message: 'Position level is required',
    });

export const basementSchema = z.object({
  buFullName: z.string().min(1, 'Tên BU là bắt buộc'),
  buId: z.string().min(1, 'Tên viết tắt BU là bắt buộc'),
  accountantCode: z.string().min(1, 'Mã kế toán là bắt buộc'),
  buType: z.string().optional(),
  active: z.string().min(1, 'Trạng thái là bắt buộc'),
  size: coerceNumber(z.number()),
  address: z.string().optional(),
  city: z.string().min(1, 'Thành phố là bắt buộc'),
  areaFullName: z.string().optional(),
});

export type BasementFormData = z.infer<typeof basementSchema>;

export const BasementResponseObj = z.object({
  id: z.string(),
  buFullName: z.string(),
  buId: z.string(),
  accountantCode: z.string().nullable(),
  buType: z.string().nullable(),
  active: z.boolean().nullable(),
  size: z.number().optional().nullable(),
  address: z.string().optional().nullable(),
  city: z.string().nullable(),
  areaFullName: z.string().nullable(),
});

export type BasementResponse = z.infer<typeof BasementResponseObj>;

export const BasementResponseArray = z.array(BasementResponseObj);

export type BasementResponseArray = z.infer<typeof BasementResponseArray>;
