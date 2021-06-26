import request from '@/utils/request'

export function getRoleMenu() {
  return request({
    url: '/role-menu/AllRoleMenu',
    method: 'get'
  })
}

export function updateRoleMenu(rmId, status) {
  return request({
    url: '/role-menu/RoleMenu',
    method: 'put',
    data: {
      rmId: `${rmId}`,
      status: `${status}`
    }
  })
}
