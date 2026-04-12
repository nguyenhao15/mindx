import z from 'zod';

const workingFieldSchema = z.object({
  id: z.string(),
  fieldName: z.string(),
  fieldCode: z.string(),
});

export const departmentSchema = z.object({
  departmentName: z.string().min(1, 'Department name is required'),
  departmentCode: z.string().min(1, 'Department code is required'),
  active: z.string().default('true'),
  workingFields: z
    .array(workingFieldSchema, {
      message: 'Bổ sung mảng hoạt động',
    })
    .nonempty('Vui lòng bổ sung mảng hoạt động '),
  iconSvg: z.string().optional(),
  isSecurity: z.string().default('false'),
  description: z.string().optional(),
});

export type DepartmentFormData = z.infer<typeof departmentSchema>;

export const DepartmentObj = z.object({
  id: z.string(),
  departmentName: z.string(),
  departmentCode: z.string(),
  description: z.string().optional(),
  active: z.boolean(),
  workingFields: z.array(workingFieldSchema),
  isSecurity: z.boolean(),
  iconSvg: z.string().optional(),
});

export type DepartmentObjType = z.infer<typeof DepartmentObj>;

export const DepartmentListObj = z.array(DepartmentObj);

export type DepartmentInput = z.input<typeof departmentSchema>;
export type DepartmentOutput = z.output<typeof departmentSchema>;
export type DepartmentListObjType = z.infer<typeof DepartmentListObj>;
