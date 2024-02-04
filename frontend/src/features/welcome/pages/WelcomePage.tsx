import Logo from "@/components/Logo";
import Button from "@/components/base/Button";
import Icon from "@/components/base/Icon";
import { loginStore } from "@/stores/loginStore";
import { useNavigate } from "react-router-dom";
import { useStore } from "zustand";

export default function WelcomePage() {
  const navigate = useNavigate();
  const loginContext = useStore(loginStore);

  const handleStart = () => {
    if (loginContext.isLogined()) navigate("/dashboard");
    else navigate("/login");
  };

  return (
    <div className="h-full flex flex-col items-center justify-center">
      <Logo size="large" />
      <div className=" h-px w-80 bg-gray-500 my-5" />
      <div className=" text-2xl font-bold text-gray-700 md:text-4xl">
        함께하는 스터디 플랫폼, <span className="text-gray-900">러닛</span>
        <span className="text-blue-500">!</span>
      </div>
      <div className="text-gray-400 px-2 py-3 text-sm md:text-lg text-center">
        러닛은 같은 주제를 공부하는 사람들이 함께 모여
        <br />
        온라인 그룹 스터디를 할 수 있는 플랫폼입니다.
        <br />
      </div>
      <div className="flex gap-4 py-5">
        <Button onClick={handleStart}>
          {loginContext.isLogined() ? (
            <div className="flex gap-1">
              <Icon icon="resume" fill />
              <span>
                <span className=" font-bold">
                  {loginContext.user?.nickname}
                </span>
                님으로 계속
              </span>
            </div>
          ) : (
            "로그인하여 시작하기"
          )}
        </Button>
        <Button variant={"third"}>더 알아보기</Button>
      </div>
    </div>
  );
}
