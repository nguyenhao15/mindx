export const MODULE_PREFIX = {
  BASE: '',
  PROCESS: '/v1/process',
  DOCUMENT: '/v1/document',
  ASSET: '/v1/asset',
  STAFF_PROFILE: '/v1/staff-profile',
  APPROVAL: '/v1/approval/policy',
  WORKFLOW: '/v1/approval/workflow',
};

// API endpoints for the application
export const PROCESS_TAG = `${MODULE_PREFIX.PROCESS}/process-tags`;
export const PROCESS_TAG_VALUES = `${MODULE_PREFIX.PROCESS}/process-tag-values`;
export const PROCESS_FLOW = `${MODULE_PREFIX.PROCESS}/process-flows`;
export const PROCESS_FLOW_CONTENT = `${MODULE_PREFIX.PROCESS}/process-flows-content`;
export const AW3_ENDPOINT = `${MODULE_PREFIX.PROCESS}/space/aws3`;

export const APPROVAL_POLICY_ENDPOINT = `${MODULE_PREFIX.APPROVAL}`;
export const APPROVAL_WORKFLOW_ENDPOINT = `${MODULE_PREFIX.WORKFLOW}`;

export const STAFF_PROFILE_ENDPOINT = `${MODULE_PREFIX.STAFF_PROFILE}`;

export const FLOW_ATTACHMENT_ENDPOINT = `${MODULE_PREFIX.BASE}/v1/attachment`;
export const ADMIN_ENDPOINT = `${MODULE_PREFIX.BASE}/admin`;
export const WORKING_FIELD_ENDPOINT = `${MODULE_PREFIX.BASE}/v1/working-fields`;
export const DEPARTMENT_ENDPOINT = `${MODULE_PREFIX.BASE}/v1/departments`;
export const POSITION_ENDPOINT = `${MODULE_PREFIX.BASE}/v1/positions`;
export const BASEMENT_ENDPOINT = `${MODULE_PREFIX.BASE}/bus`;

// FOR MAINTENANCE MODULE
export const MAINTANANCE_ENDPOINT = `${MODULE_PREFIX.ASSET}/maintenance/request`;
export const WORKFLOW_ENDPOINT = `${MODULE_PREFIX.ASSET}/maintenance/workflow`;
export const MAINTENANCE_CATEGORY_ENDPOINT = `${MODULE_PREFIX.ASSET}/maintenance/dim`;
