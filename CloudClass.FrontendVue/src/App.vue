<template>
  <v-app>
      <RouterView />
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
