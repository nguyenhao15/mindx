import z, { any } from 'zod';

export const maintanceCategoryBase = z.object({
  id: z.number(),
  categoryTitle: z.string(),
  description: z.string(),
  hashChild: z.boolean(),
  isDeleted: z.boolean(),
  active: z.boolean(),

  maintainItems: z.array(any()).optional(),
  createdDate: z.string(),
  lastModifiedDate: z.string(),
  createdBy: z.string(),
  lastModifiedBy: z.string(),
});

export const MaintanceCategoryInfo = maintanceCategoryBase.omit({
  maintainItems: true,
  createdBy: true,
  createdDate: true,
  lastModifiedBy: true,
  lastModifiedDate: true,
});

export const MaintenanceCategoryInfoWithItems = maintanceCategoryBase.extend({
  maintainItems: z.array(any()),
});

export const MaintenanceCategoryNestInfo = MaintanceCategoryInfo.pick({
  id: true,
  categoryTitle: true,
});

export type CreateMaintanceCategoryRequest = Omit<
  MaintanceCategoryEntities,
  | 'id'
  | 'createdDate'
  | 'lastModifiedDate'
  | 'createdBy'
  | 'lastModifiedBy'
  | 'maintenanceStatus'
  | 'maintainItems'
  | 'hashChild'
>;
export type MaintanceCategoryEntities = z.infer<typeof maintanceCategoryBase>;
export type MaintanceCategoryInfo = z.infer<typeof MaintanceCategoryInfo>;
export type MaintenanceCategoryInfoWithItems = z.infer<
  typeof MaintenanceCategoryInfoWithItems
>;
export type MaintenanceCategoryNestInfo = z.infer<
  typeof MaintenanceCategoryNestInfo
>;
