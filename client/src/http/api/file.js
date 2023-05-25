import serviceAxios from '../index'

export const uploadArticleCoverApi = (file) => {
  return serviceAxios({
    method: 'POST',
    url: '/uploads/cover',
    data: file
  })
}

export const uploadImageApi = (file) => {
  return serviceAxios({
    method: 'POST',
    url: '/uploads/image',
    data: file
  })
}
