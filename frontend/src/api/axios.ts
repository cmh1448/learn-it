import { loginStore } from "@/stores/loginStore";
import axios from "axios";

const instance = axios.create({});

const loginContext = loginStore.getState();

instance.interceptors.request.use((req) => {
  if (loginContext.isLogined())
    req.headers.Authorization = `Bearer ${loginContext.accessToken}`;

  return req;
});

instance.interceptors.response.use(
  (response) => response,
  (error) => {
    return Promise.reject(error.response.data);
  }
);

export default instance;
