import request from '@/utils/request'

export function getClassList(pageNo, pageSize) {
  return request({
    url: '/course/AllCourse',
    method: 'get',
    params: {
      pageNo: pageNo,
      pageSize: pageSize
    }
  })
}

export function addClass(cName, ccName, term, date, universityId, collegeId) {
  return request({
    url: '/course/Course',
    method: 'post',
    data: {
      cName: `${cName}`,
      ccName: `${ccName}`,
      term: `${term}`,
      date: `${date}`,
      universityId: `${universityId}`,
      collegeId: `${collegeId}`
    }
  })
}

export function updateClass(cId, ccId, cName, ccName, term, date, universityId, collegeId) {
  return request({
    url: '/course/CourseDetail',
    method: 'put',
    data: {
      cId: `${cId}`,
      cName: `${cName}`,
      ccName: `${ccName}`,
      term: `${term}`,
      date: `${date}`,
      universityId: `${universityId}`,
      collegeId: `${collegeId}`,
      ccId: `${ccId}`
    }
  })
}

export function queryAllCollege() {
  return request({
    url: '/school/AllCollege',
    method: 'get'
  })
}
