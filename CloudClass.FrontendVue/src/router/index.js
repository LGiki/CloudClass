import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    meta: {
      title: "主页",
    },
    component: () => import("../views/Home"),
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
];

const router = new VueRouter({
  routes,
});

router.beforeEach((to, from, next) => {
  document.title = to.meta.title;
  next();
});

export default router;
