import { User } from "@/models/User";
import { DateTime } from "luxon";
import { createStore } from "zustand";

export interface LoginContext {
  user: User | null;
  accessToken: string | null;
  expire: string;
  isLogined: () => boolean;

  login: (
    context: Omit<LoginContext, "isLogined" | "login" | "logout">
  ) => void;
  logout: () => void;
}

const loadStoredContext = () => {
  const savedContext = JSON.parse(
    localStorage.getItem("SessionContext") ?? "{}"
  ) as LoginContext;

  return savedContext;
};

const updateSavedContext = (
  mapper: (context: LoginContext) => LoginContext
) => {
  const current = loadStoredContext();
  const updated = mapper(current);

  localStorage.setItem("SessionContext", JSON.stringify(updated));
};

export const loginStore = createStore<LoginContext>((set, get) => ({
  user: loadStoredContext()?.user,
  accessToken: loadStoredContext()?.accessToken,
  expire: loadStoredContext()?.expire,
  isLogined: () => isLogined(get()) ?? false,

  login: (context) => {
    set({
      user: context.user,
      accessToken: context.accessToken,
      expire: context.expire,
    });
  },

  logout: () => {
    set({
      user: undefined,
      accessToken: undefined,
      expire: undefined,
    });
  },
}));

const isLogined = (state: LoginContext) =>
  state.user && DateTime.fromISO(state.expire) > DateTime.now();

loginStore.subscribe((val) => updateSavedContext(() => val));
