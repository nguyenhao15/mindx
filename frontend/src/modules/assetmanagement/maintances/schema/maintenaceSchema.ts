import z from 'zod';

export const MaintenanceRequest = z.object({
  id: z.string(),
  maintenanceCategoryId: z
    .string()
    .min(3, 'Vui lòng chọn một danh mục bảo trì'),
  maintenanceItemId: z.string().min(3, 'Vui lòng chọn một hạng mục bảo trì'),
  issueDate: z.string().min(1, 'Vui lòng chọn ngày phát sinh sự cố'),
  locationId: z.string().min(3, 'Vui lòng chọn một địa điểm'),
  requestedBy: z.string(),

  totalCost: z.number().min(0, 'Tổng chi phí phải là một số dương'),

  maintenanceStatus: z
    .enum(['PENDING', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED'])
    .default('PENDING'),
  createdDate: z.string(),
  lastModifiedDate: z.string(),
  createdBy: z.string(),
  lastModifiedBy: z.string(),
});

export type MaintenanceRequestDTO = z.infer<typeof MaintenanceRequest>;
export type CreateMaintenanceRequestDTO = Omit<
  MaintenanceRequestDTO,
  | 'id'
  | 'createdDate'
  | 'lastModifiedDate'
  | 'createdBy'
  | 'lastModifiedBy'
  | 'maintenanceStatus'
  | 'totalCost'
>;
export type MaintenanceInfoDTO = MaintenanceRequestDTO & {
  fixCategory: string;
  fixItem: string;
  locationName: string;
  reWork: boolean;
  totalProposals: number;
};

export type MaintananceDetailsDTO = MaintenanceInfoDTO & {
  proposals: [];
};

export type UpdateMaintenanceRequestDTO = Partial<CreateMaintenanceRequestDTO>;
