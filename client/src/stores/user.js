import { defineStore } from 'pinia'
import { reactive, ref } from 'vue'
import router from '@/router'
import { Toast } from '@/main.js'
import { getUserAccountInfoApi } from '@/http/api/user'

export const useUserStore = defineStore('user', () => {
  const user = reactive({
    account: {
      id: '',
      avatar: '',
      username: '',
      nickname: '',
      lastLoginIp: '',
      lastLoginTime: ''
    }
  })

  const token = ref('')

  function login (loginVO) {
    user.account = loginVO
    token.value = loginVO.token

    window.sessionStorage.setItem('token', token.value)
    router.push('/')
  }

  function exit () {
    user.account = null
    token.value = ''
    window.sessionStorage.removeItem('token')
    Toast.success('账号已成功退出!')
  }

  /**
   * 登录: 用户数据
   * 未登录: null
   */
  async function isLogin () {
    // 数据存在, 直接返回.
    if (user.account && user.account.nickname !== '' && user.account.username !== '') {
      return user.account
    }
    // 数据不存在, 但 token 存在, 向服务器请求数据.
    const token = window.sessionStorage.getItem('token')
    if (token && token !== '') {
      try {
        user.account = await getUserAccountInfoApi()
        // token 有效, 存入用户数据.
        return user.account
      } catch (error) {
        window.sessionStorage.removeItem('token')
        return null
      }
    } else {
      // 数据不存在, token 也不存在, 返回 null.
      return null
    }
  }

  return { login, exit, isLogin }
})
