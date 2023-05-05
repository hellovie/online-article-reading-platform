import serviceAxios from '../index'

export const loginApi = (params) => {
  return serviceAxios({
    method: 'POST',
    url: 'users/login',
    data: params
  })
}

export const registerApi = (params) => {
  return serviceAxios({
    method: 'POST',
    url: 'users/register',
    data: params
  })
}

export const getUserAccountInfoApi = () => {
  return serviceAxios({
    method: 'GET',
    url: 'users'
  })
}
