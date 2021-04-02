const state = {
  token: "",
};

const mutations = {
  SET_TOKEN(state, token) {
    state.token = token;
    localStorage.setItem("token", token);
  },
  REMOVE_TOKEN(state) {
    state.token = "";
    localStorage.removeItem("token");
  },
};

const actions = {
  setToken({ commit }) {
    commit("SET_TOKEN");
  },
  removeToken({ commit }) {
    commit("REMOVE_TOKEN");
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
