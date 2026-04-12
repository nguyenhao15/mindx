import z from 'zod';

export const workingFieldSchema = z.object({
  fieldName: z.string().min(1, 'Field name is required'),
  fieldCode: z.string().min(1, 'Field code is required'),
  active: z.boolean().default(true),
  description: z.string().optional(),
});

export type WorkingFieldFormData = z.infer<typeof workingFieldSchema>;

export const WorkingFieldObectSchema = z.object({
  id: z.string(),
  fieldName: z.string(),
  fieldCode: z.string(),
  active: z.boolean(),
  description: z.string().optional(),
  createdBy: z.string(),
  lastModifiedBy: z.string(),
  createdDate: z.string(),
  lastModifiedDate: z.string(),
  version: z.number(),
});

export type WorkingFieldObject = z.infer<typeof WorkingFieldObectSchema>;

export const WorkingFieldResponseSchema = z.array(WorkingFieldObectSchema);

export type WorkingFieldInput = z.input<typeof workingFieldSchema>;
export type WorkingFieldOutput = z.output<typeof workingFieldSchema>;
export type WorkingFieldResponse = z.infer<typeof WorkingFieldResponseSchema>;
