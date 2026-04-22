import type { FilterConfig } from '@/validations/filterWithPagination';

export const FILTER_CONFIGS: FilterConfig[] = [
  {
    field: 'module',
    label: 'Module',
    operator: 'LIKE',
    type: 'SELECT',
    options: [
      { label: 'Bảo trì', value: 'MAINTENANCE' },
      { label: 'Quản lý dự án', value: 'PROJECT' },
      { label: 'Quản lý tài chính', value: 'FINANCE' },
    ],
  },
  {
    field: 'fromStatus',
    label: 'Trạng thái bắt đầu',
    operator: 'LIKE',
    type: 'SELECT',
    options: [
      { label: 'WAITING', value: 'WAITING' },
      { label: 'APPROVED', value: 'APPROVED' },
      { label: 'REJECTED', value: 'REJECTED' },
    ],
  },
  {
    field: 'toStatus',
    label: 'Trạng thái kết thúc',
    operator: 'LIKE',
    type: 'SELECT',
    options: [
      { label: 'WAITING', value: 'WAITING' },
      { label: 'APPROVED', value: 'APPROVED' },
      { label: 'REJECTED', value: 'REJECTED' },
    ],
  },
  {
    field: 'id',
    label: 'ID',
    operator: 'EQUALS',
    type: 'TEXT',
    typeInput: 'number',
  },
];
