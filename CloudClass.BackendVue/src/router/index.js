import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/reset-password',
    component: () => import('@/views/login/reset_password'),
    hidden: true
  },
  {
    path: '/signup',
    component: () => import('@/views/login/signup'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/403',
    component: () => import('@/views/403'),
    hidden: true
  },

  {
    path: '/401',
    component: () => import('@/views/401'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: {title: '主页', icon: 'dashboard'}
    }]
  },

  {
    path: '/user',
    component: Layout,
    redirect: '/user/index',
    meta: {title: '用户管理', icon: 'peoples'},
    children: [
      {
        path: 'index',
        name: 'User List',
        component: () => import('@/views/user/list'),
        meta: {
          title: '用户列表'
        }
      },
      {
        path: 'edit/:id',
        name: 'User List',
        component: () => import('@/views/user/edit'),
        hidden: true,
        meta: {
          title: '编辑用户'
        }
      }
    ]
  },
  {
    path: '/data-dict',
    component: Layout,
    redirect: '/data-dict/index',
    meta: {title: '数据字典', icon: 'dict'},
    children: [
      {
        path: 'index',
        name: 'Data Dict List',
        component: () => import('@/views/data-dict/list'),
        meta: {
          title: '数据字典'
        }
      },
      {
        path: 'edit/:id',
        name: 'Data Dict List',
        component: () => import('@/views/data-dict/edit'),
        hidden: true,
        meta: {
          title: '编辑数据字典'
        }
      }
    ]
  },
  {
    path: '/class',
    name: 'Class',
    redirect: '/class/index',
    component: Layout,
    meta: {
      title: '班课管理'
    },
    children: [{
      path: 'index',
      name: 'Class List',
      component: () => import('@/views/class/list'),
      meta: {
        title: '班课管理'
      }
    }]
  },
  {
    path: '/system',
    component: Layout,
    // redirect: '/system/menu',
    name: 'system-configure',
    meta: {title: '系统设置', icon: 'setting'},
    children: [
      {
        path: 'menu',
        name: 'Menu',
        component: () => import('@/views/system/menu'),
        meta: {
          title: '菜单管理'
        }
      },
      {
        path: 'role',
        name: 'Role',
        component: () => import('@/views/system/role'),
        meta: {
          title: '角色管理'
        }
      },
      {
        path: 'parameter',
        name: 'Config',
        component: () => import('@/views/system/parameter'),
        meta: {
          title: '系统参数'
        }
      }
    ]
  },
  // 404 page must be placed at the end !!!
  {path: '*', redirect: '/404', hidden: true}
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({y: 0}),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
