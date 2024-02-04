import { DateTime } from "luxon";
import Icon from "@/components/base/Icon";
import Divider from "@/components/base/Divider";
import Button from "@/components/base/Button";
import { useStore } from "zustand";
import { loginStore } from "@/stores/loginStore";
import PageContainer from "@/components/pages/PageContainer";
import { ProfileImage } from "../components/ProfileImage";
import { useNavigate } from "react-router-dom";

export default function ProfilePage() {
  const loginContext = useStore(loginStore);
  const navigate = useNavigate();

  const handleLogout = () => {
    loginContext.logout();
    navigate("/login");
  };

  const expireLeft = () =>
    DateTime.fromISO(loginContext.expire).diffNow().as("hours");

  return (
    <PageContainer>
      <div className="flex flex-col items-center gap-4">
        <div className="flex flex-col items-center gap-3">
          <ProfileImage user={loginContext.user!} />
          <span className="text-2xl font-bold">
            {loginContext.user?.nickname}
          </span>
          <span className="text-lg flex items-center gap-1 text-gray-600">
            <Icon icon="person" fill />
            {loginContext.user?.name}
          </span>
          <span className="text-lg flex items-center gap-1 text-gray-600">
            <Icon icon="email" fill />
            {loginContext.user?.email}
          </span>
        </div>
        <Divider variant="dot" />
        <div className="text-gray-500">
          로그인 세션 종료까지 {expireLeft().toFixed()}시간 남음
        </div>
        <Divider variant="dot" />
        <Button
          variant="primary"
          sizeType="big"
          className="bg-red-600 hover:bg-red-700"
          onClick={handleLogout}
        >
          <Icon icon="logout" />
          로그아웃
        </Button>
        <Button variant="primary">
          
        </Button>
      </div>
    </PageContainer>
  );
}
