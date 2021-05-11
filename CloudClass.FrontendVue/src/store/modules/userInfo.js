const state = {
  isTeacher: localStorage.getItem("isTeacher"),
};
const mutations = {
  SET_TEA_TYPE(state, isTeacher) {
    state.isTeacher = isTeacher;
    localStorage.setItem("isTeacher", isTeacher);
  },
};
const actions = {};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
