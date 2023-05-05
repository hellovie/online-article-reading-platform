import { defineStore } from 'pinia'

export const useUserAccountStore = defineStore('userAccountStore', {
  state: () => {
    return { username: '', nickname: '', token: '' }
  },

  actions: {
    login (user) {
      this.username = user.username
      this.nickname = user.nickname
      this.token = user.token
      window.sessionStorage.setItem('token', this.token)
      window.sessionStorage.setItem('nickname', this.nickname)
      window.sessionStorage.setItem('username', this.username)
    },
    isLogin () {
      this.username = window.sessionStorage.getItem('username')
      this.nickname = window.sessionStorage.getItem('nickname')
      this.token = window.sessionStorage.getItem('token')
      if (
        this.token != null &&
        this.token !== '' &&
        this.nickname != null &&
        this.nickname !== '' &&
        this.username != null &&
        this.username !== ''
      ) {
        return this.nickname
      }
      return ''
    }
  }
})
