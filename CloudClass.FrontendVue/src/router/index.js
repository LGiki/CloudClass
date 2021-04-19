import Vue from "vue";
import VueRouter from "vue-router";
import AfterLoginLayout from "../layout/AfterLoginLayout";

Vue.use(VueRouter);

const routes = [
    {
        path: "/",
        meta: {
            title: "登录",
            stuTitle: "登录",
        },
        // component: () => import("../views/Home"),
        component: () => import("../views/Login"),
    },
    {
        path: "/login",
        meta: {
            title: "登录",
            stuTitle: "登录",
        },
        component: () => import("../views/Login"),
    },
    {
        path: "/register",
        meta: {
            title: "注册",
            stuTitle: "注册",
        },
        component: () => import("../views/Register"),
    },
    {
        path: "/",
        meta: {
            title: "关于",
            stuTitle: "关于",
        },
        component: AfterLoginLayout,
        children: [
            {
                path: "/about",
                meta: {
                    title: "关于",
                    stuTitle: "关于",
                },
                component: () => import("../views/About"),
            },
            {
                path: "/class",
                meta: {
                    title: "班课管理",
                    stuTitle: "我加入的班课",
                },
                component: () => import("../views/ClassList"),
            },
            {
                path: "/sign",
                meta: {
                    title: "发起签到",
                    stuTitle: "签到",
                },
                component: () => import("../views/Sign"),
            },
            {
                path: "/addClass",
                meta: {
                    title: "创建班课",
                    stuTitle: "创建班课",
                },
                component: () => import("../views/AddClass"),
            },
            {
                path: "/enterClass",
                meta: {
                    title: "加入班课",
                    stuTitle: "加入班课",
                },
                component: () => import("../views/EnterClass"),
            },
            {
                path: "/classDetail",
                meta: {
                    title: "班课详情",
                    stuTitle: "班级详情",
                },
                component: () => import("../views/ClassDetail"),
            }
        ]
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
