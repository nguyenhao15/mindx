import { RotatingLines } from 'react-loader-spinner';

const Loader = ({ text }: { text?: string }) => {
  return (
    <div className='flex justify-center items-center w-full h-full'>
      <div className='flex flex-col items-center gap-1'>
        <RotatingLines
          visible={true}
          height='96'
          width='96'
          color='#283618'
          strokeWidth='5'
          animationDuration='0.7'
          ariaLabel='rotating-lines-loading'
          wrapperStyle={{}}
          wrapperClass=''
        />
        <p className='text-slate-800'>{text ? text : 'Please wait'}</p>
      </div>
    </div>
  );
};

export default Loader;
