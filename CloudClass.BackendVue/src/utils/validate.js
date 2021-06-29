/**
 * Created by PanJiaChen on 16/11/18.
 */

/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

export function validatePhone(rule, value, callback) {
  if (value === null || value.length === 0) {
    callback(new Error('手机号不能为空'))
  } else if (!/^1[3456789]\d{9}$/.test(value)) {
    callback(new Error('手机号格式不正确'))
  } else {
    callback()
  }
}
