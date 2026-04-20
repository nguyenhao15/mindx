import type { WorkProfileType } from '@/modules/core/auth/schemas/userSchema';
import WorkProfileList from './WorkProfileComponent/WorkProfileList';

interface WorkProfileInfoProps {
  data: WorkProfileType[];
}

const WorkProfileInfo = ({ data }: WorkProfileInfoProps) => {
  return <WorkProfileList data={data} />;
};

export default WorkProfileInfo;
