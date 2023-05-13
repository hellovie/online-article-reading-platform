<script setup>
import { ref, reactive, inject } from 'vue'
import EivInput from '@/components/custom/EivInput.vue'
import { loginApi } from '@/http/api/user'
import { useUserStore } from '@/stores/user'
import Loading from 'vue-loading-overlay'
const { login } = useUserStore()

const $Toast = inject('Toast')
const loginBy = ref('username')
// 切换不同方式的登录页面
const loginByUsername = () => {
  loginBy.value = 'username'
  initData()
}
const loginByPhoneNumber = () => {
  loginBy.value = 'phone'
  initData()
}
const loginByEmailAddr = () => {
  loginBy.value = 'email'
  initData()
}
// 表单校验规则
const rules = reactive({
  username: [{
    required: true,
    min: 8,
    max: 16,
    message: '用户名长度应该在8到16之间!'
  },
  {
    required: true,
    regex: /^[a-zA-Z]{1}\w*$/,
    message: '用户名必须以字母开头, 只包含字母、数字和下划线!'
  }],
  password: [{
    required: true,
    min: 8,
    max: 16,
    message: '密码长度应该在8到16之间!'
  },
  {
    required: true,
    regex: /^(?![a-zA-Z]+$)(?!\d+$)(?![^\da-zA-Z\s]+$).*$/,
    message: '密码必须由字母、数字和特殊字符中的任意2种组成!'
  }]
})
// 用户名登录方式
const loginFormByUsername = reactive({
  username: '',
  password: ''
})
// 验证表单数据
const validator = (form, rules) => {
  for (const key in form) {
    for (const rule of rules[key]) {
      if (rule.required) {
        if (rule.min && !(rule.min <= form[key].length)) {
          return false
        } if (rule.max && !(rule.max >= form[key].length)) {
          return false
        } if (rule.regex && !(rule.regex.test(form[key]))) {
          return false
        }
      }
    }
  }
  return true
}
// 若无响应, 加载画面最多持续5s.
const closeLoading = () => {
  loading.value = false
}
// 是否可以点击按钮提交
const loading = ref(false)
const sendLoginForm = () => {
  loading.value = true
  switch (loginBy.value) {
    case 'username':
      if (validator(loginFormByUsername, rules)) {
        loginApi(loginFormByUsername).then(data => {
          login(data)
          $Toast.info(`欢迎你, ${data.nickname}!`)
          loading.value = false
        }).catch(error => {
          console.log(error)
          closeLoading()
        })
      } else {
        closeLoading()
      }
      break
    case 'phone':
      break
    case 'email':
      break
    default:
      $Toast.warning('无效的登录页!')
      closeLoading()
  }
}
// 初始化数据
const initData = () => {
  loginFormByUsername.username = ''
  loginFormByUsername.password = ''
}
</script>

<template>
  <div class="login-view">
    <div v-if="loading" class="loading-box">
      <Loading v-model:active="loading" :width="64" :height="64" loader="dots" :canCancel="true" />
    </div>
    <div class="login-title">登录</div>
    <div class="login-box">
      <ul class="login-choose-box">
        <li><a :class="{ active: loginBy === 'username' }" @click="loginByUsername">用户名</a></li>
        <li><a :class="{ active: loginBy === 'phone' }" @click="loginByPhoneNumber">手机号码</a></li>
        <li><a :class="{ active: loginBy === 'email' }" @click="loginByEmailAddr">邮箱地址</a></li>
      </ul>
      <div class="username-login-form" v-show="loginBy === 'username'">
        <EivInput placeholder="请输入用户名"
          input-type="text"
          :input-box-width="300"
          :input-box-height="40"
          prefix-icon="user"
          clearable
          :rules="rules.username"
          v-model:value="loginFormByUsername.username"/>

        <EivInput placeholder="请输入密码"
          input-type="password"
          :input-box-width="300"
          :input-box-height="40"
          prefix-icon="lock"
          show-password
          :rules="rules.password"
          v-model:value="loginFormByUsername.password"/>

        <button class="username-login-btn" @click="sendLoginForm">登录</button>
      </div>
      <div class="phone-login-form" v-show="loginBy === 'phone'"></div>
      <div class="email-login-form" v-show="loginBy === 'email'"></div>
    </div>
  </div>
</template>

<style scoped>
.login-view {
  width: 400px;
  height: 480px;
}

.login-view>.login-title {
  width: 400px;
  height: 50px;
  margin-bottom: 10px;
  line-height: 50px;
  font-weight: bolder;
  font-size: xx-large;
  color: var(--main-font-active--color);
  /* border-bottom: 1px solid var(--main-split-line--color); */
  border-left: 5px solid var(--main-primary--color);
  padding-left: 10px;
  box-sizing: border-box;
}

.login-view>.login-box {
  width: 400px;
  height: 420px;
}

.login-view>.login-box>.login-choose-box {
  width: 400px;
  height: 30px;
  margin-bottom: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 20px;
  position: relative;
}

.login-view>.login-box>.login-choose-box>li>a {
  display: block;
  width: 120px;
  height: 30px;
  text-align: center;
  line-height: 30px;
  border-radius: 5px;
  font-size: small;
  font-weight: bold;
  box-sizing: border-box;
  color: var(--main-font-inactive--color);
  /* border-bottom: 3px solid var(--main-font-active--color); */

  box-shadow: 18px 18px 30px rgba(0, 0, 0, 0.2),
    -18px -18px 30px rgba(255, 255, 255, 1);
  transition: all .2s ease-out;
}

.login-view>.login-box>.login-choose-box>li>a:hover {
  text-decoration: none;
  box-shadow: 0 0 0 rgba(0, 0, 0, 0.2),
    0 0 0 rgba(255, 255, 255, 0.8),
    inset 18px 18px 30px rgba(0, 0, 0, 0.1),
    inset -18px -18px 30px rgba(255, 255, 255, 1);
}

.login-view>.login-box>.login-choose-box .active {
  color: var(--main-font-active--color);
  box-shadow: 0 0 0 rgba(0, 0, 0, 0.2),
    0 0 0 rgba(255, 255, 255, 0.8),
    inset 18px 18px 30px rgba(0, 0, 0, 0.1),
    inset -18px -18px 30px rgba(255, 255, 255, 1);
}

.login-view>.login-box>.username-login-form,
.login-view>.login-box>.phone-login-form,
.login-view>.login-box>.email-login-form {
  /* 实际大小: 360 * 350 */
  width: 400px;
  height: 380px;
  box-sizing: border-box;
  padding: 30px 20px 10px 20px;

  display: flex;
  align-items: center;
  flex-direction: column;
}
.login-view>.login-box>.username-login-form>.username-login-btn {
  width: 300px;
  height: 40px;
  margin: 10px 0px;
  font-size: large;
  background-color: var(--main-primary--color);
  color: #fff;
  box-sizing: border-box;
  border-radius: 5px;
  transition: all 0.3s;
}
.login-view>.login-box>.username-login-form>.username-login-btn:hover {
  background-color: var(--main-primary-hover--color);
}

/* 待开发 */
.login-view>.login-box>.phone-login-form,
.login-view>.login-box>.email-login-form {
  background: url('@/assets/image/under-development.png') no-repeat;
  background-position: center center;
  background-repeat: no-repeat;
  background-size:contain;
}
</style>
