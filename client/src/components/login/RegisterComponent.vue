<script setup>
import { ref, reactive } from 'vue'
import EivInput from '@/components/custom/EivInput.vue'
import $Toast from '@/main.js'

const registerBy = ref('username')
// 切换不同方式的注册页面
const registerByUsername = () => {
  registerBy.value = 'username'
  initData()
}
const registerByPhoneNumber = () => {
  registerBy.value = 'phone'
  initData()
}
const registerByEmailAddr = () => {
  registerBy.value = 'email'
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
// 用户名注册方式
const registerFormByUsername = reactive({
  username: '',
  password: ''
})
// 相同密码
const confirmPassword = ref('')
// 验证表单数据
const validator = (form, rules) => {
  if (confirmPassword.value !== '' && form.password !== confirmPassword.value) {
    $Toast.open({
      message: '输入的两次密码不相同!',
      type: 'warning',
      position: 'top-right',
      duration: 3000
    })
    confirmPassword.value = ''
    return false
  }
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
// 是否可以点击按钮提交
const sendRegisterForm = () => {
  switch (registerBy.value) {
    case 'username':
      if (validator(registerFormByUsername, rules)) {
        $Toast.open({
          message: '注册用户名: ' + registerFormByUsername.username + '\n注册密码: ' + registerFormByUsername.password,
          type: 'info',
          position: 'top-right',
          duration: 3000
        })
      }
      break
    case 'phone':
      break
    case 'email':
      break
    default:
      $Toast.open({
        message: '无效的注册页!',
        type: 'info',
        position: 'top-right',
        duration: 3000
      })
  }
}
// 初始化数据
const initData = () => {
  registerFormByUsername.username = ''
  registerFormByUsername.password = ''
  confirmPassword.value = ''
}
</script>

<template>
  <div class="register-view">
    <div class="register-title">注册</div>
    <div class="register-box">
      <ul class="register-choose-box">
        <li><a :class="{ active: registerBy === 'username' }" @click="registerByUsername">用户名</a></li>
        <li><a :class="{ active: registerBy === 'phone' }" @click="registerByPhoneNumber">手机号码</a></li>
        <li><a :class="{ active: registerBy === 'email' }" @click="registerByEmailAddr">邮箱地址</a></li>
      </ul>
      <div class="username-register-form" v-show="registerBy === 'username'">
        <EivInput placeholder="请输入用户名"
          input-type="text"
          :input-box-width="300"
          :input-box-height="40"
          prefix-icon="user"
          clearable
          :rules="rules.username"
          v-model:value="registerFormByUsername.username"/>

        <EivInput placeholder="请输入密码"
          input-type="password"
          :input-box-width="300"
          :input-box-height="40"
          prefix-icon="lock"
          show-password
          :rules="rules.password"
          v-model:value="registerFormByUsername.password"/>

          <EivInput placeholder="请输入相同的密码"
          input-type="password"
          :input-box-width="300"
          :input-box-height="40"
          prefix-icon="tips"
          show-password
          :rules="rules.password"
          v-model:value="confirmPassword"/>

        <button class="username-register-btn" @click="sendRegisterForm">注册</button>
      </div>
      <div class="phone-register-form" v-show="registerBy === 'phone'"></div>
      <div class="email-register-form" v-show="registerBy === 'email'"></div>
    </div>
  </div>
</template>

<style scoped>
.register-view {
  width: 400px;
  height: 480px;
}

.register-view>.register-title {
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

.register-view>.register-box {
  width: 400px;
  height: 420px;
}

.register-view>.register-box>.register-choose-box {
  width: 400px;
  height: 30px;
  margin-bottom: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 20px;
  position: relative;
}

.register-view>.register-box>.register-choose-box>li>a {
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

.register-view>.register-box>.register-choose-box>li>a:hover {
  text-decoration: none;
  box-shadow: 0 0 0 rgba(0, 0, 0, 0.2),
    0 0 0 rgba(255, 255, 255, 0.8),
    inset 18px 18px 30px rgba(0, 0, 0, 0.1),
    inset -18px -18px 30px rgba(255, 255, 255, 1);
}

.register-view>.register-box>.register-choose-box .active {
  color: var(--main-font-active--color);
  box-shadow: 0 0 0 rgba(0, 0, 0, 0.2),
    0 0 0 rgba(255, 255, 255, 0.8),
    inset 18px 18px 30px rgba(0, 0, 0, 0.1),
    inset -18px -18px 30px rgba(255, 255, 255, 1);
}

.register-view>.register-box>.username-register-form,
.register-view>.register-box>.phone-register-form,
.register-view>.register-box>.email-register-form {
  /* 实际大小: 360 * 350 */
  width: 400px;
  height: 380px;
  box-sizing: border-box;
  padding: 30px 20px 10px 20px;

  display: flex;
  align-items: center;
  flex-direction: column;
}
.register-view>.register-box>.username-register-form>.username-register-btn {
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
.register-view>.register-box>.username-register-form>.username-register-btn:hover {
  background-color: var(--main-primary-hover--color);
}

/* 待开发 */
.register-view>.register-box>.phone-register-form,
.register-view>.register-box>.email-register-form {
  background: url('@/assets/image/under-development.png') no-repeat;
  background-position: center center;
  background-repeat: no-repeat;
  background-size:contain;
}
</style>
