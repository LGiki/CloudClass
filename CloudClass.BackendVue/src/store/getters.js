const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  isAuthenticated: state => !!state.token.token,
  isMenuUpdated: state => state.menus.isMenuUpdate,
  role: state => state.token.role
}
export default getters
