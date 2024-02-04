import api from "@/api";
import Button from "@/components/base/Button";
import Icon from "@/components/base/Icon";
import PageContainer from "@/components/pages/PageContainer";
import StoryItem from "@/features/dashboard/components/StoryItem";
import { Story } from "@/models/Story";
import { useMemo } from "react";
import { useQuery } from "react-query";
import { useNavigate } from "react-router-dom";

export default function StoryListPage() {
  const navigate = useNavigate();

  const myStories = useQuery(["myStories"], () => api.story.fetchMyStories());

  const fetchedStories = useMemo(
    () => myStories.data?.content,
    [myStories.data]
  );

  const handleViewStory = (story: Story) => {
    navigate(`/stories/${story.id}`);
  };

  const handleNewStory = () => {
    navigate("/stories/new");
  };

  return (
    <PageContainer>
      <div className="flex justify-end">
        <Button onClick={handleNewStory}>
          <Icon icon="book" /> 새 스토리
        </Button>
      </div>
      <div className="flex flex-col gap-4">
        <div className="text-2xl font-bold flex items-center gap-1 text-blue-500">
          <Icon icon="pending" className="text-3xl" />
          진행중인 스토리
        </div>
        <div className="grid place-items-center grid-cols-2 md:grid-cols-4 gap-y-6">
          {fetchedStories?.length ? (
            fetchedStories.map((story) => (
              <StoryItem onClick={() => handleViewStory(story)} story={story} />
            ))
          ) : (
            <div>스토리가 존재하지 않습니다.</div>
          )}
        </div>
        <div className="text-2xl font-bold flex items-center gap-1 text-gray-500">
          <Icon icon="check_circle" className="text-3xl" />
          완료한 스토리
        </div>
        <div className="grid place-items-center grid-cols-2 md:grid-cols-4 gap-y-6">
          {fetchedStories?.length ? (
            fetchedStories.map((story) => (
              <StoryItem onClick={() => handleViewStory(story)} story={story} />
            ))
          ) : (
            <div>스토리가 존재하지 않습니다.</div>
          )}
        </div>
      </div>
    </PageContainer>
  );
}
