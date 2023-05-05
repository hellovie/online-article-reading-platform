import axios from 'axios'
import serverConfig from './config'
import router from '@/router/index.js'

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
      alert(res.data.message)
    }
    return res.data.data
  },
  (error) => {
    if (error.response.status === 401) {
      router.push('/login')
    } else {
      alert(error.response.data.message)
      // Promise.reject(error)
    }
  }
)

export default serviceAxios
