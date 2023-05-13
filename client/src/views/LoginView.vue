<script setup>
import { ref, inject } from 'vue'
import { useRouter } from 'vue-router'
import LoginComponent from '@/components/login/LoginComponent.vue'
import RegisterComponent from '@/components/login/RegisterComponent.vue'
import EivIcon from '@/components/custom/EivIcon.vue'

const $Toast = inject('Toast')
const router = useRouter()
const isLoginPage = ref(true)
const gotoLoginPage = () => {
  isLoginPage.value = !isLoginPage.value
}
const gotoRegisterPage = () => {
  isLoginPage.value = !isLoginPage.value
}
const gotoHome = () => {
  router.push('/')
}
// 其他登录方式
const loginByGithub = () => {
  $Toast.info('功能紧急开发中...')
}
const loginByWeiBo = () => {
  $Toast.info('功能紧急开发中...')
}
const loginByWeChat = () => {
  $Toast.info('功能紧急开发中...')
}
const loginByQQ = () => {
  $Toast.info('功能紧急开发中...')
}
const loginByMore = () => {
  $Toast.info('功能紧急开发中...')
}
</script>

<template>
  <div class="login-page">
    <div class="box">
      <div class="ad">
        <img src="@/assets/image/ad.png" alt="宣传" />
      </div>

      <div class="container">
        <div class="web-title">
          <button v-show="!isLoginPage" class="choose-login-container" @click="gotoLoginPage">
            <div class="white-circle"></div>
            前往登录
          </button>
          <button v-show="isLoginPage" class="choose-register-container" @click="gotoRegisterPage">
            <div class="white-circle"></div>
            前往注册
          </button>
          <EivIcon icon-class="logo" isPointer v-on:click="gotoHome"/>
          <img src="@/assets/image/title.png" alt="在线文章阅读平台" />
        </div>

        <!-- 内容大小: 400 * 480 -->
        <div class="main">
          <LoginComponent v-if="isLoginPage" />
          <RegisterComponent v-if="!isLoginPage" />
        </div>

        <div class="other-login">
          <span class="text-muted">其他方式登录</span>
          <div class="other-login-link">
            <div class="other-login-btn" @click="loginByGithub"><img src="@/assets/image/github-mark.png" alt="Github">
            </div>
            <div class="other-login-btn" @click="loginByWeiBo"><img src="@/assets/image/wei-bo.png" alt="微博"></div>
            <div class="other-login-btn" @click="loginByWeChat"><img src="@/assets/image/we-chat.png" alt="微信"></div>
            <div class="other-login-btn" @click="loginByQQ"><img src="@/assets/image/qq.png" alt="QQ"></div>
            <div class="other-login-btn" @click="loginByMore"><img src="@/assets/image/more-login.png" alt="更多"></div>
          </div>
        </div>
      </div>
    </div>

    <ul class="footer">
      <li><a href="https://blog.csdn.net/thehellovie" target="_blank">CSDN</a></li>|
      <li><a href="https://gitee.com/hellovie" target="_blank">Gitee</a></li>|
      <li><a href="https://hellovie.github.io" target="_blank">Copyright © 2023 Hellovie. All rights reserved.</a></li>
    </ul>
  </div>
</template>

<style scoped>
.login-page {
  position: absolute;
  top: 0;
  right: 0;
  left: 0;
  width: 100%;
  height: 100%;
  padding: 10px;
  box-sizing: border-box;
  background-color: var(--main-bg--color);
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  overflow: auto;
  min-width: 1200px;
  min-height: 740px;
}

.login-page>.box {
  --login-page-box--width: 1000px;
  --login-page-box--height: 680px;
  --login-page-box-sub-box--width: 500px;

  width: var(--login-page-box--width);
  height: var(--login-page-box--height);
  min-width: var(--login-page-box--width);
  min-height: var(--login-page-box--height);
  display: flex;
  box-sizing: border-box;
  box-shadow: 10px 10px 5px rgba(0, 0, 0, 0.1);
}

.login-page>.footer {
  height: 30px;
  margin-top: 10px;
  width: var(--login-page-box--width);
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--main-font-inactive--color);
}

.login-page>.footer>li {
  padding: 0 10px;
}

.login-page>.footer>li>a {
  color: var(--main-font-inactive--color);
}

.login-page>.box>.ad {
  width: var(--login-page-box-sub-box--width);
  height: var(--login-page-box--height);
  overflow: hidden;
}

