import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";
import axios from "axios";
import VueAxios from "vue-axios";
import global_variable from "@/api/global_variable";

Vue.use(VueAxios, axios);
Vue.config.productionTip = false;
Vue.prototype.GLOBAL = global_variable;

new Vue({
  router,
  store,
  vuetify,
  render: (h) => h(App),
}).$mount("#app");
