import { BubbleMenu, EditorProvider, FloatingMenu } from "@tiptap/react";
import StarterKit from "@tiptap/starter-kit";
import tiptapStyle from "../../assets/tiptap.css";

const extensions = [StarterKit];

const content = `<p>Hello World!</p>`;

const TextEditor = () => {
  return (
    <EditorProvider
      extensions={extensions}
      content={content}
      editorProps={{
        attributes: {
          class:
            "prose dark:prose-invert prose-sm sm:prose-base lg:prose-lg xl:prose-2xl p-1 focus:outline-none border-l border-black",
        },
      }}
    >
      <FloatingMenu>Floating Menu</FloatingMenu>
      <BubbleMenu>BubbleMenu</BubbleMenu>
    </EditorProvider>
  );
};

export default TextEditor;