.login-page>.box>.ad>img {
  /* 500 * 680 */
  display: block;
  width: 100%;
  height: 100%;
}

.login-page>.box>.container {
  width: var(--login-page-box-sub-box--width);
  height: var(--login-page-box--height);
  background-color: var(--main-card-bg--color);
}

.login-page>.box>.container>.web-title {
  width: var(--login-page-box-sub-box--width);
  height: 50px;
  margin: 10px 0px;
  padding: 0px 50px 10px 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-bottom: 5px dashed var(--main-split-line--color);
  box-sizing: border-box;
  position: relative;
}

.login-page>.box>.container>.web-title .white-circle {
  width: 10px;
  height: 10px;
  background-color: #fff;
  position: absolute;
  border-radius: 10px;
  top: 10px;
  left: 5px;
  z-index: 3;
}

.login-page>.box>.container>.web-title>.choose-login-container,
.login-page>.box>.container>.web-title>.choose-register-container {
  font-size: medium;
  font-weight: bold;
  width: 110px;
  height: 30px;
  line-height: 30px;
  text-align: center;
  position: absolute;
  top: 0px;
  right: -55px;
  background-color: var(--main-primary--color);
  color: #fff;
  border-top-left-radius: 5px;
  border-bottom-left-radius: 5px;
  z-index: 2;
  transition: all 0.3s;
  cursor: pointer;
}

.login-page>.box>.container>.web-title>.choose-login-container:hover,
.login-page>.box>.container>.web-title>.choose-register-container:hover {
  right: -40px;
}

.login-page>.box>.container>.web-title>.choose-login-container::after,
.login-page>.box>.container>.web-title>.choose-register-container::after {
  content: "";
  display: block;
  position: absolute;
  right: -15px;
  top: 0px;
  bottom: 0;
  border-width: 15px;
  border-style: dashed solid dashed dashed;
  border-color: var(--main-primary--color) transparent var(--main-primary--color) transparent;
}

.login-page>.box>.container>.web-title>img {
  display: block;
  height: 40px;
}

.login-page>.box>.container>.main {
  /* 内容大小: 400 * 480 */
  width: calc(var(--login-page-box-sub-box--width) - 100px);
  height: 480px;
  margin: 0px 50px 10px 50px;
}

.login-page>.box>.container>.other-login {
  width: calc(var(--login-page-box-sub-box--width) - 100px);
  height: 100px;
  margin: 0px 50px 10px 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  position: relative;
}

.login-page>.box>.container>.other-login>.text-muted {
  font-size: small;
  line-height: 20px;
  color: var(--main-font-inactive--color);
  height: 20px;
  width: 100px;
  display: block;
  text-align: center;
}

.login-page>.box>.container>.other-login>.text-muted::before {
  content: "";
  position: absolute;
  width: 150px;
  top: 10px;
  left: 0;
  border: 1px solid var(--main-split-line--color);
}

.login-page>.box>.container>.other-login>.text-muted::after {
  content: "";
  position: absolute;
  width: 150px;
  top: 10px;
  right: 0;
  border: 1px solid var(--main-split-line--color);
}

.login-page>.box>.container>.other-login>.other-login-link {
  width: calc(var(--login-page-box-sub-box--width) - 100px);
  height: 50px;
  margin-top: 30px;
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 40px;
}

.login-page>.box>.container>.other-login>.other-login-link>.other-login-btn {
  width: 50px;
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 10px;
  box-shadow: 18px 18px 30px rgba(0, 0, 0, 0.2),
    -18px -18px 30px rgba(255, 255, 255, 1);
  transition: all .2s ease-out;
}

.login-page>.box>.container>.other-login>.other-login-link>.other-login-btn>img {
  width: 40px;
  height: 40px;
  transition: all .2s ease-out;
}

.login-page>.box>.container>.other-login>.other-login-link>.other-login-btn:hover {
  cursor: pointer;
  box-shadow: 0 0 0 rgba(0, 0, 0, 0.2),
    0 0 0 rgba(255, 255, 255, 0.8),
    inset 18px 18px 30px rgba(0, 0, 0, 0.1),
    inset -18px -18px 30px rgba(255, 255, 255, 1);
}

.login-page>.box>.container>.other-login>.other-login-link>.other-login-btn:hover img {
  width: 38px;
  height: 38px;
}
</style>
