<template>
  <div :class="{'has-logo':showLogo}">
    <logo v-if="showLogo" :collapse="isCollapse"/>
    <el-scrollbar wrap-class="scrollbar-wrapper">
      <el-menu
        :collapse="isCollapse"
        :background-color="variables.menuBg"
        :text-color="variables.menuText"
        :unique-opened="false"
        :active-text-color="variables.menuActiveText"
        :collapse-transition="false"
        mode="vertical"
      >
        <sidebar-item
          v-for="route in menus"
          :key="route.meta.title"
          :item="route"
          :base-path="route.path"
        />
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script>
import {mapGetters} from 'vuex'
import Logo from './Logo'
import SidebarItem from './SidebarItem'
import variables from '@/styles/variables.scss'
import {getAllMenuWithoutPagination} from "@/api/menu";
import {getRoleMenu} from "@/api/role";
import menuMap from "@/menu_map";

export default {
  components: {SidebarItem, Logo},
  async mounted() {
    await this.getMenus()
  },
  data() {
    return {
      menus: []
    }
  },
  methods: {
    async getMenus() {
      // console.log('getMenus:', this.$store.state.token.role)
      let hasMenuConfig = false
      const menuResponse = await getAllMenuWithoutPagination()
      const roleMenuResponse = await getRoleMenu(this.$store.state.token.role)
      const menuList = menuResponse.data.data
      const roleMenu = roleMenuResponse.data.data
      if (menuList !== null && roleMenu !== null) {
        // 获取当前用户拥有的菜单ID列表
        const currentUserMenuIds = []
        for (const item of roleMenu) {
          if (item.status === 1) {
            currentUserMenuIds.push(item.mid)
          }
        }
        const menus = []
        for (const item of menuList) {
          // console.log(item)
          if (!currentUserMenuIds.includes(item.mId)) {
            continue
          }
          const menuInfo = menuMap[item.Link]
          if (!((item.Link === null || item.Link === '') && item.items.length > 0) && (menuInfo === null || menuInfo === undefined)) {
            continue
          }
          if (item.Link === '/system/menu' && item.items.length === 0) {
            hasMenuConfig = true
          }
          const menuItem = {
            path: item.Link,
            meta: {
              title: item.menuName,
              icon: menuInfo === undefined ? 'setting' : menuInfo.icon
            }
          }
          if (item.items.length > 0) {
            menuItem.children = []
            for (const childItem of item.items) {
              if (!currentUserMenuIds.includes(childItem.mid)) {
                continue
              }
              const childMenuInfo = menuMap[childItem.link]
              if (childMenuInfo === null || childMenuInfo === undefined) {
                continue
              }
              if (childItem.link === '/system/menu') {
                hasMenuConfig = true
              }
              menuItem.children.push(
                {
                  path: childItem.link,
                  meta: {
                    title: childItem.menuName,
                    icon: childMenuInfo.icon
                  }
                }
              )
            }
            if (menuItem.children.length === 0) {
              delete menuItem.children
            }
          }
          menus.push(menuItem)
        }
        // TODO：这里还需要判断用户是否是管理员
        if (!hasMenuConfig) {
          const menuConfigItem = menuMap['/system/menu']
          menus.push({
            path: '/system/menu',
            meta: {
              title: menuConfigItem.label,
              icon: menuConfigItem.icon
            }
          })
        }
        // console.log(menus)
        this.menus = menus
      }
    }
  },
  watch: {
    isMenuUpdated() {
      if (this.isMenuUpdated) {
        this.getMenus()
        this.$store.commit('menus/SET_IS_MENU_UPDATED', false)
      }
    }
  },
  computed: {
    ...mapGetters([
      'sidebar'
    ]),
    isMenuUpdated() {
      return this.$store.state.menus.isMenuUpdate
    },
    routes() {
      return this.$router.options.routes
    },
    activeMenu() {
      const route = this.$route
      const {meta, path} = route
      // if set path, the sidebar will highlight the path you set
      if (meta.activeMenu) {
        return meta.activeMenu
      }
      return path
    },
    showLogo() {
      return this.$store.state.settings.sidebarLogo
    },
    variables() {
      return variables
    },
    isCollapse() {
      return !this.sidebar.opened
    }
  }
}
</script>
