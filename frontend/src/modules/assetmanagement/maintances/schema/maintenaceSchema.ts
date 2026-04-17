import z from 'zod';
import { MaintenanceCategoryNestInfo } from '../../dimension/schema/maintanceCategory';
import { MaintanceItemInfoDto } from '../../dimension/schema/maintanceItem';
import { AuditUpdateJPASchema } from '@/validations/auditSchema';

export const MAINTENANCE_STATUS_VALUES = [
  'WAITING',
  'APPROVED',
  'REJECTED',
  'CHECKED',
  'PROCESSING',
  'FINISHED',
  'COMPLETED',
] as const;

export const MaintenanceRequest = z.object({
  id: z.number(),
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
  isDeleted: z.boolean().default(false),
  maintenancesStatus: z.enum(MAINTENANCE_STATUS_VALUES).default('WAITING'),
  createdDate: z.string(),
  lastModifiedDate: z.string(),
  createdBy: z.string(),
  inspectAt: z.date().min(1, 'Vui lòng chọn ngày kiểm tra'),
  completionAt: z.date().min(1, 'Vui lòng chọn ngày hoàn thành'),
  verifiedAt: z.date().min(1, 'Vui lòng chọn ngày nghiệm thu'),
  lastModifiedBy: z.string(),
});

export type MaintenanceRequestDTO = z.infer<typeof MaintenanceRequest>;

export type CreateMaintenanceRequestDTO = Omit<
  MaintenanceRequestDTO,
  | 'id'
  | 'createdDate'
  | 'lastModifiedDate'
  | 'createdBy'
  | 'inspectAt'
  | 'completionAt'
  | 'verifiedAt'
  | 'lastModifiedBy'
  | 'maintenancesStatus'
  | 'isDeleted'
>;

export const MaintenanceSumarySchema = MaintenanceRequest.omit({
  maintenanceCategoryId: true,
  maintenanceItemId: true,
  attachments: true,
  issueDate: true,
}).extend({
  issueDate: z.string(),
  fixCategory: MaintenanceCategoryNestInfo,
  fixItem: MaintanceItemInfoDto,
  reWork: z.boolean(),
  isDeleted: z.boolean().default(false),
  totalProposals: z.number(),
});

export const MaintananceDetailsDTO = MaintenanceSumarySchema.extend({
  proposals: z.array(
    z.object({
      id: z.string(),
      proposedBy: z.string(),
      proposedDate: z.string(),
      proposalDetails: z.string(),
      estimatedCost: z.number(),
      attachments: z.array(z.any()),
    }),
  ),
});

export const MaintenanceUpdateRequestDtoSchema =
  MaintenanceSumarySchema.partial().pick({
    maintenancesStatus: true,
    reWork: true,
    totalCost: true,
    isDeleted: true,
    inspectAt: true,
    completionAt: true,
    verifiedAt: true,
  });

export const MaintenanceUpdateFormSchema =
  MaintenanceUpdateRequestDtoSchema.merge(AuditUpdateJPASchema);

export const MaintenanceUpdateRequest = z.object({
  requestDto: MaintenanceUpdateRequestDtoSchema,
  auditUpdateRequest: AuditUpdateJPASchema,
});

export const MaintenanceSumarySchemaArray = z.array(MaintenanceSumarySchema);

export type MaintananceDetailsDTO = z.infer<typeof MaintananceDetailsDTO>;
export type MaintenanceSumaryResponse = z.infer<typeof MaintenanceSumarySchema>;
export type MaintenanceSummaryResponseArray = z.infer<
  typeof MaintenanceSumarySchemaArray
>;
export type UpdateMaintenanceRequestDTO = z.infer<
  typeof MaintenanceUpdateRequest
>;
export type UpdateMaintenanceRequestDataDTO = z.infer<
  typeof MaintenanceUpdateRequestDtoSchema
>;
export type UpdateMaintenanceFormDTO = z.infer<
  typeof MaintenanceUpdateFormSchema
>;
export type UpdateMaintenanceFormInputDTO = z.input<
  typeof MaintenanceUpdateFormSchema
>;

export type MaintenanceStatus = (typeof MAINTENANCE_STATUS_VALUES)[number];
