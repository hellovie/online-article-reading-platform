import { defineStore } from 'pinia'
import { reactive, ref } from 'vue'
import router from '@/router/index.js'
import $Toast from '@/main.js'

export const useUserStore = defineStore('user', () => {
  const account = reactive({
    id: '',
    username: '',
    nickname: '',
    lastLoginIp: '',
    lastLoginTime: ''
  })

  const token = ref('')

  function login (loginVO) {
    account.id = loginVO.id
    account.username = loginVO.username
    account.nickname = loginVO.nickname
    account.lastLoginIp = loginVO.lastLoginIp
    account.lastLoginTime = loginVO.lastLoginTime

    token.value = loginVO.token

    window.sessionStorage.setItem('token', token.value)
    router.push('/home')
  }

  function exit () {
    account.value = ''
    token.value = ''
    window.sessionStorage.removeItem('token')
    $Toast.open({
      message: '账号已成功退出!',
      type: 'success',
      position: 'top-right',
      duration: 3000
    })
  }

  return { login, exit }
})
