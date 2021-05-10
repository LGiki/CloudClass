const state = {
  token: localStorage.getItem("token"),
  isTeacher: localStorage.getItem("isTeacher"),
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
  SET_TEA_TYPE(state, isTeacher) {
    state.isTeacher = isTeacher;
    localStorage.setItem("isTeacher", isTeacher);
  },
};

const actions = {
  setToken({ commit }) {
    commit("SET_TOKEN");
  },
  removeToken({ commit }) {
    commit("REMOVE_TOKEN");
  },
  setTeacherType({ commit }) {
    commit("SET_TEA_TYPE");
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
