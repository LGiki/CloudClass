import request from '@/utils/request'

export function getRoleMenu() {
  return request({
    url: '/role-menu/AllRoleMenu',
    method: 'get'
  })
}
