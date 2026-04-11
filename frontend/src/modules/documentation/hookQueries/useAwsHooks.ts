import { uploadFileToS3 } from '@/modules/documentation/actions/aws3Actions';
import { useMutation } from '@tanstack/react-query';

import toast from 'react-hot-toast';

export const useUploadFileToS3 = () => {
  return useMutation({
    mutationFn: async (data: FormData) => {
      const response = await uploadFileToS3(data);
      return response;
    },
    onError: (error) => {
      console.error('Error uploading file to S3:', error);
      toast.error('Failed to upload file');
    },
  });
};
