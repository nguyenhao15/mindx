import z from 'zod';

export const MaintenanceRequest = z.object({
  id: z.string(),
  description: z.string().min(3, 'Vui lòng nhập mô tả sự cố'),
  maintenanceCategoryId: z
    .number('Vui lòng chọn một loại bảo trì')
    .min(0, 'Vui lòng chọn một danh mục bảo trì'),
  maintenanceItemId: z.number().min(0, 'Vui lòng chọn một hạng mục bảo trì'),
  issueDate: z.date().min(1, 'Vui lòng chọn ngày phát sinh sự cố'),
  locationId: z.string().min(1, 'Vui lòng chọn một địa điểm'),
  attachments: z
    .array(z.any(), {
      message: 'Vui lòng đính kèm ít nhất 1 tệp',
    })
    .min(1, 'Vui lòng đính kèm ít nhất 1 tệp'),
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
>;
export type MaintenanceInfoDTO = MaintenanceRequestDTO & {
  fixCategory: string;
  fixItem: string;
  locationName: string;
  reWork: boolean;
  isDeleted: boolean;
  totalProposals: number;
};

export type MaintananceDetailsDTO = MaintenanceInfoDTO & {
  proposals: [];
};

export type UpdateMaintenanceRequestDTO = Partial<CreateMaintenanceRequestDTO>;
