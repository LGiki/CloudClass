import request from '@/utils/request'

export function loginByPassword(username, password) {
  return request({
    url: '/user/phone',
    method: 'post',
    data: {
      phone: username,
      password: password
    }
  })
}

export function sendSMS(phoneNumber) {
  return request({
    url: '/Sms/send',
    method: 'post',
    data: {
      phone: phoneNumber
    }
  })
}

export function loginBySMS(username, verifyCode) {
  return request({
    url: '/user/verifyCode',
    method: 'post',
    data: {
      phone: username,
      verifyCode: verifyCode
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
