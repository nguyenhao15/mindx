import { useGetActiveProcessTag } from '../modules/documentations/tag/hooks/useProcessTagHooks';
import { useGetActiveTagValueOptions } from '../modules/documentations/tag/hooks/useProcessTagValueHooks';

export const useInitialValue = () => {
  const { data: tagData, isLoading, error } = useGetActiveProcessTag();
  const {
    data: tagValueData,
    isLoading: tagValueLoading,
    error: tagValueError,
  } = useGetActiveTagValueOptions();

  const tagOptions =
    tagData?.map((tag: any) => ({
      label: tag.fullTagName,
      value: tag.id,
      ...tag,
    })) || [];

  const tagValueOptions =
    tagValueData?.map((tagValue: any) => ({
      label: tagValue.tagTitle,
      value: tagValue.id,
      ...tagValue,
    })) || [];

  return {
    tagOptions: tagOptions || [],
    tagValueOptions: tagValueOptions || [],
    isLoading: isLoading || tagValueLoading,
    error: error || tagValueError,
  };
};
