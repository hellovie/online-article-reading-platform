import axios from 'axios'
import serverConfig from './config'
import router from '@/router'
import { Toast } from '@/main.js'

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
      Toast.info(res.data.message)
    }
    return res.data.data
  },
  (error) => {
    if (error.response.status === 401) {
      Toast.error(error.response.data.message)
      router.push('/login')
    } else if (error.response.status >= 500) {
      Toast.error(error.response.data.message)
    } else {
      Toast.warning(error.response.data.message)
      return error.response.data.message !== '' ? Promise.reject(new Error(error.response.data.message)) : Promise.reject(error)
    }
  }
)

export default serviceAxios
