import { aws3Api } from '@/api/aws3Api';

export const uploadFileToS3 = async (data: FormData) => {
  const response = await aws3Api.uploadfile(data);
  return response.data;
};
