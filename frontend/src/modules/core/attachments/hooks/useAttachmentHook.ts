import { getFlowAttachmentById } from '@/modules/core/attachments/quries/attachmentActions';
import { useQuery } from '@tanstack/react-query';

type UseGetFlowAttachmentByIdResult = {
  params: { id: string; expirationTime: number };
  options?: {};
};

export const useGetFlowAttachmentById = ({
  params: { id, expirationTime },
  options = {},
}: UseGetFlowAttachmentByIdResult) => {
  return useQuery({
    queryKey: ['flowAttachment', id, expirationTime],
    queryFn: async () => {
      const response = await getFlowAttachmentById(id, expirationTime);
      return response;
    },
    enabled: !!id, // Chỉ chạy query khi id tồn tại
    ...options,
  });
};
