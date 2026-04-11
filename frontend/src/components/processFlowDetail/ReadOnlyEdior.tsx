import { useEditor, EditorContent } from '@tiptap/react';
import { StarterKit } from '@tiptap/starter-kit';
import { Image } from '@tiptap/extension-image';
import { TaskItem, TaskList } from '@tiptap/extension-list';
import { TextAlign } from '@tiptap/extension-text-align';
import { Typography } from '@tiptap/extension-typography';
import { Highlight } from '@tiptap/extension-highlight';
import { Subscript } from '@tiptap/extension-subscript';
import { Superscript } from '@tiptap/extension-superscript';
import { Markdown } from 'tiptap-markdown';

import '@/components/tiptap-node/blockquote-node/blockquote-node.scss';
import '@/components/tiptap-node/code-block-node/code-block-node.scss';
import '@/components/tiptap-node/horizontal-rule-node/horizontal-rule-node.scss';
import '@/components/tiptap-node/list-node/list-node.scss';
import '@/components/tiptap-node/image-node/image-node.scss';
import '@/components/tiptap-node/heading-node/heading-node.scss';
import '@/components/tiptap-node/paragraph-node/paragraph-node.scss';
import '@/components/tiptap-templates/simple/simple-editor.scss';
import Loader from '../shared/Loader';
import ModalComponent from '../shared/ModalComponent';
import { useState } from 'react';
import { FilePreviewer } from '../shared/FilePreview';

export default function ReadOnlyEdior({
  loading,
  content,
}: {
  loading: boolean;
  content?: string;
}) {
  const [openModal, setOpenModal] = useState(false);
  const [selectedImage, setSelectedImage] = useState<string | null>(null);

  if (loading) {
    return <Loader text={'Loading...'} />;
  }

  const handleOnImageClick = (data) => {
    setOpenModal(true);
    setSelectedImage(data);
    console.log();
  };

  const editor = useEditor({
    immediatelyRender: false,
    editable: false,
    extensions: [StarterKit, Markdown, Image],
    content: content,
    editorProps: {
      handleClickOn(view, pos, node, nodePos, event, direct) {
        if (!direct) return false; // Chỉ xử lý sự kiện click trực tiếp trên node, không xử lý sự kiện bubble từ con của nó
        if (node.type.name !== 'image') return false;
        handleOnImageClick({
          src: node.attrs.src,
          alt: node.attrs.alt,
          title: node.attrs.title,
        });
        return true;
      },
    },
  });

  return (
    <div className='flex flex-col'>
      <EditorContent
        editor={editor}
        role='presentation'
        className='simple-editor-content'
      />
      <ModalComponent open={openModal} onClose={() => setOpenModal(false)}>
        <FilePreviewer
          type={selectedImage?.src}
          fileUrl={selectedImage?.src || ''}
        />
      </ModalComponent>
    </div>
  );
}
