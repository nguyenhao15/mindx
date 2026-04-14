import z, { any } from 'zod';

export const maintanceItemBaseEntity = z.object({
  id: z.number(),
  itemTitle: z.string(),
  description: z.string(),
  isDeleted: z.boolean(),

  createdDate: z.string(),
  lastModifiedDate: z.string(),
  createdBy: z.string(),
  lastModifiedBy: z.string(),
  maintenanceCategory: z.object(any()),
});

export const MaintanceItemInfo = maintanceItemBaseEntity
  .omit({
    isDeleted: true,
    maintenanceCategory: true,
  })
  .extend({
    maintenanceCategory: z.object({
      id: z.number(),
      categoryTitle: z.string(),
    }),
  });

export const MaintanceItemInfoDto = maintanceItemBaseEntity.pick({
  id: true,
  itemTitle: true,
  description: true,
});

export type MaintanceItemCreateRequest = Omit<
  MaintanceItemEntity,
  | 'id'
  | 'createdDate'
  | 'lastModifiedDate'
  | 'createdBy'
  | 'lastModifiedBy'
  | 'maintenanceCategory'
>;
export type MaintanceItemUpdateRequest = Partial<MaintanceItemCreateRequest>;
export type MaintanceItemInfo = z.infer<typeof MaintanceItemInfo>;
export type MaintanceItemInfoDto = z.infer<typeof MaintanceItemInfoDto>;
export type MaintanceItemEntity = z.infer<typeof maintanceItemBaseEntity>;
