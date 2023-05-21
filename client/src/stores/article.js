import { defineStore } from 'pinia'

export const useArticleStore = defineStore('article', () => {
  function getCreationType (type) {
    let result = ''
    if (type === 'ORIGINAL') {
      result = '原创'
    }
    if (type === 'REPRODUCE') {
      result = '转载'
    }
    if (type === 'TRANSLATE') {
      result = '翻译'
    }
    return result
  }

  function getStatus (status) {
    let result = ''
    if (status === 'DRAFT') {
      result = '草稿'
    }
    if (status === 'OPENNESS') {
      result = '公开'
    }
    if (status === 'PRIVACY') {
      result = '私密'
    }
    return result
  }
  return { getCreationType, getStatus }
})
