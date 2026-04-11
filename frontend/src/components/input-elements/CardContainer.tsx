import React from 'react';

interface CardContainerProps {
  children: React.ReactNode;
}

const CardContainer = ({ children }: CardContainerProps) => {
  return <div className='p-5 rounded shadow-md bg-white'>{children}</div>;
};

export default CardContainer;
