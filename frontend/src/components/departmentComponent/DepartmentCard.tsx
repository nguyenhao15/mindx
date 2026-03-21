import { ReactSVG } from 'react-svg';

interface DepartmentCardProps {
  data: any;
  onView: (data: any) => void;
}

const defaultIcon =
  'data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0IiBmaWxsPSJub25lIiBzdHJva2U9ImN1cnJlbnRDb2xvciIgc3Ryb2tlLXdpZHRoPSIyIiBzdHJva2UtbGluZWNhcD0icm91bmQiIHN0cm9rZS1saW5lam9pbj0icm91bmQiIGNsYXNzPSJsdWNpZGUgbHVjaWRlLWNoZXZyb24tZG93bi1pY29uIGx1Y2lkZS1jaGV2cm9uLWRvd24iPjxwYXRoIGQ9Im02IDkgNiA2IDYtNiIvPjwvc3ZnPg==';

const DepartmentCard = ({ data, onView }: DepartmentCardProps) => {
  return (
    <div
      onClick={() => onView(data)}
      className='group p-6 bg-white dark:bg-slate-800 rounded-2xl border border-slate-100 dark:border-slate-700 hover:shadow-xl hover:shadow-slate-200/40 dark:hover:shadow-none hover:-translate-y-1 transition-all cursor-pointer'
    >
      <div className='size-12 bg-brand-primary/10 rounded-xl flex items-center justify-center text-brand-primary mb-4 group-hover:bg-brand-primary group-hover:text-white transition-colors'>
        <ReactSVG
          src={data?.iconSvg || defaultIcon}
          className='icon-custom transition-colors duration-300
                   fill-current text-red-500 
                   group-hover:text-white'
        />
      </div>
      <h4 className='font-bold text-lg mb-1'>{data.departmentName}</h4>
      <p className='text-sm text-slate-500 dark:text-slate-400'>
        {data.description}
      </p>
    </div>
  );
};

export default DepartmentCard;
