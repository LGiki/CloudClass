import request from '@/utils/request'

export function loginByPassword(username, password) {
  return request({
    url: '/authentication/phone',
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

export function getUserList(pageNo, pageSize) {
  return request({
    url: '/allPerson',
    method: 'get',
    params: {
      pageNo: pageNo,
      pageSize: pageSize
    }
  })
}

export function addUser(peName, isTeacher, username, phone) {
  return request({
    url: '/PersonMan',
    method: 'post',
    data: {
      peName: peName,
      isTeacher: `${isTeacher}`,
      username: username,
      phone: phone
    }
  })
}

export function updateUser(peId, peName, isTeacher, username, phone, password) {
  return request({
    url: '/PersonMan',
    method: 'put',
    data: {
      peId: `${peId}`,
      peName: peName,
      isTeacher: `${isTeacher}`,
      username: username,
      phone: phone,
      password: password
    }
  })
}
