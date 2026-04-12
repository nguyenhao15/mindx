import { useInitialValue } from '@/hookQueries/useInitialValue';
import { useState } from 'react';
import ChipCardComboboxContainer from '@/components/shared/ChipCardComboboxContainer';
import GridContainerChip from '@/components/shared/GridContainerChip';
import GridChipsCard from '@/components/shared/GridChipsCard';

const TagValuePopOverContent = ({
  searchKeyDefaultValue = '',
  onApply,
  selectedTagsDefaultValue = [],
}: {
  onApply: (tags: string[]) => void;
  searchKeyDefaultValue?: string;
  selectedTagsDefaultValue?: string[];
}) => {
  const [searchKeyword, setSearchKeyword] = useState(searchKeyDefaultValue);
  const [selectedItems, setSelectedItems] = useState<string[]>(
    selectedTagsDefaultValue,
  );

  const { tagValueOptions } = useInitialValue();

  const handleSearchChange = (value: string) => {
    setSearchKeyword(value);
  };

  const filteredTags = tagValueOptions
    .filter((opt: any) =>
      opt.tagTitle.toLowerCase().includes(searchKeyword.toLowerCase()),
    )
    .map((opt: any) => ({
      id: opt.id,
      label: opt.tagTitle,
    }));

  const handleApply = () => {
    if (selectedItems.length > 0) {
      onApply(selectedItems);
    } else {
      onApply([]);
    }
  };

  const handleOnClickTag = (id: string) => {
    if (selectedItems.includes(id)) {
      setSelectedItems((prev) => prev.filter((tag) => tag !== id));
    } else {
      setSelectedItems((prev) => [...prev, id]);
    }
  };

  const selectedTags = selectedItems.map((tag) => {
    const foundTag = tagValueOptions.find((opt: any) => opt.id === tag);
    return {
      id: tag,
      label: foundTag ? foundTag.tagTitle : '',
    };
  });

  const handleClear = () => {
    setSelectedItems([]);
  };

  return (
    <ChipCardComboboxContainer
      selectedItems={selectedTags}
      defaultKeyword={searchKeyDefaultValue}
      handleSearchKeyWord={handleSearchChange}
      handleApply={handleApply}
      handleToggleTag={handleOnClickTag}
      handleClear={handleClear}
    >
      <GridContainerChip>
        {filteredTags.map((opt: any) => (
          <GridChipsCard
            key={opt.id}
            opt={opt}
            isSelected={selectedItems.includes(opt.id)}
            onClickAction={handleOnClickTag}
          />
        ))}
      </GridContainerChip>
    </ChipCardComboboxContainer>
  );
};

export default TagValuePopOverContent;
