import { RouteObject } from "react-router-dom";

type RouteContext = "logined" | "unlogined";

export const routes: Record<RouteContext, RouteObject[]> = {
  logined: [
    {
      path: "/",
      element: <div>Home</div>,
    },
    {
      path: "/dashboard",
      element: <div>Dashboard</div>,
    },
    {
      path: "/login",
      element: <div>Login</div>,
    },
  ],
  unlogined: [
    {
      path: "/",
      element: <div>Home</div>,
    },
    {
      path: "*",
      element: <div>Login</div>,
    },
  ],
};
