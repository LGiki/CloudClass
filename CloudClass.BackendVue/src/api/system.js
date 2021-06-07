import request from '@/utils/request'

export function getSystemParameter(pageNo, pageSize) {
  return request({
    url: '/parameter/allParameter',
    method: 'get',
    params: {
      pageNo: pageNo,
      pageSize: pageSize
    }
  })
}

export function updateSystemParameter(paId, paName, description, value, status) {
  return request({
    url: '/parameter/Parameter',
    method: 'put',
    data: {
      paId: `${paId}`,
      paName: `${paName}`,
      description: `${description}`,
      value: `${value}`,
      status: `${status}`
    }
  })
}

export function addSystemParameter(paName, description, value, status) {
  return request({
    url: '/parameter/Parameter',
    method: 'post',
    data: {
      paName: `${paName}`,
      description: `${description}`,
      value: `${value}`,
      status: `${status}`
    }
  })
}

export function deleteSystemParameter(paId) {
  return request({
    url: '/parameter/Parameter',
    method: 'delete',
    params: {
      paId: paId
    }
  })
}
