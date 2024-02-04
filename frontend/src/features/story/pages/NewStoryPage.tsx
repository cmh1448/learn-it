import Button from "@/components/base/Button";
import Icon from "@/components/base/Icon";
import Input from "@/components/base/Input";
import TextEditor from "@/components/editor/TextEditor";
import PageContainer from "@/components/pages/PageContainer";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import TitleInput from "../components/TitleInput";
interface StoryForm {
  title: String;
  content: String;
}

export default function NewStoryPage() {
  const [storyTitle, setStoryTitle] = useState("");
  const [storyContent, setStoryContent] = useState("");

  const navigate = useNavigate();

  const onGoBack = () => {
    navigate(-1);
  };

  return (
    <PageContainer>
      <div className="flex justify-end items-center">
        <Button variant="third" onClick={onGoBack}>
          <Icon icon="arrow_back" />
          뒤로가기
        </Button>
      </div>
      <div className="font-bold text-4xl mt-4 text-gray-700">새 스토리</div>
      <div className="flex gap-4 flex-col mt-6">
        <div className="flex flex-col gap-1">
          <span className="text-blue-500 flex gap-1 items-center">
            <Icon icon="square" fill className="text-sm" />
            스토리 이름
          </span>
          <TitleInput
            onInput={(str) => setStoryTitle(str)}
            placeholder="이름 입력..."
          />
        </div>

        <div>
          <span className="text-blue-500 flex gap-1 items-center">
            <Icon icon="square" fill className="text-sm" />
            스토리 설명
          </span>
          <TextEditor />
        </div>
      </div>
    </PageContainer>
  );
}
