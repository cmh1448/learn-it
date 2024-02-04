import Icon from "@/components/base/Icon";
import { User } from "@/models/User";
import { HTMLAttributes } from "react";

export interface ProfileImageProps extends HTMLAttributes<HTMLDivElement> {
  user: User;
}

export function ProfileImage(props: ProfileImageProps) {
  return (
    <div
      className={`aspect-square rounded-full bg-gray-200 w-20 flex items-center justify-center ${props.className}`}
    >
      {props.user?.profileImage ? <span>Image</span> : <Icon icon="person" />}
    </div>
  );
}
