import { useMemo, useState } from 'react';
import { DataTable, type Column } from '../shared/DataTable';
import { ActionHeader } from '../shared/ActionHeder';
import { EmptyState } from '@/components/shared/EmtyState';
import { UsersIcon } from 'lucide-react';
import { useGetAllUsers } from '@/hookQueries/useAdminHook';
import ModalComponent from '../shared/ModalComponent';
import UserForm from './UserForm';
import Loader from '../shared/Loader';
import { safeString, toArray } from '@/utils/formatValue';
import type {
  UserManagementDTO,
  UserResponseObjectType,
  WorkProfileType,
} from '@/validations/userSchema';
import { useTypeQueryState } from '@/hooks/useTypeQueryState';
import Status from '../shared/Status';

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
  workProfileList?: WorkProfileType[];
  rawUser: UserResponseObjectType;
};

export function UserTableComponent({}) {
  const [, setQuery] = useState('');
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
        workProfileList: record.workProfileList as WorkProfileType[],
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
    {
      key: 'department',
      label: 'Department',
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
      accountNonLocked: user.accountNonLocked,
      workProfileList: user.workProfileList.map((profile) => ({
        departmentId: profile.departmentId,
        positionCode: profile.positionCode,
        positionLevel: profile.positionLevel,
        isMainPosition: profile.isMainPosition ?? false,
        buAllowedList: profile.buAllowedList ?? [],
      })),
    };

    setIsModalOpen(true);
    setSelectedUser(handledUser);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
    setSelectedUser(null);
  };

  return (
    <div className='animate-in fade-in duration-300'>
      <ActionHeader
        title='User Management'
        searchPlaceholder='Search users...'
        ctaLabel='Invite User'
        onSearch={setQuery}
        onCtaClick={() => setIsModalOpen(true)}
      />
      {isLoading ? (
        <Loader text='Đang tải dữ liệu' />
      ) : rows.length === 0 ? (
        <EmptyState
          title='No Users Found'
          description='No users match your keyword. Try another search to continue.'
          icon={UsersIcon}
        />
      ) : (
        <DataTable
          columns={columns}
          data={rows}
          actionLabel='Update'
          onEdit={handleSelectUser}
          pagination={pagination}
          handlePageChange={handlePageChange}
        />
      )}
      <ModalComponent open={isModalOpen} onClose={handleCloseModal}>
        <UserForm initialUser={selectedUser} onClose={handleCloseModal} />
      </ModalComponent>
    </div>
  );
}
