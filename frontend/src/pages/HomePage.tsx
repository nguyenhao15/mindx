import { MODULE_NAME } from '@/constants/module-const';

const HomePage = () => {
  return (
    <div className='flex flex-col items-center justify-center h-screen'>
      <h1 className='text-4xl font-bold mb-4'>Welcome to the Home Page</h1>
      <p className='text-lg text-gray-600'>
        This is the home page of the application. Use the navigation to explore
        different modules.
      </p>
      <div className='mt-6'>
        <h2 className='text-2xl font-semibold mb-2'>Available Modules:</h2>
        <ul className='list-disc list-inside text-gray-700'>
          {MODULE_NAME.map((module) => (
            <li key={module.path} className='mb-1'>
              {module.title}
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default HomePage;
