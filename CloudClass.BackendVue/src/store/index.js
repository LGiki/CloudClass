import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import app from './modules/app'
import settings from './modules/settings'
import token from './modules/token'
import menus from './modules/menus';

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    settings,
    token,
    menus
  },
  getters
})

export default store
