import RichTextEditor from './rich-text-editor';

export const TiptapEditor = ({}: {
  content: string;
  onChange: (html: string) => void;
}) => {
  return (
    <div className='max-w-3xl mx-auto py-8 w-full rounded-md bg-slate-200 p-5 h-fit m-5 '>
      <RichTextEditor />
    </div>
  );
};
