import Divider from "@/components/base/Divider";
import { Note } from "@/models/Story";
import { elapsedStringOf } from "@/utils/TimeUtil";
import { HTMLAttributes } from "react";

export interface NoteItemProps extends HTMLAttributes<HTMLDivElement> {
  note: Note;
}

export default function NoteItem(props: NoteItemProps) {
  return (
    <div className="flex flex-col gap-2 select-none cursor-pointer">
      <div
        className="relative min-w-[200px] min-h-[120px] p-4 bg-amber-200 rounded-lg overflow-hidden
      flex items-center justify-center shadow flex-col gap-2  active:scale-95 hover:bg-amber-300 transition-all active:shadow-none"
      >
        <div className="absolute w-[30px] h-[30px] bg-amber-50 right-[-15px] top-[-15px] rounded shadow" />
        <div className="font-bold flex items-center">{props.note.title}</div>
        <div className="flex gap-1 flex-col text-[12px]">
          <div className="text-gray-500 flex items-center gap-1">
            {props.note.content}
          </div>
        </div>
      </div>
      <div className="flex gap-1 text-sm items-center justify-center text-gray-400">
        노트 <Divider variant="dot" className="bg-gray-400" />
        {elapsedStringOf(props.note.modifiedAt)}
      </div>
    </div>
  );
}
