import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    meta: {
      title: "登录",
    },
    // component: () => import("../views/Home"),
    component: () => import("../views/Login"),
  },
  {
    path: "/login",
    meta: {
      title: "登录",
    },
    component: () => import("../views/Login"),
  },
  {
    path: "/about",
    meta: {
      title: "关于",
    },
    component: () => import("../views/About"),
  },
  {
    path: "/register",
    meta: {
      title: "注册",
    },
    component: () => import("../views/Register"),
  },
  {
    path: "/class",
    meta: {
      title: "班课管理",
    },
    component: () => import("../views/ClassList"),
  },
  {
    path: "/sign",
    meta: {
      title: "签到",
    },
    component: () => import("../views/Sign"),
  },
];

const router = new VueRouter({
  routes,
});

router.beforeEach((to, from, next) => {
  document.title = to.meta.title;
  next();
});

export default router;
