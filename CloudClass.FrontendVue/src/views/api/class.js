import request from "../utils/request";

export function addClass(
  //  peId,
  cName,
  ccName,
  term,
  date,
  universityId,
  collegeId
) {
  return request({
    url: "/course/Course",
    method: "post",

    data: {
      //      peId: peId,
      cName: cName,
      ccName: ccName,
      term: term,
      date: date,
      universityId: universityId,
      collegeId: collegeId,
    },
  });
}
//初始化班级列表
export function initClassList(pageNo, pageSize) {
  return request({
    url: "/course/Course",
    method: "get",
    params: {
      pageNo: pageNo,
      pageSize: pageSize,
      //   peid: peid,
    },
  });
}
//查询所有可选学校
export function queryAllCollege() {
  return request({
    url: "/school/AllCollege",
    method: "get",
  });
}
//取得班课信息  返回cId以及classNumber之类
export function getClassData(classCode) {
  return request({
    url: "/course/CourseDetail",
    method: "get",
    params: { cNumber: classCode },
  });
}
//能否加入班课
export function enterClass(classCode) {
  return request({
    url: "/person-course/personCourse",
    method: "post",
    data: { cNumber: classCode },
  });
}
//更新是否允许加入
export function updateClassPermitAdd(cId, canAttend) {
  return request({
    url: "/course/CourseCanAttend",
    method: "put",
    data: { cId: cId, canAttend: canAttend },
  });
}
//更新班课状态是否结束
export function updateClassFinished(cId, IsEnd) {
  return request({
    url: "/course/CourseIsEnd",
    method: "put",
    data: { cId: cId, IsEnd: IsEnd },
  });
}

//更新班课状态是否结束
export function queryClassFinished(cNumber) {
  return request({
    url: "/course/CourseIsEnd",
    method: "get",
    params: { cNumber: cNumber },
  });
}
//更新是否允许加入
export function queryClassPermitAdd(cNumber) {
  return request({
    url: "/course/CourseCanAttend",
    method: "get",
    params: { cNumber: cNumber },
  });
}
//获取班课成员
export function queryClassMembers(cId) {
  return request({
    url: "/person-course/CourseMember",
    method: "get",
    params: { cId: cId },
  });
}
