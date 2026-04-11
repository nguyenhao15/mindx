import React from 'react';
import { ReactSVG } from 'react-svg';

interface HeaderPageProps {
  title: string;
  subtitle: string;
  Icon: React.ElementType;
}

interface HeaderPageProps {
  title: string;
  subtitle: string;
  Icon: React.ElementType;
  svgLink?: string; // Optional prop for SVG link
}

const HeaderPage = ({ title, subtitle, Icon, svgLink }: HeaderPageProps) => {
  return (
    <div className='px-10 py-2 text'>
      <div className='flex flex-row gap-2 my-auto bg-brand-primary text-white w-fit px-3 py-1 rounded-lg items-center shadow-sm mb-4'>
        {svgLink ? (
          <ReactSVG
            src={svgLink}
            className='icon-custom fill-current text-white '
          />
        ) : (
          <Icon size={28} className='' />
        )}
        <h1 className='text-2xl my-auto p-2 font-bold'>{title}</h1>
      </div>
      <p>{subtitle}</p>
    </div>
  );
};

export default HeaderPage;
