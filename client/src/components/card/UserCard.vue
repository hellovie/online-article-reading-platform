<script setup>
import { reactive } from 'vue'
import EivIcon from '@/components/custom/EivIcon.vue'
import $Toast from '@/main.js'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
const { exit } = useUserStore()

const router = useRouter()
const user = reactive({
  avatar: 'https://hellovie.github.io/img/avatar.jpg',
  nickname: '再睡 5 分钟吧',
  username: 'hellovie'
})
const write = () => {
  $Toast.open({
    message: '功能紧急开发中...',
    type: 'info',
    position: 'top-right',
    duration: 3000
  })
}
const openDraft = () => {
  $Toast.open({
    message: '功能紧急开发中...',
    type: 'info',
    position: 'top-right',
    duration: 3000
  })
}
const openUserHomePage = () => {
  $Toast.open({
    message: '功能紧急开发中...',
    type: 'info',
    position: 'top-right',
    duration: 3000
  })
}
const copyUsername = () => {
  $Toast.open({
    message: '功能紧急开发中...',
    type: 'info',
    position: 'top-right',
    duration: 3000
  })
}
const exitLogin = () => {
  exit()
  router.push('/login')
}
const navBtn = (path) => {
  console.log(`跳转路径为: ${path} .`)
  if (path === '/home') {
    router.push(path)
  }
  $Toast.open({
    message: '功能紧急开发中...',
    type: 'info',
    position: 'top-right',
    duration: 3000
  })
}
</script>

<template>
  <div class="user-card">
    <div class="user-info">
      <img :src="user.avatar" alt="用户头像" class="avatar">
      <div class="info">
        <div class="nickname singe-line">{{ user.nickname }}</div>
        <div class="username singe-line">@{{ user.username }}</div>
        <div class="user-btn">
          <EivIcon icon-class="user-card" :icon-size="30" is-pointer is-hover v-on:click="copyUsername" />
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
        <Button class="user-func-btn" @click="navBtn('/follow')">
          <EivIcon icon-class="follow" :icon-size="30" is-pointer />
          <span class="follow">关注</span>
        </Button>
        <Button class="user-func-btn" @click="navBtn('/fans')">
          <EivIcon icon-class="fans" :icon-size="30" is-pointer />
          <span class="fans">粉丝</span>
        </Button>
        <Button class="user-func-btn" @click="navBtn('/collect')">
          <EivIcon icon-class="collect" :icon-size="30" is-pointer />
          <span class="collect">收藏</span>
        </Button>
      </div>
      <div>
        <Button class="user-func-btn" @click="navBtn('/home')">
          <EivIcon icon-class="home" :icon-size="30" is-pointer />
          <span class="home">主页</span>
        </Button>
        <Button class="user-func-btn" @click="navBtn('/creation')">
          <EivIcon icon-class="creation" :icon-size="30" is-pointer />
          <span class="creation">创作</span>
        </Button>
        <Button class="user-func-btn" @click="navBtn('/more')">
          <EivIcon icon-class="more" :icon-size="30" is-pointer />
          <span class="more">更多</span>
        </Button>
      </div>
    </div>
    <div class="user-create">
      <button @click="write">
        <EivIcon icon-class="write" :icon-size="30" is-pointer />&nbsp;开始创作
      </button>
      <button @click="openDraft">
        <EivIcon icon-class="draft" :icon-size="30" is-pointer />&nbsp;草稿箱
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

.user-card>.user-info {
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

.user-card>.user-info>.exit {
  margin: 10px 10px auto auto;
}

.user-card>.user-info>.avatar {
  height: 80px;
  width: 80px;
  margin: 10px;
  border-radius: 5px;
  background: #fff;
}

.user-card>.user-info>.info {
  display: flex;
  flex-direction: column;
  flex: 1;
  width: 125px;
  margin: 10px 10px 10px 0px;
}

.user-card>.user-info>.info>.nickname {
  font-weight: bold;
  font-size: medium;
  color: var(--user-card--nickname-color);
}

.user-card>.user-info>.info>.username {
  font-size: small;
  color: var(--user-card--username-color);
}

.user-card>.user-info>.info>.user-btn {
  display: flex;
  margin-top: auto;
}

.user-card>.user-info>.info>.user-btn>.details {
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

.user-card>.user-info>.info>.user-btn>.details:hover {
  border: 2px solid var(--main-primary--color);
  color: var(--main-primary--color);
}

.user-card>.user-func {
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

.user-card>.user-func>div {
  display: flex;
  gap: 10px;
  width: 100%;
  height: 75px;
}

.user-card>.user-func .user-func-btn {
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

.user-card>.user-func .user-func-btn:hover {
  width: 100%;
}
.user-card>.user-func .user-func-btn:hover .follow {
  color: #fc455d;
}
.user-card>.user-func .user-func-btn:hover .fans {
  color: #d9362c;
}
.user-card>.user-func .user-func-btn:hover .collect {
  color: #ffca28;
}
.user-card>.user-func .user-func-btn:hover .home {
  color: #3b7d90;
}
.user-card>.user-func .user-func-btn:hover .creation {
  color: #efa63a;
}
.user-card>.user-func .user-func-btn:hover .more {
  color: #046ea7;
}
.user-card>.user-create {
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

.user-card>.user-create>button {
  display: flex;
  align-items: center;
  justify-content: center;
  width: calc(100% - 10px);
  height: 100%;
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

.user-card>.user-create>button:hover {
  border: 2px solid var(--main-primary--color);
  color: var(--main-primary--color);
}
</style>
