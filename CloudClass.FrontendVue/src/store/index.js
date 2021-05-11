import Vue from "vue";
import Vuex from "vuex";
import token from "./modules/token";
import userInfo from "./modules/userInfo";
Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    userInfo,
    token,
  },
});
