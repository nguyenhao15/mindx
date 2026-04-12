import { UserTableComponent } from '@/components/userComponent/UserTableComponent';

import { EmptyState } from '@/components/shared/EmtyState';
import {
  TabNavigation,
  type TabItem,
} from '@/modules/documentations/document/components/shared/TabNavigation';
import {
  BriefcaseBusiness,
  Building,
  File,
  PackageIcon,
  TagsIcon,
  UsersIcon,
} from 'lucide-react';
import { useState } from 'react';

import InternalWorkingSystemComponent from '@/modules/core/admin/components/InternalWorkingSystemComponent';
import type { UserResponseObjectType } from '@/modules/core/auth/schemas/userSchema';
import { FaUserShield } from 'react-icons/fa';
import { Link } from 'react-router-dom';
import DocumentAdminList from '@/modules/core/admin/components/DocumentAdminList';
import BasementListComponent from '../../basement/components/BasementListComponent';
import TagList from '../components/TagList';
import TagValueList from '../components/TagValueList';

const tabs: TabItem[] = [
  {
    id: 'tags',
    label: 'Tags',
    icon: TagsIcon,
  },
  {
    id: 'tagValues',
    label: 'Tag Values',
    icon: PackageIcon,
  },
  {
    id: 'users',
    label: 'Users',
    icon: UsersIcon,
  },
  {
    id: 'departments',
    label: 'Departments',
    icon: BriefcaseBusiness,
  },
  {
    id: 'basements',
    label: 'Basements',
    icon: Building,
  },
  {
    id: 'Documents',
    label: 'Documents',
    icon: File,
  },
];

const AdminAppPage = ({ user: _user }: { user: UserResponseObjectType }) => {
  const [activeTab, setActiveTab] = useState(tabs[0].id);

  const renderContent = () => {
    switch (activeTab) {
      case 'tags':
        return <TagList />;
      case 'tagValues':
        return <TagValueList />;
      case 'users':
        return <UserTableComponent />;
      case 'departments':
        return <InternalWorkingSystemComponent />;
      case 'basements':
        return <BasementListComponent />;
      case 'Documents':
        return <DocumentAdminList />;
      default:
        return (
          <EmptyState
            title='Content Unavailable'
            description='This tab does not have content yet.'
            icon={BriefcaseBusiness}
          />
        );
    }
  };
  return (
    <div className='p-10 gap-2'>
      <div>
        <span className='text-2xl font-bold text-slate-800 dark:text-white flex items-center gap-2'>
          <FaUserShield className='text-brand-primary' />
          Admin Dashboard
        </span>
        <p className='text-slate-600 dark:text-slate-300 mb-4'>
          Manage your tags, tag values, users, departments, and basements
          efficiently.
        </p>
      </div>
      <Link to='/' className='mb-4 inline-block'>
        <span className='p-2 rounded-md bg-brand-primary text-white font-bold '>
          Go to Home
        </span>
      </Link>
      <TabNavigation
        tabs={tabs}
        activeTab={activeTab}
        onTabChange={setActiveTab}
      />
      <div className='px-8 pb-8 flex-1'>
        <div className='max-w-7xl mx-auto w-full'>{renderContent()}</div>
      </div>
    </div>
  );
};

export default AdminAppPage;
