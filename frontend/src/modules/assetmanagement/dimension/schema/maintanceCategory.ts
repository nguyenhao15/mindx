import z from 'zod';
import { maintanceItemBaseEntity, MaintanceItemInfoDto } from './maintanceItem';

export const MaintanceCategoryBase = z.object({
  id: z.number(),
  categoryTitle: z.string(),
  description: z.string(),
  hashChild: z.boolean(),
  isDeleted: z.boolean(),
  active: z.boolean(),

  maintenanceItems: z.array(maintanceItemBaseEntity).nullable().optional(),
  createdDate: z.string(),
  lastModifiedDate: z.string(),
  createdBy: z.string(),
  lastModifiedBy: z.string(),
});

export const MaintanceCategoryInfo = MaintanceCategoryBase.omit({
  maintenanceItems: true,
  createdBy: true,
  createdDate: true,
  lastModifiedBy: true,
  lastModifiedDate: true,
});

export const MaintenanceCategoryInfoWithItems = MaintanceCategoryBase.pick({
  id: true,
  categoryTitle: true,
  description: true,
  hashChild: true,
  createdBy: true,
  createdDate: true,
  lastModifiedBy: true,
  lastModifiedDate: true,
}).extend({
  maintenanceItems: z.array(MaintanceItemInfoDto).nullable().optional(),
});

export const MaintenanceCategoryNestInfo = MaintanceCategoryInfo.pick({
  id: true,
  categoryTitle: true,
});

export const MaintenanceCategoryInfoWithItemsArray = z.array(
  MaintenanceCategoryInfoWithItems,
);

export type CreateMaintanceCategoryRequest = Omit<
  MaintanceCategoryEntities,
  | 'id'
  | 'createdDate'
  | 'lastModifiedDate'
  | 'createdBy'
  | 'lastModifiedBy'
  | 'maintenanceStatus'
  | 'maintenanceItems'
  | 'hashChild'
>;
export type MaintanceCategoryEntities = z.infer<typeof MaintanceCategoryBase>;
export type MaintanceCategoryInfo = z.infer<typeof MaintanceCategoryInfo>;
export type MaintenanceCategoryInfoWithItems = z.infer<
  typeof MaintenanceCategoryInfoWithItems
>;
export type MaintenanceCategoryNestInfo = z.infer<
  typeof MaintenanceCategoryNestInfo
>;
