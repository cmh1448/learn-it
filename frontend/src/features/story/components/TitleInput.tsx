export interface TitleInputProps {
  placeholder?: string;

  onInput?: (val: string) => void;

  className?: string;
}

export default function TitleInput(props: TitleInputProps) {
  const { onInput, placeholder } = props;

  return (
    <div className={` border-b-2 border-b-gray-300 pb-2 ${props.className}`}>
      <input
        type="text"
        className="bg-transparent w-full h-full text-3xl placeholder:text-gray-200"
        placeholder={placeholder}
        onInput={(e) => onInput && onInput(e.currentTarget.value)}
      />
    </div>
  );
}
