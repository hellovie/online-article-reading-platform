<script setup>
import router from '@/router/index.js'
import { useUserAccountStore } from '@/stores/user'
import $Toast from '@/main.js'

const userAccountStore = useUserAccountStore()
const { isLogin } = userAccountStore

const nickname = isLogin()

const login = () => {
  router.push('/login')
}
const gotoHome = () => {
  router.push('/home')
}
const search = () => {
  $Toast.open({
    message: '搜索文章功能紧急开发中...',
    type: 'info',
    position: 'top-right',
    duration: 3000
  })
}
const userInfo = () => {
  $Toast.open({
    message: '欢迎你，' + nickname + '！用户信息功能紧急开发中...',
    type: 'info',
    position: 'top-right',
    duration: 3000
  })
}
</script>

<template>
  <div class="header-container">
    <div class="header-title">
      <img class="article-icon" src="../assets/icon/article.svg" @click="gotoHome" />
      <div class="web-title">在线文章阅读平台</div>
    </div>
    <div tabindex="1" class="header-search">
      <input placeholder="请输入关键字" />
      <img class="search-icon" src="../assets/icon/search.svg" @click="search" />
    </div>
    <div v-if="nickname !== ''" class="header-user" @click="userInfo">
      <img class="user-icon" src="../assets/icon/user.svg" />
      <div class="user-nickname singe-line">{{ nickname }}</div>
    </div>
    <div v-else class="header-login" @click="login">
      <div class="login-msg">登录/注册</div>
      <img class="login-icon" src="../assets/icon/login.svg" />
    </div>
  </div>
</template>

<style scoped>
.header-container {
  width: 100%;
  height: 50px;
  background: var(--header-bg-color);
  display: flex;
  align-items: center;
  color: var(--main-font-color);
  position: fixed;
  left: 0;
  top: 0;
  right: 0;
  z-index: 999;
  box-shadow: 0px 1px 7px var(--main-shadow-color);
}
.header-container > .header-title {
  margin: 0 10px;
  width: 250px;
  height: 50px;
  box-sizing: border-box;
  overflow: hidden;
  display: flex;
  align-items: center;
}
.header-container > .header-title > .article-icon {
  width: 40px;
  height: 40px;
  margin-left: 5px;
  cursor: pointer;
}
.header-container > .header-title > .web-title {
  width: 200px;
  height: 50px;
  line-height: 50px;
  text-align: center;
  font-size: x-large;
  cursor: default;
}
.header-container > .header-search {
  width: 250px;
  height: 35px;
  box-sizing: border-box;
  border-radius: 5px;
  background: var(--main-bg-color);
  display: flex;
  align-items: center;
  padding: 5px 10px;
  overflow: hidden;
}
.header-container > .header-search > input {
  border: none;
  outline: none;
  width: 100%;
  height: 100%;
  background: transparent;
  border-bottom: 2px transparent solid;
  box-sizing: border-box;
  transition: border 0.3s;
}
.header-container > .header-search > input:focus {
  border-bottom: 2px var(--btn-hover-border-color) solid;
}
.header-container > .header-search > .search-icon {
  width: 30px;
  height: 30px;
  margin-left: 5px;
  cursor: pointer;
}
.header-container > .header-login {
  width: 110px;
  height: 50px;
  line-height: 50px;
  text-align: center;
  justify-content: flex-end;
  margin-left: auto;
  margin-right: 30px;
  box-sizing: border-box;
  display: flex;
  align-items: center;
  border-bottom: 3px transparent solid;
  cursor: pointer;
  transition: border 0.3s;
}
.header-container > .header-login:hover {
  border-bottom: 3px var(--btn-hover-border-color) solid;
}
.header-container > .header-login > .login-icon {
  width: 30px;
  height: 30px;
}
.header-container > .header-login > .login-msg {
  width: 80px;
  height: 50px;
  line-height: 50px;
  text-align: center;
  box-sizing: border-box;
}

.header-container > .header-user {
  width: 150px;
  height: 50px;
  line-height: 50px;
  text-align: center;
  justify-content: flex-end;
  margin-left: auto;
  margin-right: 30px;
  box-sizing: border-box;
  display: flex;
  align-items: center;
  border-bottom: 3px transparent solid;
  cursor: pointer;
  transition: border 0.3s;
}
.header-container > .header-user:hover {
  border-bottom: 3px var(--btn-hover-border-color) solid;
}
.header-container > .header-user > .user-icon {
  width: 30px;
  height: 30px;
}
.header-container > .header-user > .user-nickname {
  width: 120px;
  height: 50px;
  line-height: 50px;
  text-align: center;
  box-sizing: border-box;
}
</style>
