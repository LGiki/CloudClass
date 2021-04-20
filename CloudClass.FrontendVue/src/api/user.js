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

export function register(phone, username, password, is_teacher) {
  return request({
    url: "/person",
    method: "post",
    data: {
      phone: phone,
      username: username,
      password: password,
      is_teacher: is_teacher,
    },
  });
}

export function sendSMS(phone) {
  return request({
    url: "/Sms/send",
    method: "post",
    data: {
      phone: phone,
    },
  });
}
