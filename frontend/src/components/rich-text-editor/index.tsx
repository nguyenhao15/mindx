import { EditorContent, useEditor } from '@tiptap/react';
import { StarterKit } from '@tiptap/starter-kit';
import TextAlign from '@tiptap/extension-text-align';
import Highlight from '@tiptap/extension-highlight';
import MenuBar from './menu-bar';
import { Markdown } from 'tiptap-markdown'; // Import thư viện mới

export default function RichTextEditor() {
  const editor = useEditor({
    extensions: [
      StarterKit.configure({
        bulletList: {
          HTMLAttributes: {
            class: 'ml-4 list-disc',
          },
        },
        orderedList: {
          HTMLAttributes: {
            class: 'ml-4 list-decimal',
          },
        },
      }),
      Markdown,
      TextAlign.configure({
        types: ['heading', 'paragraph'],
      }),
      Highlight,
    ], // define your extension array
    content: '<p>Hello World!</p>', // initial content
    editorProps: {
      attributes: {
        class: 'min-h-[156px] border rounded-md bg-slate-50 py-2 px-3',
      },
    },
    onUpdate: ({ editor }) => {
      const text = editor.storage.markdown.getMarkdown(); // Lấy nội dung Markdown
      console.log('Content updated:', text);
    },
  });

  return (
    <>
      <MenuBar editor={editor} />
      <EditorContent editor={editor} />
    </>
  );
}
