import {
  getProcessFlowTextContentByProessFlowIdActions,
  uploadFileInLineContent,
} from '@/actions/processFlowTextContentActions';
import type { UploadConfig } from '@/api/processFlowTextContentApi';
import { useMutation, useQuery } from '@tanstack/react-query';
import toast from 'react-hot-toast';

export const useGetProcessFlowTextContentByProcessFlowId = (id: string) => {
  return useQuery({
    queryKey: ['processFlowTextContent', id],
    queryFn: async () => {
      const response = await getProcessFlowTextContentByProessFlowIdActions(id);

      return response;
    },
    enabled: !!id, // Chỉ chạy query khi id tồn tại
  });
};

export const useUploadFileInLineContent = () => {
  return useMutation({
    mutationFn: async ({
      file,
      config,
    }: {
      file: File;
      config?: UploadConfig;
    }) => {
      const response = await uploadFileInLineContent(file, config);
      return response;
    },
    onError: (error) => {
      console.error('Upload error status:', error?.response?.status);
      console.error('Upload error data:', error?.response?.data);
      if (error?.code !== 'ERR_CANCELED') {
        toast.error(error?.response?.data?.message || 'Failed to upload file');
      }
    },
  });
};
