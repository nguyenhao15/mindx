import { PROCESS_FLOW_CONTENT } from '@/modules/documentation/constants/api-endpoint';
import axiosClient from '@/lib/axiosClient';

export interface UploadConfig {
  onUploadProgress?: (progressEvent: any) => void;
  signal?: AbortSignal;
}

export const processFlowTextContentApi = {
  getProcessFlowTextContent: (id: string) => {
    return axiosClient.get(`${PROCESS_FLOW_CONTENT}/process-flow-id/${id}`);
  },

  uploadfile: (file: File, config?: UploadConfig) => {
    const formData = new FormData();
    formData.append('file', file);
    return axiosClient.post(`${PROCESS_FLOW_CONTENT}/upload-file`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
      onUploadProgress: config?.onUploadProgress,
      signal: config?.signal,
    });
  },
};
