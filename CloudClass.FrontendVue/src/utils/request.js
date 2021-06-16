import axios from "axios";
import store from "@/store";

const service = axios.create({
  baseURL: "http://8.140.0.237:8080/api/CloudClass", // url = base url + request url
  timeout: 5000,
});

service.interceptors.request.use(
  (config) => {
    if (store.state.token) {
      config.headers["Authorization"] = `Bearer ${store.state.token.token}`;
    }
    return config;
  },
  (error) => {
    console.log(error);
    return Promise.reject(error);
  }
);

service.interceptors.response.use(
  (response) => {
    if (response.status === 403) {
      // Message.error("您无权访问当前页面");
      this.$router.replace({ path: "/login" });
    }
    return response;
  },
  (error) => {
    console.log("err" + error);
    if (error.response) {
      switch (error.response.status) {
        case 401:
        // Message.error("您未登录或登录已过期，请登录后重试");
        // TODO: remove token from localstorage
      }
      return Promise.reject(error);
    }
  }
);

export default service;
