import type {
  WorkProfileEmbeddedType,
  WorkProfileType,
} from '@/modules/core/auth/schemas/userSchema';
import WorkProfileList from './WorkProfileComponent/WorkProfileList';

interface WorkProfileInfoProps {
  data: WorkProfileEmbeddedType[];
}

const WorkProfileInfo = ({ data }: WorkProfileInfoProps) => {
  return <WorkProfileList data={data} />;
};

export default WorkProfileInfo;
