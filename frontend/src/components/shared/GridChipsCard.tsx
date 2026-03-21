import React from 'react';

interface GridChipsCardProps {
  opt: {
    id: string;
    label: string;
  };
  isSelected: boolean;
  onClickAction?: (id: string) => void;
}

const GridChipsCard = ({
  opt,
  isSelected,
  onClickAction,
}: GridChipsCardProps) => {
  const handleClick = () => {
    if (onClickAction) {
      onClickAction(opt.id);
    }
  };
  return (
    <div
      key={opt.id}
      className={`whitespace-nowrap px-2 py-1 rounded-full ${isSelected ? 'bg-brand-primary font-bold text-white' : 'bg-slate-100 font-bold text-black'} border-solid border-#e5e7eb border-2 cursor-pointer`}
      onClick={handleClick}
    >
      <span className='p-2'>{opt.label.toUpperCase()}</span>
    </div>
  );
};

export default GridChipsCard;
