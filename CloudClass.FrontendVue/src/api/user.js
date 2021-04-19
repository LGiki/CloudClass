import request from "../utils/request";

export function loginByUsername(username, password) {
  return request({
    url: "/authentication/username",
    method: "post",
    data: {
      username: username,
      password: password,
    },
  });
}

export function register(phone, username, password, isTeacher) {
  return request({
    url: "/authentication/person",
    method: "post",
    data: {
      phone: phone,
      username: username,
      password: password,
      isTeacher: isTeacher,
    },
  });
}

export function sendSMS(phone) {
  return request({
    url: "/Sms/send",
    method: "post",
    data: {
      phone: phone
    },
  })
}
