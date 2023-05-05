import axios from 'axios'
import serverConfig from './config'
import router from '@/router/index.js'
import $Toast from '@/main.js'

const serviceAxios = axios.create({
  baseURL: serverConfig.baseURL,
  timeout: 10000
})

serviceAxios.interceptors.request.use(
  (config) => {
    // console.log('请求配置', config)
    const token = window.sessionStorage.getItem('token')
    if (token != null && token !== '') {
      config.headers.Authorization = 'Bearer ' + token
    }
    return config
  },
  (error) => {
    Promise.reject(error)
  }
)

serviceAxios.interceptors.response.use(
  (res) => {
    // console.log('响应结果' + res)
    // 调用失败但 HTTP status 为 200.
    if (res.data.code !== 10000) {
      $Toast.open({
        message: res.data.message,
        type: 'info',
        position: 'top-right',
        duration: 3000
      })
    }
    return res.data.data
  },
  (error) => {
    if (error.response.status === 401) {
      $Toast.open({
        message: error.response.data.message,
        type: 'error',
        position: 'top-right',
        duration: 3000
      })
      router.push('/login')
    } else if (error.response.status >= 500) {
      $Toast.open({
        message: error.response.data.message,
        type: 'error',
        position: 'top-right',
        duration: 3000
      })
    } else {
      $Toast.open({
        message: error.response.data.message,
        type: 'warning',
        position: 'top-right',
        duration: 3000
      })
      return error.response.data.message !== '' ? Promise.reject(new Error(error.response.data.message)) : Promise.reject(error)
    }
  }
)

export default serviceAxios
