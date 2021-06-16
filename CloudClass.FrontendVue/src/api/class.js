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

export function initClassList(pageNo, pageSize, peid) {
  return request({
    url: "/course/Course",
    method: "get",
    data: {
      pageNo: pageNo,
      pageSize: pageSize,
      peid: peid,
    },
  });
}

export function queryAllCollege() {
  return request({
    url: "/school/AllCollege",
    method: "get",
  });
}

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

export function updateClassPermitAdd(cId, canAttend) {
  return request({
    url: "/course/CourseCanAttend",
    method: "put",
    data: { cId: cId, canAttend: canAttend },
  });
}

export function updateClassFinished(cId, IsEnd) {
  return request({
    url: "/course/CourseIsEnd",
    method: "put",
    data: { cId: cId, IsEnd: IsEnd },
  });
}
