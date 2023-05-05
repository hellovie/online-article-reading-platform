<script setup>
import router from '@/router/index.js'
import { ref, reactive } from 'vue'
import { loginApi, registerApi } from '@/http/api/user'
import { useUserAccountStore } from '@/stores/user'
import Loading from 'vue-loading-overlay'

const userAccountStore = useUserAccountStore()
const { login } = userAccountStore

const gotoHome = () => {
  router.push('/home')
}
const loginUser = reactive({
  username: '',
  password: ''
})
const registerUser = reactive({
  username: '',
  password: '',
  checkPassword: ''
})
const loginPageShow = ref(true)
const openLoginPage = () => {
  loginPageShow.value = true
  loginUser.username = ''
  loginUser.password = ''
}
const openRegisterPage = () => {
  loginPageShow.value = false
  registerUser.username = ''
  registerUser.password = ''
  registerUser.checkPassword = ''
}
const loginOpenEyes = ref(false)
const registerOpenEyes = ref(false)
const confirmOpenEyes = ref(false)
const clearLoginUsername = () => {
  const loginUsernameInput = document.getElementById('login-username')
  loginUsernameInput.value = ''
  loginUser.username = ''
}
const loginShowPassword = () => {
  loginOpenEyes.value = !loginOpenEyes.value
  const loginPasswordInput = document.getElementById('login-password')
  loginPasswordInput.type = loginOpenEyes.value ? 'text' : 'password'
}
const clearRegisterUsername = () => {
  const registerUsernameInput = document.getElementById('register-username')
  registerUsernameInput.value = ''
  registerUser.username = ''
}
const registerShowPassword = () => {
  registerOpenEyes.value = !registerOpenEyes.value
  const registerPasswordInput = document.getElementById('register-password')
  registerPasswordInput.type = registerOpenEyes.value ? 'text' : 'password'
}
const confirmShowPassword = () => {
  confirmOpenEyes.value = !confirmOpenEyes.value
  const confirmPasswordInput = document.getElementById('confirm-password')
  confirmPasswordInput.type = confirmOpenEyes.value ? 'text' : 'password'
}
const loading = ref(false)
const loginForm = () => {
  const loginSubmitBtn = document.getElementById('login-submit-btn')
  loginSubmitBtn.disabled = true
  loading.value = true
  loginApi(loginUser)
    .then((data) => {
      // console.log(data)
      const userInfo = {
        username: data.username,
        nickname: data.nickname,
        token: data.token
      }
      login(userInfo)
      router.push('/home')
    }).finally(() => {
      loginSubmitBtn.disabled = false
      loading.value = false
    })
}
const registerForm = () => {
  if (registerUser.password !== registerUser.checkPassword) {
    alert('两次密码不同！')
  } else {
    const registerSubmitBtn = document.getElementById('register-submit-btn')
    registerSubmitBtn.disabled = true
    loading.value = true
    registerApi(registerUser)
      .then((data) => {
        console.log(data)
        const userInfo = {
          username: data.username,
          nickname: data.nickname,
          token: data.token
        }
        login(userInfo)
        router.push('/home')
      }).finally(() => {
        registerSubmitBtn.disabled = false
        loading.value = false
      })
  }
}
</script>

<template>
  <div class="login-container">
    <div v-if="loading" class="loading-box">
      <Loading v-model:active="loading" :width="64" :height="64" loader="dots" :canCancel="true" />
    </div>
    <div class="main-box">
      <div class="box-title">
        <img class="article-icon" src="../assets/icon/article.svg" @click="gotoHome" />
        <div class="web-title">在线文章阅读平台</div>
      </div>
      <div class="change-box">
        <div
          class="login-btn"
          @click="openLoginPage"
          :class="loginPageShow ? 'btn-checked' : 'btn-unchecked'"
        >
          登录
        </div>
        <div
          class="register-btn"
          @click="openRegisterPage"
          :class="!loginPageShow ? 'btn-checked' : 'btn-unchecked'"
        >
          注册
        </div>
      </div>
      <div class="login-box" v-show="loginPageShow">
        <form @submit.prevent="loginForm">
          <div class="input-box">
            <input
              id="login-username"
              type="text"
              name="username"
              v-model="loginUser.username"
              placeholder="请输入用户名"
            />
            <img class="clear-icon" src="../assets/icon/clear.svg" @click="clearLoginUsername" />
          </div>
          <div class="input-box">
            <input
              id="login-password"
              type="password"
              name="password"
              v-model="loginUser.password"
              placeholder="请输入密码"
            />
            <img
              class="open-eyes-icon"
              src="../assets/icon/open-eyes.svg"
              v-show="!loginOpenEyes"
              @click="loginShowPassword"
            />
            <img
              class="close-eyes-icon"
              src="../assets/icon/close-eyes.svg"
              v-show="loginOpenEyes"
              @click="loginShowPassword"
            />
          </div>
          <button id="login-submit-btn" class="input-btn" type="submit">登录</button>
        </form>
      </div>
      <div class="register-box" v-show="!loginPageShow">
        <form @submit.prevent="registerForm">
          <div class="input-box">
            <input
              id="register-username"
              type="text"
              name="username"
              v-model="registerUser.username"
              placeholder="请输入用户名"
            />
            <img class="clear-icon" src="../assets/icon/clear.svg" @click="clearRegisterUsername" />
          </div>
          <div class="input-box">
            <input
              id="register-password"
              type="password"
              name="password"
              v-model="registerUser.password"
              placeholder="请输入密码"
            />
            <img
              class="open-eyes-icon"
              src="../assets/icon/open-eyes.svg"
              v-show="!registerOpenEyes"
              @click="registerShowPassword"
            />
            <img
              class="close-eyes-icon"
              src="../assets/icon/close-eyes.svg"
              v-show="registerOpenEyes"
              @click="registerShowPassword"
            />
          </div>
          <div class="input-box">
            <input
              id="confirm-password"
              type="password"
              name="checkPassword"
              v-model="registerUser.checkPassword"
              placeholder="请输入相同的密码"
            />
            <img
              class="open-eyes-icon"
              src="../assets/icon/open-eyes.svg"
              v-show="!confirmOpenEyes"
              @click="confirmShowPassword"
            />
            <img
              class="close-eyes-icon"
              src="../assets/icon/close-eyes.svg"
              v-show="confirmOpenEyes"
              @click="confirmShowPassword"
            />
          </div>
          <button id="register-submit-btn" class="input-btn" type="submit">注册</button>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>
