import { useMemo, useState } from 'react';
import { type Column } from '@/components/shared/DataTable';
import { User } from 'lucide-react';
import { useGetAllUsers } from '@/modules/core/admin/hooks/useAdminHook';
import ModalComponent from '@/components/shared/ModalComponent';
import { safeString, toArray } from '@/utils/formatValue';
import type {
  UserManagementDTO,
  UserResponseObjectType,
} from '@/modules/core/auth/schemas/userSchema';
import { useTypeQueryState } from '@/hooks/useTypeQueryState';
import Status from '@/components/shared/Status';
import UseAdminLayout from '../components/content/UseAdminLayout';
import CreateUserComponent from '../components/userComponent/CreateUserComponent';
import UpdateUserComponent from '../components/userComponent/UpdateUserComponent';

type UserRow = {
  id: string | number;
  fullName: string;
  email: string;
  staffId: string;
  systemRole: string;
  role: string;
  department: string;
  enabled: boolean;
  accountNonLocked: boolean;
  rawUser: UserResponseObjectType;
};

export function UserTableComponent({}) {
  const [query, setQuery] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);
  const { state, updateState } = useTypeQueryState();
  const [selectedUser, setSelectedUser] = useState<UserManagementDTO | null>(
    null,
  );
  const { data, isLoading } = useGetAllUsers(state);

  const { content, ...pagination } = data || {};

  const rows = useMemo<UserRow[]>(() => {
    const source = toArray(content);

    return source.map((item) => {
      const record = item as Record<string, unknown>;
      const rawUser = item as UserResponseObjectType;
      const workProfileList = toArray(record.workProfileList)
        .map((profile) =>
          safeString((profile as Record<string, unknown>).departmentId, ''),
        )
        .join(', ');

      return {
        id: safeString(record.id ?? record._id ?? record.staffId),
        fullName: safeString(record.fullName),
        email: safeString(record.email),
        staffId: safeString(record.staffId),
        enabled: Boolean(record.enabled),
        systemRole: safeString(record.systemRole),
        role: safeString(record.systemRole),
        accountNonLocked: Boolean(record.accountNonLocked),
        department: safeString(workProfileList),
        rawUser,
      };
    });
  }, [content]);

  const columns: Column<UserRow>[] = [
    {
      key: 'fullName',
      label: 'Full Name',
      render: (item) => (
        <span className='font-medium text-slate-900'>{item.fullName}</span>
      ),
    },
    {
      key: 'staffId',
      label: 'Staff ID',
    },
    {
      key: 'email',
      label: 'Email',
    },
    {
      key: 'enabled',
      label: 'Enabled',
      render: (item) => (
        <Status status={item.enabled ? 'ACTIVE' : 'INACTIVE'} />
      ),
    },
    {
      key: 'accountNonLocked',
      label: 'Lock Status',
      render: (item) => (
        <Status status={item.accountNonLocked ? 'ACTIVE' : 'LOCKED'} />
      ),
    },
    {
      key: 'role',
      label: 'Role',
    },
  ];

  const handlePageChange = (page: number) => {
    updateState((prv) => ({ ...prv, pagination: { ...prv.pagination, page } }));
  };

  const handleSelectUser = (row: UserRow) => {
    const user = row.rawUser;

    const handledUser: UserManagementDTO = {
      _id: user._id,
      staffId: user.staffId,
      fullName: user.fullName,
      email: user.email,
      systemRole: user.systemRole ?? '',
      enabled: user.enabled,
      workProfileList: user.workProfileList,
      accountNonLocked: user.accountNonLocked,
    };

    setIsModalOpen(true);
    setSelectedUser(handledUser);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
    setSelectedUser(null);
  };

  return (
    <div className='m-2 animate-in fade-in duration-300'>
      <UseAdminLayout
        moduleTitle='Quản lý người dùng'
        columns={columns}
        moduleDescription='Quản trị tài khoản người dùng, phân quyền và trạng thái kích hoạt.'
        rows={rows}
        isLoading={isLoading}
        pagination={pagination}
        ModuleIcon={User}
        handleEdit={handleSelectUser}
        handlePageChange={handlePageChange}
        onSearchAction={setQuery}
        ctaLabel={'Thêm người dùng'}
        onCtaClick={() => setIsModalOpen(true)}
      />
      <ModalComponent open={isModalOpen} onClose={handleCloseModal}>
        {!selectedUser ? (
          <CreateUserComponent />
        ) : (
          <UpdateUserComponent staffId={selectedUser.staffId} />
        )}
      </ModalComponent>
    </div>
  );
}
