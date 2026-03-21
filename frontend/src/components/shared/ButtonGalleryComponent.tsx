import React from 'react';

interface ButtonGalleryComponentProps {
  direction?:
    | 'start'
    | 'end'
    | 'center'
    | 'space-between'
    | 'space-around'
    | 'space-evenly';
  children: React.ReactNode;
  gap?: string;
  className?: string;
}

const ButtonGalleryComponent: React.FC<ButtonGalleryComponentProps> = ({
  direction = 'start',
  children,
  gap = '12px',
  className = '',
}) => {
  const justifyContentMap: Record<string, string> = {
    start: 'flex-start',
    end: 'flex-end',
    center: 'center',
    'space-between': 'space-between',
    'space-around': 'space-around',
    'space-evenly': 'space-evenly',
  };

  const justifyContent = justifyContentMap[direction] || 'flex-start';

  const containerStyle: React.CSSProperties = {
    display: 'flex',
    justifyContent: justifyContent,
    gap: gap,
    alignItems: 'center',
    flexWrap: 'wrap',
  };

  return (
    <div style={containerStyle} className={className}>
      {children}
    </div>
  );
};

export default ButtonGalleryComponent;
