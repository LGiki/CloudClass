import request from "../utils/request";

//发起签到
export function startSignUp(type, cId, lng, lat, time) {
  return request({
    url: "/sign-in/SignIn",
    method: "post",
    data: {
      type: type,
      cId: cId,
      lng: lng,
      lat: lat,
      time: time,
    },
  });
}
//学生签到
export function signUpBySid(si_id, lat, lng) {
  return request({
    url: "/sign-record/SignRecord",
    method: "post",
    data: {
      si_id: si_id,
      lat: lat,
      lng: lng,
    },
  });
}
//获取已结束的签到
export function getFinishedSignUp(cId) {
  return request({
    url: "/sign-in/NotActiveSignIn",
    method: "get",
    params: { cId: cId },
  });
}
//获取正在进行中的签到
export function getActiveSignUp(cId) {
  return request({
    url: "/sign-in/ActiveSignIn",
    method: "get",
    params: { cId: cId },
  });
}
//手动结束签到
export function endSignUp(siId) {
  return request({
    url: "/sign-in/SignInCode",
    method: "put",
    data: { siId: siId },
  });
}
