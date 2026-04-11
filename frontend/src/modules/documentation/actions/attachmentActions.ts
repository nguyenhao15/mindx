import { flowAttachmentApi } from '@/modules/documentation/api/attachmentApi';

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
