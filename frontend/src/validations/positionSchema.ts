import z from 'zod';

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

export const positionSchema = z.object({
  positionName: z.string().min(1, 'Position name is required'),
  positionCode: z.string().min(1, 'Position code is required'),
  description: z.string().optional(),
  departmentCode: z.string().min(1, 'Department code is required'),
  positionLevel: coerceNumber(
    z
      .number()
      .min(0, 'Position level is required')
      .max(5, 'Position level is required'),
  ),
  active: z.boolean().default(true),
});

export type PositionFormData = z.infer<typeof positionSchema>;

export const PositionResponseObj = z.object({
  id: z.string(),
  positionName: z.string(),
  positionCode: z.string(),
  description: z.string(),
  departmentCode: z.string(),
  positionLevel: z.number(),
  active: z.boolean(),
});

export type PositionResponse = z.infer<typeof PositionResponseObj>;

export const PositionResponseArray = z.array(PositionResponseObj);

export type PositionResponseArray = z.infer<typeof PositionResponseArray>;
