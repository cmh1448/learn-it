import api from "@/api";
import Icon from "@/components/base/Icon";
import Skeleton from "@/components/base/Skeleton";
import PageContainer from "@/components/pages/PageContainer";
import { Suspense } from "react";
import { useQuery } from "react-query";
import { useNavigate } from "react-router-dom";
import StoryItem from "../components/StoryItem";

export default function DashBoardPage() {
  const navigate = useNavigate();

  const myStories = useQuery(["myStories"], () => api.story.fetchMyStories());

  return (
    <PageContainer>
      <div className="flex flex-col">
        <span className="text-gray-500 text-[12px]">오늘은 뭐하지?</span>
        <span className="font-bold text-2xl flex items-center gap-1">
          <Icon icon="book" />
          내가 집중중인 스토리
        </span>
        <div className="flex pt-2 gap-4">
          <Suspense
            fallback={
              <div className="flex flex-col items-center gap-1 w-fit">
                <Skeleton className="w-[140px] h-[200px] rounded-lg shadow" />
                <Skeleton className="w-[60px] h-[20px]" />
              </div>
            }
          >
            {!myStories.data?.content.length ? (
              <div className="my-3 opacity-50">스토리가 존재하지 않습니다.</div>
            ) : null}

            {myStories.data?.content.map((story) => (
              <StoryItem story={story} />
            ))}
          </Suspense>
        </div>
      </div>
      <div className="flex flex-col pt-8">
        <span className="text-gray-500 text-[12px]">향상의 지름길은 복습!</span>
        <span className="font-bold text-2xl flex items-center gap-1">
          <Icon icon="note" />
          작성한 노트 다시보기
        </span>
        <div className="flex pt-2 gap-4 overflow-x-auto remove-scrollbar"></div>
      </div>
    </PageContainer>
  );
}
