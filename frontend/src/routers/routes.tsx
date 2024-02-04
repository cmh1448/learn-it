import DashBoardPage from "@/features/dashboard/pages/DashBoardPage";
import LoginPage from "@/features/login/pages/LoginPage";
import ProfilePage from "@/features/profile/pages/ProfilePage";
import NewStoryPage from "@/features/story/pages/NewStoryPage";
import StoryListPage from "@/features/story/pages/StoryListPage";
import WelcomePage from "@/features/welcome/pages/WelcomePage";
import { RouteObject } from "react-router-dom";

export type RouteContext = "NotLogined" | "Logined";

export const routes: Record<RouteContext, RouteObject[]> = {
  Logined: [
    {
      path: "/",
      element: <WelcomePage />,
    },
    {
      path: "/dashboard",
      element: <DashBoardPage />,
    },
    {
      path: "/login",
      element: <LoginPage />,
    },
    {
      path: "/profile",
      element: <ProfilePage />,
    },
    {
      path: "/stories",
      element: <StoryListPage />,
    },
    {
      path: "/stories/new",
      element: <NewStoryPage />,
    },
  ],
  NotLogined: [
    {
      path: "/",
      element: <WelcomePage />,
    },
    {
      path: "/*",
      element: <LoginPage />,
    },
  ],
};
