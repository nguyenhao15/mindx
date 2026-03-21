import { Textarea } from '../ui/textarea';

interface TextAreaInputProps {
  className?: string;
  description?: string;
}

const TextAreaInput = ({
  className,
  description,
  ...props
}: TextAreaInputProps) => {
  return (
    <div className={className}>
      <Textarea rows={4} placeholder={description} {...props} />
    </div>
  );
};

export default TextAreaInput;
