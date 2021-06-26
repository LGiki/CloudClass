const state = {
  token: localStorage.getItem('token') || '',
  role: localStorage.getItem('role') || ''
}

const mutations = {
  SET_TOKEN(state, { token, role }) {
    state.token = token
    state.role = role
    localStorage.setItem('token', token)
    localStorage.setItem('role', role)
  },
  REMOVE_TOKEN(state) {
    state.token = ''
    state.role = ''
    localStorage.removeItem('token')
    localStorage.removeItem('role')
  }
}

const actions = {
  setToken({ commit }) {
    commit('SET_TOKEN')
  },
  removeToken({ commit }) {
    commit('REMOVE_TOKEN')
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