.login-container {
  background-color: var(--main-bg-color);
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: 0px;
  right: 0px;
  left: 0px;
  min-height: 420px;
  min-width: 470px;
}
.login-container > .main-box {
  background: var(--main-card-bg-color);
  box-shadow: 0px 1px 7px var(--main-shadow-color);
  box-sizing: border-box;
  border-radius: 15px;
  width: 450px;
  height: 400px;
  padding: 15px;
}
.login-container > .main-box > .box-title {
  width: 100%;
  height: 50px;
  box-sizing: border-box;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  border-bottom: 5px dashed var(--main-bg-color);
}
.login-container > .main-box > .box-title > .article-icon {
  width: 40px;
  height: 40px;
  cursor: pointer;
}
.login-container > .main-box > .box-title > .web-title {
  width: 200px;
  height: 50px;
  line-height: 50px;
  text-align: center;
  font-size: x-large;
  color: var(--main-font-color);
  cursor: default;
}
.login-container > .main-box > .change-box {
  width: 100%;
  height: 50px;
  border-bottom: 1px solid var(--main-bg-color);
  box-sizing: border-box;
  padding: 5px 0;
  display: flex;
}
.login-container > .main-box > .change-box > .login-btn,
.login-container > .main-box > .change-box > .register-btn {
  width: calc(50% - 5px);
  height: 40px;
  text-align: center;
  line-height: 40px;
  box-sizing: border-box;
  border-bottom: 3px var(--main-card-bg-color) solid;
  cursor: pointer;
  transition: border 0.5s;
}
.login-container > .main-box > .change-box > .login-btn {
  margin-right: 5px;
}
.login-container > .main-box > .change-box > .register-btn {
  margin-left: 5px;
}
.login-container > .main-box > .change-box > .btn-checked {
  border-bottom: 3px var(--btn-hover-border-color) solid;
}
.login-container > .main-box > .change-box > .btn-unchecked {
  border-bottom: 3px var(--main-card-bg-color) solid;
}
.login-container > .main-box > .login-box,
.login-container > .main-box > .register-box {
  height: 270px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.login-container > .main-box > .login-box input,
.login-container > .main-box > .register-box input {
  border: none;
  outline: none;
  width: 100%;
  height: 100%;
  background: transparent;
  border-bottom: 2px solid transparent;
  box-sizing: border-box;
  transition: border 0.3s;
}
.login-container > .main-box > .login-box input:focus,
.login-container > .main-box > .register-box input:focus {
  border-bottom: 2px var(--btn-hover-border-color) solid;
}
.login-container > .main-box > .login-box .input-box,
.login-container > .main-box > .register-box .input-box {
  width: 300px;
  height: 40px;
  box-sizing: border-box;
  border-radius: 5px;
  background: var(--main-bg-color);
  display: flex;
  align-items: center;
  padding: 5px 15px;
  overflow: hidden;
  margin: 0 0 20px 0;
}
.login-container > .main-box > .login-box .input-btn,
.login-container > .main-box > .register-box .input-btn {
  background: var(--btn-hover-border-color);
  width: 300px;
  height: 40px;
  box-sizing: border-box;
  border-radius: 5px;
  overflow: hidden;
  color: var(--main-card-bg-color);
  font-size: medium;
  transition: all 0.3s;
  box-shadow: 0px 1px 7px var(--main-shadow-color);
  border: 2px dashed transparent;
}
.login-container > .main-box > .login-box .input-btn:hover,
.login-container > .main-box > .register-box .input-btn:hover {
  background: var(--main-card-bg-color);
  color: var(--btn-hover-border-color);
  border: 2px dashed var(--btn-hover-border-color);
}
.open-eyes-icon,
.close-eyes-icon,
.clear-icon {
  width: 20px;
  height: 20px;
  cursor: pointer;
}
</style>
