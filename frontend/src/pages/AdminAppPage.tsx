import { UserTableComponent } from '@/components/userComponent/UserTableComponent';
import TagList from '@/components/tag/TagList';
import TagValueList from '@/components/tag/TagValueList';
import { EmptyState } from '@/components/shared/EmtyState';
import { TabNavigation, type TabItem } from '@/components/TabNavigation';
import {
  BriefcaseBusiness,
  Building,
  File,
  PackageIcon,
  TagsIcon,
  UsersIcon,
} from 'lucide-react';
import { useState } from 'react';

import InternalWorkingSystemComponent from '@/components/adminComponent/InternalWorkingSystemComponent';
import BasementListComponent from '@/components/basementComponent/BasementListComponent';
import type { UserDTO } from '@/validations/userSchema';
import { FaUserShield } from 'react-icons/fa';
import { Link } from 'react-router-dom';
import DocumentAdminList from '@/components/documents/DocumentAdminList';

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

const AdminAppPage = ({ user }: { user: UserDTO }) => {
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
