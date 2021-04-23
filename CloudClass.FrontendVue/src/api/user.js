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
export function loginByTelephone(phone, password) {
  return request({
    url: "/authentication/phone",
    method: "post",
    data: {
      phone: phone,
      password: password,
    },
  });
}
export function loginByMessage(phone, verifyCode) {
  return request({
    url: "authentication/verifyCode",
    method: "post",
    data: {
      phone: phone,
      verifycode: verifyCode,
    },
  });
}

export function registerByName(
  phone,
  username,
  password,
  verifyCode,
  is_teacher
) {
  return request({
    url: "/person",
    method: "post",
    data: {
      phone: phone,
      username: username,
      password: password,
      verifycode: verifyCode,
      is_teacher: is_teacher,
    },
  });
}

export function registerByPhone(phone, verifycode, is_teacher) {
  return request({
    url: "/fast",
    method: "post",
    data: {
      phone: phone,
      verifycode: verifycode,
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
