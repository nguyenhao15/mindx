const ErrorCatchComponent = ({ error }: { error: any }) => {
  return (
    <div className='bg-red-100 text-red-700 p-3 rounded'>
      {error?.response?.data?.message || 'An error occurred.'}
    </div>
  );
};

export default ErrorCatchComponent;
