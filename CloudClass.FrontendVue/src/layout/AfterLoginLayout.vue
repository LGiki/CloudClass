<template>
  <v-app>
    <v-app-bar
      color="blue darken-3"
      dark
      app
      :clipped-left="$vuetify.breakpoint.lgAndUp"
    >
<!--      <v-app-bar-nav-icon @click="$router.back()">-->
<!--        <v-icon>mdi-chevron-left</v-icon>-->
<!--      </v-app-bar-nav-icon>-->
<!--      <div style="transform: translateX(-3px)">-->
      <div>
        <v-toolbar-title v-if="isTeacher === '1'">
          到云 - {{ this.$route.meta.title }}
        </v-toolbar-title>
        <v-toolbar-title v-if="isTeacher === '0'"
          >到云 - {{ this.$route.meta.stuTitle }}</v-toolbar-title
        >
      </div>
      <v-spacer />
      <v-btn icon @click="toggleDarkMode">
        <v-icon> {{ darkModeIcon }}</v-icon>
      </v-btn>
    </v-app-bar>
<!--    <v-navigation-drawer-->
<!--      v-model="drawer"-->
<!--      app-->
<!--      :clipped="$vuetify.breakpoint.lgAndUp"-->
<!--    >-->
<!--      <v-list nav dense>-->
<!--        <v-list-item-group v-model="itemIndex" color="primary">-->
<!--          <v-list-item v-for="(item, i) in items" :key="i" :to="item.to">-->
<!--            <v-list-item-icon>-->
<!--              <v-icon v-text="item.icon"></v-icon>-->
<!--            </v-list-item-icon>-->
<!--            <v-list-item-content>-->
<!--              <v-list-item-title v-text="item.text"></v-list-item-title>-->
<!--            </v-list-item-content>-->
<!--          </v-list-item>-->
<!--          <v-list-item @click="logout">-->
<!--            <v-list-item-icon>-->
<!--              <v-icon>mdi-arrow-left-bold</v-icon>-->
<!--            </v-list-item-icon>-->
<!--            <v-list-item-content>-->
<!--              <v-list-item-title>退出</v-list-item-title>-->
<!--            </v-list-item-content>-->
<!--          </v-list-item>-->
<!--        </v-list-item-group>-->
<!--      </v-list>-->
<!--    </v-navigation-drawer>-->
    <v-main>
      <RouterView style="margin-bottom: 56px"/>
    </v-main>
    <v-bottom-navigation
      :value="value"
      color="primary"
      grow
      style="position: fixed; bottom: 0"
    >
      <v-btn
        v-for="item of items"
        :key="item.to"
        @click="$router.push(item.to)"
      >
        <span>{{ item.text }}</span>
        <v-icon>{{ item.icon }}</v-icon>
      </v-btn>
    </v-bottom-navigation>
  </v-app>
</template>

<script>
export default {
  name: "App",
  components: {},
  mounted() {
    // if (document.body.clientWidth > 1264) {
    //   this.drawer = true;
    // }
    console.log(this.$router.history)
  },
  data: () => ({
    value: 0,
    // drawer: false,
    darkModeIcon: "mdi-brightness-4",
    itemIndex: 0,
    isTeacher: localStorage.getItem("isTeacher"),
    items: [
      {
        icon: "mdi-google-classroom",
        text: "班课",
        to: "/class",
      },
      {
        icon: "mdi-account",
        text: "个人信息",
        to: "/personal",
      },
    ],
    // items: [
    //   {
    //     icon: "mdi-google-classroom",
    //     text: "我创建的",
    //     to: "/class",
    //   },
    //   {
    //     icon: "mdi-google-classroom",
    //     text: "我加入的",
    //     to: "/class",
    //   },
    //   {
    //     icon: "mdi-account",
    //     text: "个人信息",
    //     to: "/personal",
    //   },
    //   {
    //     icon: "mdi-information",
    //     text: "关于",
    //     to: "/about",
    //   },
    // ],
  }),
  methods: {
    toggleDarkMode: function () {
      this.$vuetify.theme.dark = !this.$vuetify.theme.dark;
      this.darkModeIcon = this.$vuetify.theme.dark
        ? "mdi-brightness-7"
        : "mdi-brightness-4";
    },
    logout() {
      this.$router.replace({ path: "/login" });
      this.$store.commit("token/REMOVE_TOKEN");
    },
  },
};
</script>
