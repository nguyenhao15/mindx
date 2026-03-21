import z from 'zod';

export const WorkProfile = z.object({
  departmentId: z.string().trim().min(1, 'Mã phòng ban không được để trống'),
  positionCode: z.string().trim().min(1, 'Mã vị trí không được để trống'),
  positionLevel: z.number().min(0, 'Cấp bậc phải là số dương'),
  isMainPosition: z.boolean().nullable().default(false),
  buAllowedList: z.array(z.string()).nullable().optional().default([]),
});

export const userSchema = z.object({
  staffId: z
    .string()
    .trim()
    .min(3, 'Mã nhân viên phải có ít nhất 3 ký tự')
    .uppercase(),
  fullName: z.string().trim().min(3, 'Họ tên phải có ít nhất 3 ký tự'),
  email: z.email('Email không hợp lệ'),
  systemRole: z
    .string('Vui lòng chọn vai trò hệ thống ')
    .trim()
    .min(3, 'Vai trò hệ thống phải có ít nhất 3 ký tự'),
  workProfileList: z
    .array(WorkProfile)
    .min(1, 'Phải có ít nhất một profile công việc'),
});

export type UserDTO = z.infer<typeof userSchema>;

export const UserResponseObject = z
  .object({
    _id: z.string(),
    staffId: z.string(),
    fullName: z.string(),
    systemRole: z
      .string('Vui lòng chọn vai trò hệ thống ')
      .trim()
      .min(3, 'Vai trò hệ thống phải có ít nhất 3 ký tự')
      .nullable(),
    email: z.string(),
    workProfileList: z.array(WorkProfile),
    accountNonLocked: z.boolean(),
    accountNonExpired: z.boolean(),
    credentialsNonExpired: z.boolean(),
    enabled: z.boolean(),
    createdDate: z.string(),
  })
  .transform((user) => ({
    id: user._id,
    ...user,
  }));

export const UserResponseSchema = z.array(UserResponseObject);

export type UserResponse = z.infer<typeof UserResponseSchema>;
export type UserResponseObjectType = z.infer<typeof UserResponseObject>;
export type WorkProfileType = z.infer<typeof WorkProfile>;
