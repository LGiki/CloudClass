import request from "../utils/request";

export function addClass(peId, cName, description, term, date) {
  return request({
    url: "/course/Course",
    method: "post",
    data: {
      peId: peId,
      cName: cName,
      description: description,
      term: term,
      date: date,
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
