<script setup>
import { ref, reactive } from 'vue'
import EivIcon from '@/components/custom/EivIcon.vue'
import EivInput from '@/components/custom/EivInput.vue'
import UserCard from '@/components/card/UserCard.vue'
import TodoCard from '@/components/card/TodoCard.vue'
import $Toast from '@/main.js'

const todoList = reactive([
  {
    id: '1',
    name: '这是一条已完成的代办事项!',
    type: 'TEXT',
    status: 'FINISH'
  },
  {
    id: '2',
    name: '这是一条未完成的代办事项!',
    type: 'TEXT',
    status: 'TODO'
  },
  {
    id: '3',
    name: '这是一条已完成的文章代办事项!',
    type: 'ARTICLE',
    status: 'TODO'
  },
  {
    id: '4',
    name: '这是一条未完成的文章代办事项!',
    type: 'ARTICLE',
    status: 'TODO'
  },
  {
    id: '5',
    name: '这是一条未完成的代办事项!',
    type: 'TEXT',
    status: 'TODO'
  }
])
const searchKey = ref('')
const searchTips = ref('请输入关键字')
const addTodo = () => {
  $Toast.open({
    message: '功能紧急开发中...',
    type: 'info',
    position: 'top-right',
    duration: 3000
  })
}
const sendBug = () => {
  $Toast.open({
    message: '功能紧急开发中...',
    type: 'info',
    position: 'top-right',
    duration: 3000
  })
}
const search = () => {
  $Toast.open({
    message: '功能紧急开发中...',
    type: 'info',
    position: 'top-right',
    duration: 3000
  })
}
</script>

<template>
  <div class="base-layout">
    <header class="header">
      <div class="web-title">
        <EivIcon icon-class="logo" :icon-size="40" />
        <img src="@/assets/image/title.png" alt="在线文章阅读平台" />
      </div>
        <EivInput :placeholder="searchTips"
          input-type="text"
          :input-box-width="300"
          :input-box-height="35"
          suffix-icon="search"
          suffix-icon-is-pointer
          suffix-icon-is-hover
          v-on:suffixIconClick="search"
          v-model:value="searchKey"/>
      <button class="commit-bug-btn" @click="sendBug"><EivIcon icon-class="question" :icon-size="20" is-pointer />&nbsp;问题反馈</button>
    </header>
    <div class="middle">
      <main class="main">
        <router-view />
      </main>
      <aside class="aside">
        <div class="user-info">
          <UserCard />
        </div>
        <div class="user-todo">
          <div class="user-todo-title">
            <EivIcon icon-class="todo" :icon-size="30" />
            <div class="title">待办事项</div>
            <EivIcon icon-class="add-todo" :icon-size="25" isPointer is-hover v-on:click="addTodo"/>
          </div>
          <div class="todo-list">
            <div class="todo-card" v-for="todo in todoList" :key="todo.id">
              <TodoCard />
            </div>
          </div>
        </div>
      </aside>
    </div>
    <ul class="footer">
      <li><a href="https://blog.csdn.net/thehellovie" target="_blank">CSDN</a></li>|
      <li><a href="https://gitee.com/hellovie" target="_blank">Gitee</a></li>|
      <li><a href="https://hellovie.github.io" target="_blank">Copyright © 2023 Hellovie. All rights reserved.</a></li>
    </ul>
  </div>
</template>

<style scoped>
.base-layout {
  display: flex;
  align-items: center;
  justify-content: center;
  position: absolute;
  top: 0;
  left: 0;
  min-width: 1200px;
  min-height: 560px;
  width: 100%;
  height: 100%;
  background: var(--main-bg--color);
}
.base-layout>.header {
  display: flex;
  align-items: center;
  justify-content: center;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 50px;
  min-width: 1200px;
  box-sizing: border-box;
  box-shadow: var(--main-card--shadow);
  border-bottom: 1px solid var(--main-split-line--color);
  background: var(--main-card-bg--color);
}
.base-layout>.header>.web-title {
  display: flex;
  align-items: center;
  justify-content: center;
  max-width: 760px;
  padding: 0px 380px 0px 60px;
  height: 50px;
}
.base-layout>.header>.web-title>img {
  display: block;
  height: 40px;
}
.base-layout>.header>.commit-bug-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100px;
  height: 30px;
  margin: 0px 20px 0px 20px;
  font-size: small;
  background-color: var(--main-tips-bg--color);
  color: var(--main-tips-font--color);
  box-sizing: border-box;
  box-shadow: var(--main-card--shadow);
  border-radius: 5px;
  border: 1px solid var(--main-tips-border--color);
  transition: all 0.5s;
  cursor: pointer;
}
.base-layout>.header>.commit-bug-btn:hover {
  border: 1px solid var(--main-primary--color);
}
.base-layout>.middle>.main {
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  width: 670px;
  margin: 0px 10px;
  box-sizing: border-box;
  box-shadow: var(--main-card--shadow);
  border: 1px solid var(--main-split-line--color);
  background: var(--main-card-bg--color);
  overflow: auto;
}
.base-layout>.middle {
  position: absolute;
  top: 60px;
  bottom: 60px;
  width: 1000px;
  min-width: 1000px;
  margin: 0px auto;
}
.base-layout>.middle>.aside {
  position: absolute;
  top: 0;
  right: 10px;
  bottom: 0;
  width: 300px;
  box-sizing: border-box;
  background: transparent;
}
/* 用户信息 */
.base-layout>.middle>.aside>.user-info {
  width: 100%;
  height: 350px;
  margin-bottom: 10px;
  box-sizing: border-box;
  box-shadow: var(--main-card--shadow);
  border: 1px solid var(--main-split-line--color);
  background: var(--main-card-bg--color);
  overflow: auto;
}
/* 用户代办 */
.base-layout>.middle>.aside>.user-todo {
  width: 100%;
  height: calc(100% - 350px - 10px);
  box-sizing: border-box;
  box-shadow: var(--main-card--shadow);
  border: 1px solid var(--main-split-line--color);
  background: var(--main-card-bg--color);
}
.base-layout>.middle>.aside>.user-todo>.user-todo-title {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 30px;
  box-sizing: border-box;
  padding: 0px 3px;
  color: var(--main-font-active--color);
  /* color: var(--main-primary--color); */
  /* -webkit-text-stroke: 1px var(--main-font-active--color); */
  font-weight: bold;
  border-bottom: 1px solid var(--main-split-line--color);
}
.base-layout>.middle>.aside>.user-todo>.user-todo-title>.title {
  width: 100%;
  height: 30px;
  margin-left: 3px;
  line-height: 30px;
}
.base-layout>.middle>.aside>.user-todo>.todo-list {
  width: 100%;
  height: calc(100% - 30px);
  overflow: auto;
}
.base-layout>.middle>.aside>.user-todo>.todo-list>.todo-card {
  width: 100%;
  height: 50px;
  box-sizing: border-box;
  border-bottom: 1px solid var(--main-split-line--color);
}
.base-layout>.footer {
  display: flex;
  align-items: center;
  justify-content: center;
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 50px;
  min-width: 1200px;
  color: var(--main-font-inactive--color);
  background: transparent;
}
.base-layout>.footer>li {
  padding: 0 10px;
}
.base-layout>.footer>li>a {
  color: var(--main-font-inactive--color);
}
</style>
