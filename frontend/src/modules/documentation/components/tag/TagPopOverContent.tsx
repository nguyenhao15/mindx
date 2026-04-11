import { useMemo, useState } from 'react';
import { useInitialValue } from '@/modules/documentation/hookQueries/useInitialValue';
import GridChipsCard from '../shared/GridChipsCard';
import GridContainerChip from '../shared/GridContainerChip';
import ChipCardComboboxContainer from '../shared/ChipCardComboboxContainer';

const TagPopOverContent = ({
  searchKeyDefaultValue = '',
  onApply,
  selectedTagsDefaultValue = [],
}: {
  onApply: (tags: string[]) => void;
  searchKeyDefaultValue?: string;
  selectedTagsDefaultValue?: string[];
}) => {
  const [searchKeyword, setSearchKeyword] = useState(searchKeyDefaultValue);
  const [selectedTagValues, setSelectedTagValues] = useState<string[]>(
    selectedTagsDefaultValue,
  );

  const { tagOptions } = useInitialValue();

  const handleSearchChange = (value: string) => {
    setSearchKeyword(value);
  };

  const filteredTags = tagOptions
    .filter((opt: any) =>
      opt.tagName.toLowerCase().includes(searchKeyword.toLowerCase()),
    )
    .map((opt: any) => ({
      id: opt.id,
      label: opt.tagName,
    }));

  const handleApply = () => {
    if (selectedTagValues.length > 0) {
      onApply(selectedTagValues);
    } else {
      onApply([]);
    }
  };

  const handleOnClickTag = (id: string) => {
    if (selectedTagValues.includes(id)) {
      setSelectedTagValues((prev) => prev.filter((tag) => tag !== id));
    } else {
      setSelectedTagValues((prev) => [...prev, id]);
    }
  };

  const handleClear = () => {
    setSelectedTagValues([]);
  };

  const selectedTags = useMemo(() => {
    return selectedTagValues.map((tag: any) => {
      return {
        id: tag,
        label: tagOptions.find((opt: any) => opt.id === tag)?.tagName || '',
      };
    });
  }, [selectedTagValues]);

  return (
    <ChipCardComboboxContainer
      selectedItems={selectedTags}
      defaultKeyword={searchKeyDefaultValue}
      handleSearchKeyWord={handleSearchChange}
      handleClear={handleClear}
      handleApply={handleApply}
      handleToggleTag={handleOnClickTag}
    >
      <GridContainerChip>
        {filteredTags.map((opt: any) => (
          <GridChipsCard
            key={opt.id}
            opt={opt}
            isSelected={selectedTagValues.includes(opt.id)}
            onClickAction={handleOnClickTag}
          />
        ))}
      </GridContainerChip>
    </ChipCardComboboxContainer>
  );
};

export default TagPopOverContent;
