import { useSearchUser } from '@/modules/documentation/hookQueries/useAdminHook';
import React, { useMemo, useState } from 'react';
import { Controller, useFormContext } from 'react-hook-form';
import ManualCustomCombobox from './ManualCustomCombobox';

const SearchUser = () => {
  const [searchKeyword, setSearchKeyword] = useState('');
  const {
    control,
    register,
    formState: { errors },
  } = useFormContext();

  const { data, isLoading, isError } = useSearchUser(searchKeyword);

  const handleSearchChange = (data) => {
    setTimeout(() => {
      setSearchKeyword(data);
    }, 500); // Thêm debounce 300ms để tránh gọi API quá nhiều lần khi người dùng gõ nhanh
  };

  const userOptions = useMemo(() => {
    return data
      ? data.map((user) => ({
          label: `${user.fullName} (${user.email})`,
          value: user.id,
        }))
      : [];
  }, [data]);

  return (
    <div>
      <ManualCustomCombobox
        label='Tìm kiếm người dùng'
        id='searchUser'
        isLoading={isLoading}
        options={userOptions}
        placeholder='Nhập tên hoặc email để tìm kiếm'
        onChangeInput={handleSearchChange}
      />
    </div>
  );
};

export default SearchUser;
