import { defineStore } from 'pinia'
import { reactive, ref } from 'vue'
import router from '@/router/index.js'

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
  }

  return { login, exit }
})
