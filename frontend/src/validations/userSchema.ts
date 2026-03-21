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

export const updatePasswordSchema = z
  .object({
    currentPassword: z
      .string()
      .trim()
      .min(1, 'Mật khẩu hiện tại không được để trống'),
    newPassword: z
      .string()
      .trim()
      .min(8, 'Mật khẩu mới phải có ít nhất 8 ký tự')
      .regex(
        /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).+$/,
        'Mật khẩu mới phải chứa chữ hoa, chữ thường và số',
      ),
    confirmNewPassword: z
      .string()
      .trim()
      .min(1, 'Vui lòng nhập lại mật khẩu mới'),
  })
  .refine((data) => data.newPassword !== data.currentPassword, {
    message: 'Mật khẩu mới phải khác mật khẩu hiện tại',
    path: ['newPassword'],
  })
  .refine((data) => data.newPassword === data.confirmNewPassword, {
    message: 'Mật khẩu nhập lại không khớp',
    path: ['confirmNewPassword'],
  });

export type UserDTO = z.infer<typeof userSchema>;
export type UpdatePasswordDTO = z.infer<typeof updatePasswordSchema>;

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
    updatedDate: z.string(),
    username: z.string().nullable(),
    twoFactorEnabled: z.boolean(),
  })
  .transform((user) => ({
    id: user._id,
    ...user,
  }));

export const activatePasswordSchema = z
  .object({
    password: z
      .string()
      .trim()
      .min(8, 'Mật khẩu phải có ít nhất 8 ký tự')
      .regex(
        /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).+$/,
        'Mật khẩu phải chứa chữ hoa, chữ thường và số',
      ),
    confirmPassword: z.string().trim().min(1, 'Vui lòng nhập lại mật khẩu'),
  })
  .refine((data) => data.password === data.confirmPassword, {
    message: 'Mật khẩu nhập lại không khớp',
    path: ['confirmPassword'],
  });

export const UserResponseSchema = z.array(UserResponseObject);

export type UserResponse = z.infer<typeof UserResponseSchema>;
export type ActivatePasswordDTO = z.infer<typeof activatePasswordSchema>;
export type UserResponseObjectType = z.infer<typeof UserResponseObject>;
export type WorkProfileType = z.infer<typeof WorkProfile>;
