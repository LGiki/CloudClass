import request from '@/utils/request'

export function getRoleMenu(role) {
  return request({
    url: `/role-menu/AllRoleMenu?rId=${role}`,
    method: 'get'
  })
}

export function updateRoleMenu(rId, mId, status) {
  return request({
    url: '/role-menu/RoleMenu',
    method: 'put',
    data: {
      rId: `${rId}`,
      mId: `${mId}`,
      status: `${status}`
    }
  })
}
