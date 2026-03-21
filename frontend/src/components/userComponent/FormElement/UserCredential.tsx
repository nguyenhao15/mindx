import TextInputField from '@/components/input-elements/TextInputField';
import React from 'react';
import { useFormContext } from 'react-hook-form';

const UserCredential = ({
  register,
  errors,
}: {
  register: any;
  errors: any;
}) => {
  return (
    <div className='space-y-4'>
      <div>
        <h2 className='text-lg font-semibold text-gray-900'>
          Account Credentials
        </h2>
        <p className='text-sm text-gray-500'>
          Basic login information for the new user.
        </p>
      </div>
      <div className='bg-white rounded-xl shadow-sm border border-gray-200 p-8'>
        <div className='grid grid-cols-1 md:grid-cols-3 gap-6'>
          <TextInputField
            id='staffId'
            type='text'
            label='Mã nhân viên'
            required
            register={register}
            errors={errors}
            placeholder='Mã nhân viên...'
          />
          <TextInputField
            id='fullName'
            type='text'
            label='Họ và tên'
            required
            register={register}
            errors={errors}
            placeholder='Họ và tên...'
          />
          <TextInputField
            id='email'
            type='email'
            label='Email'
            required
            register={register}
            errors={errors}
            placeholder='Lưu ý sử dụng email công ty'
          />
        </div>
      </div>
    </div>
  );
};

export default UserCredential;
