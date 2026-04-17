export const MODULE_NAME = [
  {
    title: 'Admin',
    path: 'admin',
    desc: 'Quản lý người dùng, phòng ban, tài sản và các thiết lập hệ thống.',
  },
  {
    title: 'Documents',
    path: 'docs',
    desc: 'Quản lý và theo dõi các tài liệu nội bộ.',
  },
  {
    title: 'Asset Management',
    path: 'assets',
    desc: 'Quản lý tài sản công ty, bao gồm theo dõi và bảo trì.',
  },
];

export const MODULENUM = ['DOCUMENTS', 'MAINTENANCE', 'CONTRACT'] as const;

export const CHANGE_TYPE = [
  'CREATE',
  'UPDATE',
  'DELETE',
  'CANCEL',
  'REJECT',
  'APPROVE',
] as const;
