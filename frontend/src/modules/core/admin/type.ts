export type ModuleId =
  | 'workflows'
  | 'users'
  | 'employees'
  | 'documents'
  | 'tags';

export interface ModuleStat {
  moduleId: ModuleId;
  label: string;
  count: number;
  delta: number; // positive = increase vs last period
  unit?: string;
}

export interface ModuleCard {
  id: ModuleId;
  label: string;
  description: string;
  iconName: string; // lucide icon name string – resolved at runtime
  accentColor: string;
  href?: string;
}

export type ActivityKind = 'created' | 'updated' | 'deleted' | 'approved';

export interface ActivityItem {
  id: string;
  kind: ActivityKind;
  module: ModuleId;
  title: string;
  actor: string;
  timestamp: string;
}
