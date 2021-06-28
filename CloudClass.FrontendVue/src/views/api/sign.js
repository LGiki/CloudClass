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
//获取签到剩余时间
export function getSignUpRemainTime(siId) {
  return request({
    url: "/sign-in/RemainTime",
    method: "get",
    params: { siId: siId },
  });
}
//获取签到成功的学生
export function getSignedStudents(siId) {
  return request({
    url: "/sign-in/SignInStudent",
    method: "get",
    params: { siId: siId },
  });
}
//获取未签到成功的学生
export function getUnSignedStudents(siId) {
  return request({
    url: "/sign-in/NotSignInStudent",
    method: "get",
    params: { siId: siId },
  });
}
//补签   siId,签到号   peId 教师号
export function reSignUp(siId, peId) {
  return request({
    url: "/sign-record/SupplementSignRecord",
    method: "post",
    data: {
      siId: `${siId}`,
      peId: `${peId}`,
    },
  });
}
//获取某班课的详细签到记录
export function getDetailSignUpRecord(cId) {
  return request({
    url: "sign-record/StudentSignRecord",
    method: "get",
    params: { cId: cId },
  });
}
//手动结束签到
export function exitClass(cId) {
  return request({
    url: "person-course/personCourse",
    method: "put",
    data: { cId: cId },
  });
}
//获取班课内学生个人信息
export function getStudentInfo(peId) {
  return request({
    url: "person-course/StudentInfo",
    method: "get",
    params: { peId : peId },
  });
}


