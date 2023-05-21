<script setup>
import { inject, reactive, toRaw, onMounted } from 'vue'
import EivIcon from '@/components/custom/EivIcon.vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
const { isLogin } = useUserStore()
const { exit } = useUserStore()
const $Toast = inject('Toast')

const router = useRouter()
const user = reactive({
  account: {
    id: '',
    avatar: '',
    username: '用户名',
    nickname: '用户昵称',
    lastLoginIp: '',
    lastLoginTime: ''
  }
})
onMounted(() => {
  isLogin().then(res => {
    user.account = res
  })
})
// 是否是当前路由地址
const isCurPath = (path) => {
  if (path === toRaw(router).currentRoute.value.fullPath) {
    return true
  }
  return false
}
const write = () => {
  $Toast.info('功能紧急开发中...')
}
const openDraft = () => {
  $Toast.info('功能紧急开发中...')
}
const openUserHomePage = () => {
  $Toast.info('功能紧急开发中...')
}
const copyUsername = () => {
  const aux = document.createElement('input')
  aux.setAttribute('value', user.account.username)
  document.body.appendChild(aux)
  aux.select()
  document.execCommand('copy')
  document.body.removeChild(aux)
  $Toast.info('已复制用户名到剪切板!')
}
const exitLogin = () => {
  exit()
  router.push('/login')
}
const navBtn = (path) => {
  if (
    path === '/' ||
    path === '/fans' ||
    path === '/collect' ||
    path === '/creation' ||
    path === '/hot' ||
    path === '/follow'
  ) {
    router.push(path)
  }
}
</script>

<template>
  <div class="user-card">
    <div class="no-login-card" v-if="user.account === null" @click="router.push('/login')">
      <div class="title">登录之后, 您可以享受以下权益: </div>
      <div class="rights">
        <div>
          <EivIcon icon-class="better-communicate" :icon-size="25" />
          <span>更深入的互动交流</span>
        </div>
        <div>
          <EivIcon icon-class="better-write-env" :icon-size="25" />
          <span>更高效的创作环境</span>
        </div>
      </div>
      <button>立即登录/注册</button>
    </div>
    <div class="user-info" v-else>
      <img :src="user.account.avatar" alt="用户头像" class="avatar" />
      <div class="info">
        <div class="nickname singe-line">{{ user.account.nickname }}</div>
        <div class="username singe-line">@{{ user.account.username }}</div>
        <div class="user-btn">
          <EivIcon
            icon-class="user-card"
            :icon-size="30"
            is-pointer
            is-hover
            v-on:click="copyUsername"
          />
          <button class="details" @click="openUserHomePage">
            <EivIcon icon-class="user-home" :icon-size="25" is-pointer />个人主页
          </button>
        </div>
      </div>
      <div class="exit">
        <EivIcon icon-class="exit" :icon-size="25" is-pointer is-hover v-on:click="exitLogin" />
      </div>
    </div>
    <div class="user-func">
      <div>
        <button
          class="user-func-btn"
          @click="navBtn('/follow')"
          :class="{ activeFollow: isCurPath('/follow') }"
        >
          <EivIcon icon-class="follow" :icon-size="30" is-pointer />
          <span class="follow">关注</span>
        </button>
        <button
          class="user-func-btn"
          @click="navBtn('/fans')"
          :class="{ activeFans: isCurPath('/fans') }"
        >
          <EivIcon icon-class="fans" :icon-size="30" is-pointer />
          <span class="fans">粉丝</span>
        </button>
        <button
          class="user-func-btn"
          @click="navBtn('/collect')"
          :class="{ activeCollect: isCurPath('/collect') }"
        >
          <EivIcon icon-class="collect" :icon-size="30" is-pointer />
          <span class="collect">收藏</span>
        </button>
      </div>
      <div>
        <button
          class="user-func-btn"
          @click="navBtn('/')"
          :class="{ activeHome: isCurPath('/') }"
        >
          <EivIcon icon-class="recommend" :icon-size="30" is-pointer />
          <span class="home">推荐</span>
        </button>
        <button
          class="user-func-btn"
          @click="navBtn('/hot')"
          :class="{ activeHot: isCurPath('/hot') }"
        >
          <EivIcon icon-class="hot" :icon-size="30" is-pointer />
          <span class="hot">热榜</span>
        </button>
        <button
          class="user-func-btn"
          @click="navBtn('/creation')"
          :class="{ activeCreation: isCurPath('/creation') }"
        >
          <EivIcon icon-class="creation" :icon-size="30" is-pointer />
          <span class="creation">创作</span>
        </button>
      </div>
    </div>
    <div class="user-create">
      <button class="write" @click="write">
        <EivIcon icon-class="write" :icon-size="25" is-pointer />开始创作
      </button>
      <button class="draft" @click="openDraft">
        <EivIcon icon-class="draft" :icon-size="30" is-pointer />草稿箱
      </button>
    </div>
  </div>
</template>

<style scoped>
/* 300 * 350 */
/* 298 * 348 */
.user-card {
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
}

.user-card > .user-info {
  display: flex;
  width: calc(100% - 20px);
  height: 100px;
  box-sizing: border-box;
  border-radius: 10px;
  margin: 10px;
  background: var(--user-card--bg-color);
  box-shadow: var(--main-card--shadow);
  border: 2px solid var(--main-tips-border--color);
}

.user-card > .no-login-card {
  display: flex;
  align-items: center;
  flex-direction: column;
  width: calc(100% - 20px);
  height: 100px;
  box-sizing: border-box;
  border-radius: 10px;
  margin: 10px;
  padding: 5px 0px;
  font-size: small;
  background: var(--user-card--bg-color);
  box-shadow: var(--main-card--shadow);
  border: 2px solid var(--main-tips-border--color);
}

