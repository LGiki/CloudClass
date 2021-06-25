<template>
  <div :class="{'has-logo':showLogo}">
    <logo v-if="showLogo" :collapse="isCollapse"/>
    <el-scrollbar wrap-class="scrollbar-wrapper">
      <el-menu
        :default-active="activeMenu"
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
          :key="route.meta.label"
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
      // console.log(this.$router.options.routes)
      const menuResponse = await getAllMenuWithoutPagination()
      const roleMenuResponse = await getRoleMenu()
      const menuList = menuResponse.data.data
      const roleMenu = roleMenuResponse.data.data
      const currentUserRid = 2
      if (menuList !== null && roleMenu !== null) {
        const currentUserMenuIds = []
        for (const item of roleMenu) {
          if (item.rid === currentUserRid && item.status === 1) {
            currentUserMenuIds.push(item.mid)
          }
        }
        const menus = []
        for (const item of menuList) {
          const menuInfo = menuMap[item.Link]
          if (menuInfo === null || menuInfo === undefined) {
            continue
          }
          const menuItem = {
            path: item.Link,
            meta: {
              // title: menuInfo.label,
              title: item.menuName,
              icon: menuInfo.icon
            }
          }
          if (item.items.length > 0) {
            menuItem.children = []
            for (const childItem of item.items) {
              const childMenuInfo = menuMap[childItem.link]
              if (childMenuInfo === null || childMenuInfo === undefined) {
                continue
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
