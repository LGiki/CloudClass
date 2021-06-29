const state = {
  isMenuUpdate: false
}

const mutations = {
  SET_IS_MENU_UPDATED(state, isMenuUpdate) {
    state.isMenuUpdate = isMenuUpdate
  }
}

export default {
  namespaced: true,
  state,
  mutations
}

