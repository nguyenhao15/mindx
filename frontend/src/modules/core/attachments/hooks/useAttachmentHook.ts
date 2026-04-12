import { getFlowAttachmentById } from '@/modules/core/attachments/quries/attachmentActions';
import { useQuery } from '@tanstack/react-query';

type UseGetFlowAttachmentByIdResult = {
  id: string;
  expirationTime: number;
};

export const useGetFlowAttachmentById = ({
  id,
  expirationTime,
}: UseGetFlowAttachmentByIdResult) => {
  return useQuery({
    queryKey: ['flowAttachment', id, expirationTime],
    queryFn: async () => {
      const response = await getFlowAttachmentById(id, expirationTime);
      return response;
    },
    enabled: !!id, // Chỉ chạy query khi id tồn tại
  });
};
