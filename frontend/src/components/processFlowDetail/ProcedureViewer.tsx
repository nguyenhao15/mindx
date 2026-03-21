import ReactMarkdown from 'react-markdown';
import remarkGfm from 'remark-gfm';

const ProcedureViewer = ({ content }: { content: string }) => {
  return (
    <div className='prose max-w-none prose-slate px-4 py-8'>
      <ReactMarkdown remarkPlugins={[remarkGfm]}>{content}</ReactMarkdown>
    </div>
  );
};

export default ProcedureViewer;
