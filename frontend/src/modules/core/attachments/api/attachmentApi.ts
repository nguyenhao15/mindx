import { FLOW_ATTACHMENT_ENDPOINT } from '@/modules/documentations/constants/api-endpoint';
import axiosClient from '@/lib/axiosClient';

export const flowAttachmentApi = {
  getFlowAttachmentById: (id: string, expirationTime: number) => {
    return axiosClient.get(`${FLOW_ATTACHMENT_ENDPOINT}/file/${id}`, {
      params: {
        expirationTimeInSeconds: expirationTime,
      },
    });
  },
};
