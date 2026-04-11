import { flowAttachmentApi } from '@/api/attachmentApi';

export const getFlowAttachmentById = async (
  id: string,
  expirationTime: number,
) => {
  const response = await flowAttachmentApi.getFlowAttachmentById(
    id,
    expirationTime,
  );
  return response.data;
};
