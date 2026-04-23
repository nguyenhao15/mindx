import {
  TabNavigation,
  type TabItem,
} from '@/modules/documentations/document/components/shared/TabNavigation';
import WorkflowComponent from '../components/WorkFlow/WorkFlowComponent';
import { ShieldAlert, Workflow } from 'lucide-react';
import { act, useState } from 'react';
import ApprovalPolicyComponent from '../components/WorkFlow/ApprovalPolicyComponent';

const tabs: TabItem[] = [
  {
    id: 'workFlow',
    label: 'Workflow',
    icon: Workflow,
  },
  {
    id: 'policy',
    label: 'Policy',
    icon: ShieldAlert,
  },
];
const WorkflowPage = () => {
  const [activeTab, setActiveTab] = useState<string>(tabs[0].id);
  return (
    <div className='m-2 animate-in fade-in duration-300'>
      <div className='flex flex-col gap-2'>
        <TabNavigation
          tabs={tabs}
          activeTab={activeTab}
          onTabChange={setActiveTab}
        />
        {activeTab === 'workFlow' && <WorkflowComponent />}
        {activeTab === 'policy' && <ApprovalPolicyComponent />}
      </div>
    </div>
  );
};

export default WorkflowPage;
