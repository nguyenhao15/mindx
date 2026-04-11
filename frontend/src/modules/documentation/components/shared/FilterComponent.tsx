import { FaFilter, FaSearch, FaTag } from 'react-icons/fa';

import { Button } from '../ui/button';
import { useState } from 'react';

import InputWithIcon from '../input-elements/InputWithIcon';
import PopOverComponent from './PopOverComponent';
import TagPopOverContent from '../tag/TagPopOverContent';
import TagValuePopOverContent from '../tag/TagValuePopOverContent';
import type { FilterInput } from '@/modules/documentation/validations/filterWithPagination';

interface FilterComponentProps {
  filters: FilterInput[];
  setFilters: (filters: FilterInput[]) => void;
  loading: boolean;
  searchKeyword?: string;
  setSearchKeyword?: (keyword: string) => void;
}

const FilterComponent = ({
  filters,
  setFilters,
  loading,
  searchKeyword,
  setSearchKeyword,
}: FilterComponentProps) => {
  const [isTagOpen, setIsTagOpen] = useState(false);
  const [isTagValueOpen, setIsTagValueOpen] = useState(false);
  const [tagSelected, setTagSelected] = useState<string[]>([]);
  const [tagValueSelected, setTagValueSelected] = useState<string[]>([]);
  const [localSearchKeyword, setLocalSearchKeyword] = useState('');

  const handleApplySelectedChips = () => {
    let nextFilters = [...filters];
    let updates: { field: string; value: string[]; operator: string }[] = [];
    updates.push({
      field: 'tagItems._id',
      value: tagSelected,
      operator: 'IN',
    });
    updates.push({
      field: 'tagIdValues._id',
      value: tagValueSelected,
      operator: 'IN',
    });

    updates.forEach(({ field, value }) => {
      const existingFilterIndex = nextFilters.findIndex(
        (f) => f.field === field,
      );
      if (existingFilterIndex !== -1) {
        nextFilters[existingFilterIndex].value = value;
      } else {
        nextFilters.push({ field, value, operator: 'IN' });
      }
    });
    isTagOpen ? setIsTagOpen(false) : setIsTagValueOpen(false);
    nextFilters = nextFilters.filter((f) => {
      if (f.field === 'tagItems._id') {
        return f.value.length > 0;
      }
      if (f.field === 'tagIdValues._id') {
        return f.value.length > 0;
      }
      return true;
    });
    setFilters(nextFilters);
  };

  const handleClearFilters = () => {
    setTagSelected([]);
    setTagValueSelected([]);

    setFilters([]);
  };

  const applyValueSelectedChips = (data: string[]) => {
    isTagOpen ? setTagSelected(data) : setTagValueSelected(data);
    setIsTagOpen(false);
    setIsTagValueOpen(false);
  };

  const handleToggleTagPopOver = () => {
    setIsTagOpen(!isTagOpen);
    if (isTagValueOpen) {
      setIsTagValueOpen(false);
    }
  };

  return (
    <div className='flex flex-col gap-2 p-3 bg-white rounded border shadow dark:bg-slate-800 dark:border-slate-700'>
      <div className='flex gap-2 pl-5 py-3 items-center text-brand-primary dark:text-brand-primary-dark'>
        <FaFilter className='text-brand-primary dark:text-white' />
        <span className='font-bold text-slate-800 dark:text-white'>
          Filter Documents
        </span>
      </div>
      <div className='px-10 flex flex-row gap-4 items-center '>
        <InputWithIcon
          placeholder='Type keyword...'
          Icon={FaSearch}
          defaultValue={searchKeyword}
          isLoading={loading}
          onChange={(value) => setSearchKeyword && setSearchKeyword(value)}
        />

        <PopOverComponent
          onToggle={handleToggleTagPopOver}
          Icon={FaTag}
          buttonLabel={
            tagSelected.length > 0
              ? 'Tags (' + tagSelected.length + ')'
              : 'Tags'
          }
          isLoading={loading}
          variant={tagSelected.length > 0 ? 'positive' : 'outline'}
          isOpen={isTagOpen}
        >
          <TagPopOverContent
            onApply={applyValueSelectedChips}
            selectedTagsDefaultValue={tagSelected}
          />
        </PopOverComponent>
        <PopOverComponent
          onToggle={() => {
            setIsTagValueOpen(!isTagValueOpen);
            if (isTagOpen) {
              setIsTagOpen(false);
            }
          }}
          isLoading={loading}
          Icon={FaTag}
          buttonLabel={
            tagValueSelected.length > 0
              ? 'Tag Values (' + tagValueSelected.length + ')'
              : 'Tag Values'
          }
          variant={tagValueSelected.length > 0 ? 'positive' : 'outline'}
          isOpen={isTagValueOpen}
        >
          <TagValuePopOverContent
            onApply={applyValueSelectedChips}
            selectedTagsDefaultValue={tagValueSelected}
          />
        </PopOverComponent>
      </div>
      <div className='flex flex-row flex-wrap gap-2 self-end'>
        <Button
          className='self-end cursor-pointer'
          variant='outline'
          size={'default'}
          onClick={handleClearFilters}
        >
          Clear Filters
        </Button>
        <Button
          className='self-end cursor-pointer'
          variant='positive'
          size={'default'}
          onClick={handleApplySelectedChips}
        >
          Apply Filters
        </Button>
      </div>
    </div>
  );
};

export default FilterComponent;
