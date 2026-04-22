import React from 'react';
import WorkflowComponent from './WorkFlow/WorkFlowComponent';
import ApprovalPolicyComponent from './WorkFlow/ApprovalPolicyComponent';

const WorkflowMasterComponent = () => {
  return (
    <div className='flex flex-col gap-3 p-2'>
      <WorkflowComponent />
      <ApprovalPolicyComponent />
    </div>
  );
};

export default WorkflowMasterComponent;
