<template>
  <v-app>
    <v-app-bar
      color="blue darken-3"
      dark
      app
      :clipped-left="$vuetify.breakpoint.lgAndUp"
    >
      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title v-if="this.GLOBAL.user == 'teacher'"
        >到云 - {{ this.$route.meta.title }}</v-toolbar-title
      >
      <v-toolbar-title v-if="this.GLOBAL.user == 'student'"
        >到云 - {{ this.$route.meta.stuTitle }}</v-toolbar-title
      >
      <v-spacer />
      <v-btn icon @click="toggleDarkMode">
        <v-icon> {{ darkModeIcon }}</v-icon>
      </v-btn>
    </v-app-bar>

    <v-navigation-drawer
      v-model="drawer"
      app
      :clipped="$vuetify.breakpoint.lgAndUp"
    >
      <v-list nav dense>
        <v-list-item-group v-model="itemIndex" color="primary">
          <v-list-item v-for="(item, i) in items" :key="i" :to="item.to">
            <v-list-item-icon>
              <v-icon v-text="item.icon"></v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title v-text="item.text"></v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>
    <v-main>
      <RouterView />
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: "App",
  components: {},
  mounted() {
    if (document.body.clientWidth > 1264) {
      this.drawer = true;
    }
  },
  data: () => ({
    drawer: false,
    darkModeIcon: "mdi-brightness-4",
    itemIndex: 0,
    items: [
      {
        icon: "mdi-home",
        text: "主页",
        to: "/",
      },
      {
        icon: "mdi-account",
        text: "登录",
        to: "/login",
      },
      {
        icon: "mdi-google-classroom",
        text: "班课列表",
        to: "/class",
      },
      {
        icon: "mdi-information",
        text: "关于",
        to: "/about",
      },
    ],
  }),
  methods: {
    toggleDarkMode: function () {
      this.$vuetify.theme.dark = !this.$vuetify.theme.dark;
      this.darkModeIcon = this.$vuetify.theme.dark
        ? "mdi-brightness-7"
        : "mdi-brightness-4";
    },
  },
};
</script>
