import request from '@/utils/request'

// export function login(data) {
//   return request({
//     url: '/vue-admin-template/user/login',
//     method: 'post',
//     data
//   })
// }

export function login(username, password) {
  return request({
    url: '/vue-admin-template/user/login',
    method: 'post',
    data: {
      username: username,
      password: password
    }
  })
}

export function getInfo() {
  return request({
    url: '/vue-admin-template/user/info',
    method: 'get'
  })
}

export function logout(token) {
  return request({
    url: '/vue-admin-template/user/logout',
    method: 'post',
    data: {
      token: token
    }
  })
}
