import { flowAttachmentApi } from '@/modules/core/attachments/api/attachmentApi';

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
