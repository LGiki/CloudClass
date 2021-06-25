import request from '@/utils/request'

export function getAllMenu(pageNo, pageSize) {
  return request({
    url: '/menu/allMenu',
    method: 'get',
    params: {
      pageNo: pageNo,
      pageSize: pageSize
    }
  })
}

export function getAllMenuWithoutPagination() {
  return request({
    url: '/menu/Menu',
    method: 'get'
  })
}

export function getAllParentMenu() {
  return request({
    url: '/menu/parentMenu',
    method: 'get'
  })
}

export function updateMenu(mId, menuName, sort, isDisplay, isPage, link) {
  console.log(link)
  return request({
    url: '/menu/Menu',
    method: 'put',
    data: {
      mId: `${mId}`,
      menuName: menuName,
      sort: `${sort}`,
      isDisplay: isDisplay,
      isPage: isPage,
      Link: link
    }
  })
}

export function addMenu(menuName, sort, isDisplay, isPage, parentId, link) {
  return request({
    url: '/menu/Menu',
    method: 'post',
    data: {
      menuName: menuName,
      sort: `${sort}`,
      isDisplay: isDisplay,
      isPage: isPage,
      parentId: `${parentId}`,
      Link: link
    }
  })
}

export function deleteMenu(mId) {
  return request({
    url: '/menu/Menu',
    method: 'delete',
    data: {
      mId: `${mId}`
    }
  })
}

