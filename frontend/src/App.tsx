import { useEffect, useState } from "react";
import { useLocation, useRoutes } from "react-router-dom";
import { routes } from "./routers/routes";
import { useStore } from "zustand";
import { loginStore } from "./stores/loginStore";
import NavBar from "./components/navigation/NavigationBar";
import { Navigation } from "./models/Naviation";
import { CSSTransition, SwitchTransition } from "react-transition-group";

export default function App() {
  const loginContext = useStore(loginStore);
  const [activeRoutes, setActiveRoutes] = useState(routes.NotLogined);
  const location = useLocation();

  useEffect(() =>
    setActiveRoutes(
      loginContext.isLogined() ? routes.Logined : routes.NotLogined
    )
  );

  const statefulRoutes = useRoutes(activeRoutes);

  const navigations: Navigation[] = [
    {
      title: "대시보드",
      icon: "home",
      path: "/dashboard",
    },
    {
      title: "내 스토리",
      icon: "book",
      path: "/stories",
    },
    {
      title: "프로필",
      icon: "person",
      path: "/profile",
    },
  ];

  return (
    <>
      <div className="h-full flex flex-col overflow-hidden">
        {loginContext.isLogined() ? <NavBar navigations={navigations} /> : null}
        <SwitchTransition mode="out-in">
          <CSSTransition
            key={location.pathname}
            classNames="scale"
            timeout={300}
          >
            {statefulRoutes}
          </CSSTransition>
        </SwitchTransition>
      </div>
    </>
  );
}
