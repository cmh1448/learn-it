import Divider from "@/components/base/Divider";
import { Story } from "@/models/Story";
import { elapsedStringOf } from "@/utils/TimeUtil";
import { HTMLAttributes } from "react";

export interface StoryItemProps extends HTMLAttributes<HTMLDivElement> {
  story: Story;
}

export default function StoryItem(props: StoryItemProps) {
  return (
    <div className="flex flex-col gap-2 w-fit">
      <div
        className={`w-[140px] h-[200px] subpixel-antialiased transition-all cursor-pointer bg-gray-200 hover:bg-gray-300 rounded-lg select-none shadow-md p-2 backdrop-blur-lg active:scale-95 active:shadow-none ${props.className}`}
      >
        <div className="text-lg font-bold flex flex-col justify-center items-center  w-full h-full break-keep text-center">
          <div className="bg-gray-800 h-px w-20" />
          <span className="my-2">{props.story.title}</span>
          <div className="bg-gray-800 h-px w-20" />
        </div>
      </div>
      <div className="flex justify-center text-sm text-gray-400 items-center gap-1">
        {props.story.title} <Divider variant="dot" className=" bg-gray-400" />{" "}
        {elapsedStringOf(new Date(props.story.createdAt))}
      </div>
    </div>
  );
}
