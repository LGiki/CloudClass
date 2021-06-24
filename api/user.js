import request from "../utils/request";

export function loginByUsername(username, password) {
  return request({
    url: "/authentication/",
    method: "post",
    data: {
      username: username,
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
      verifyCode: verifyCode,
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
      verifyCode: verifyCode,
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
      verifyCode: verifycode,
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
