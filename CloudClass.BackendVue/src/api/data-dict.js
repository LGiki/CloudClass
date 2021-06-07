import request from '@/utils/request'

export function getDataDictList(pageNo, pageSize) {
  return request({
    url: '/dictionary/allDictionary',
    method: 'get',
    params: {
      pageNo: pageNo,
      pageSize: pageSize
    }
  })
}

export function addDataDict(chineseName, englishName) {
  return request({
    url: '/dictionary/Dictionary',
    method: 'post',
    data: {
      chineseName: chineseName,
      englishName: englishName
    }
  })
}

export function updateDataDict(dId, chineseName, englishName) {
  return request({
    url: '/dictionary/Dictionary',
    method: 'put',
    data: {
      dId: `${dId}`,
      chineseName: chineseName,
      englishName: englishName
    }
  })
}

export function deleteDataDict(dId) {
  return request({
    url: `/dictionary/Dictionary`,
    method: 'delete',
    params: {
      dId: dId
    }
  })
}

export function getDataDictItemList(dId, pageNo, pageSize) {
  return request({
    url: `/dictionary-detail/DictionaryDetail`,
    method: 'get',
    params: {
      dId: `${dId}`,
      pageNo: `${pageNo}`,
      pageSize: `${pageSize}`
    }
  })
}

export function addDataDictItem(dId, ddName) {
  return request({
    url: `/dictionary-detail/DictionaryDetail`,
    method: 'post',
    data: {
      dId: `${dId}`,
      ddName: `${ddName}`
    }
  })
}

export function updateDataDictItem(ddId, ddName) {
  return request({
    url: `/dictionary-detail/DictionaryDetail`,
    method: 'put',
    data: {
      ddId: `${ddId}`,
      ddName: `${ddName}`
    }
  })
}

export function deleteDataDictItem(ddId) {
  return request({
    url: `/dictionary-detail/DictionaryDetail`,
    method: 'delete',
    params: {
      ddId: ddId
    }
  })
}
