import { AW3_ENDPOINT } from '@/modules/documentation/constants/api-endpoint';
import axiosClient from '@/lib/axiosClient';

export const aws3Api = {
  uploadfile: (data: FormData) => {
    return axiosClient.post(`${AW3_ENDPOINT}/upload/file`, data, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  },
};