.user-card > .no-login-card > .title {
  width: 100%;
  font-weight: bold;
  padding: 0px 10px;
  box-sizing: border-box;
  color: var(--main-font-active--color);
}

.user-card > .no-login-card > .rights {
  display: flex;
  align-items: center;
  width: 100%;
  height: 30px;
  margin-bottom: 5px;
  padding: 0px 5px;
  box-sizing: border-box;
  color: var(--main-tips-font--color);
}

.user-card > .no-login-card > .rights > div {
  display: flex;
  align-items: center;
}

.user-card > .no-login-card > .rights > div {
  display: flex;
  align-items: center;
}
.user-card > .no-login-card > .rights div:first-child {
  margin-right: auto;
}

.user-card > .no-login-card > button {
  width: calc(100% - 20px);
  flex: 1;
  color: #fff;
  margin: 0px 10px;
  box-sizing: border-box;
  border-radius: 5px;
  background-color: var(--main-primary--color);
  font-weight: bold;
}

.user-card > .no-login-card > button:hover {
  background-color: var(--main-primary-hover--color);
}

.user-card > .user-info > .exit {
  margin: 10px 10px auto auto;
}

.user-card > .user-info > .avatar {
  height: 80px;
  width: 80px;
  margin: 10px;
  border-radius: 5px;
  background: #fff;
}

.user-card > .user-info > .info {
  display: flex;
  flex-direction: column;
  flex: 1;
  width: 125px;
  margin: 10px 10px 10px 0px;
}

.user-card > .user-info > .info > .nickname {
  font-weight: bold;
  font-size: medium;
  color: var(--user-card--nickname-color);
}

.user-card > .user-info > .info > .username {
  font-size: small;
  color: var(--user-card--username-color);
}

.user-card > .user-info > .info > .user-btn {
  display: flex;
  margin-top: auto;
}

.user-card > .user-info > .info > .user-btn > .details {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 90px;
  height: 30px;
  margin-top: auto;
  margin-left: auto;
  font-size: small;
  background-color: var(--main-tips-bg--color);
  color: var(--main-tips-font--color);
  box-sizing: border-box;
  box-shadow: var(--main-card--shadow);
  border-radius: 5px;
  border: 2px solid var(--main-tips-border--color);
  transition: all 0.5s;
  cursor: pointer;
}

.user-card > .user-info > .info > .user-btn > .details:hover {
  border: 2px solid var(--main-primary--color);
  color: var(--main-primary--color);
}

.user-card > .user-func {
  display: flex;
  flex-direction: column;
  gap: 4px;
  width: calc(100% - 20px);
  height: 178px;
  box-sizing: border-box;
  margin: 0px 10px 10px 10px;
  padding: 10px 0px;
  border-top: 2px dashed var(--main-tips-border--color);
  border-bottom: 2px dashed var(--main-tips-border--color);
}

.user-card > .user-func > div {
  display: flex;
  gap: 10px;
  width: 100%;
  height: 75px;
}

.user-card > .user-func .user-func-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  width: 86px;
  height: 75px;
  line-height: 35px;
  box-sizing: border-box;
  box-shadow: var(--main-card--shadow);
  border-radius: 5px;
  border: 1px solid var(--main-tips-border--color);
  transition: all 0.5s;
  color: var(--main-tips-font--color);
  overflow: hidden;
  cursor: pointer;
}

.user-card > .user-func .user-func-btn:hover {
  width: 100%;
}

.user-card > .user-func .user-func-btn:hover .follow {
  color: #fc455d;
}

.activeFollow {
  color: #fc455d !important;
  border: 1px solid #fc455d !important;
}

.user-card > .user-func .user-func-btn:hover .fans {
  color: #d9362c;
}

.activeFans {
  color: #d9362c !important;
  border: 1px solid #d9362c !important;
}

.user-card > .user-func .user-func-btn:hover .collect {
  color: #ffca28;
}

.activeCollect {
  color: #ffca28 !important;
  border: 1px solid #ffca28 !important;
}

.user-card > .user-func .user-func-btn:hover .home {
  color: #f6db2d;
}

.activeHome {
  color: #f6db2d !important;
  border: 1px solid #f6db2d !important;
}

.user-card > .user-func .user-func-btn:hover .creation {
  color: #efa63a;
}

.activeCreation {
  color: #efa63a !important;
  border: 1px solid #efa63a !important;
}

.user-card > .user-func .user-func-btn:hover .hot {
  color: #fc4956;
}

.activeHot {
  color: #fc4956 !important;
  border: 1px solid #fc4956 !important;
}

.user-card > .user-create {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  width: calc(100% - 20px);
  height: 40px;
  box-sizing: border-box;
  border-radius: 5px;
  margin: 0px 10px 10px 10px;
}

.user-card > .user-create > button {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  box-sizing: border-box;
  box-shadow: var(--main-card--shadow);
  border-radius: 5px;
  transition: all 0.3s;
  cursor: pointer;
}
.user-card > .user-create > .write {
  flex: 2;
  color: #fff;
  background-color: var(--main-primary--color);
}
.user-card > .user-create > .draft {
  flex: 1;
  color: var(--main-tips-font--color);
  background-color: var(--main-tips-bg--color);
  border: 1px solid var(--main-tips-border--color);
}
.user-card > .user-create > .write:hover {
  background-color: var(--main-primary-hover--color);
}
.user-card > .user-create > .draft:hover {
  border: 1px solid var(--main-primary--color);
  color: var(--main-primary--color);
}
</style>
