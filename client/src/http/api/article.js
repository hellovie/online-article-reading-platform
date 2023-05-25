import serviceAxios from '../index'

export const searchPagesApi = (pageIndex, pageSize, search, sort) => {
  let parms = ''
  if (search && !sort) {
    parms = `?search=${search}`
  } else if (sort && !search) {
    parms = `?sort=${sort}`
  } else if (search && sort) {
    parms = `?search=${search}&sort=${sort}`
  }
  return serviceAxios({
    method: 'GET',
    url: `/articles/views/${pageIndex}/${pageSize}${parms}`
  })
}

export const getOneByIdApi = (id) => {
  return serviceAxios({
    method: 'GET',
    url: `/articles/views/${id}`
  })
}

export const createApi = (parms) => {
  return serviceAxios({
    method: 'POST',
    url: '/articles',
    data: parms
  })
}

export const publishArticleApi = (id) => {
  return serviceAxios({
    method: 'PUT',
    url: `/articles/publish/${id}`
  })
}
