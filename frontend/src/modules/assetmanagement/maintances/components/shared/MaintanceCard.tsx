import React from 'react';

interface MaintanceCardProps {
  item: any;
}

const MaintanceCard = ({ item }: MaintanceCardProps) => {
  return <div className='p-2 rounded-lg shadow'>MaintanceCard</div>;
};

export default MaintanceCard;
