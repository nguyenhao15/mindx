import {
  processFlowTextContentApi,
  type UploadConfig,
} from '@/modules/documentations/document/api/processFlowTextContentApi';

export const getProcessFlowTextContentByProessFlowIdActions = async (
  id: string,
) => {
  try {
    const response =
      await processFlowTextContentApi.getProcessFlowTextContent(id);

    return response.data;
  } catch (error) {
    console.log('Errors:', error);
    throw error;
  }
};

export const uploadFileInLineContent = async (
  file: File,
  config?: UploadConfig,
) => {
  const response = await processFlowTextContentApi.uploadfile(file, config);
  return response.data;
};
