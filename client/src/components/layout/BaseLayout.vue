<script setup>
import { reactive, inject } from 'vue'
import { useRouter } from 'vue-router'
import EivIcon from '@/components/custom/EivIcon.vue'
import UserCard from '@/components/card/UserCard.vue'
import HeaderNav from './HeaderNav.vue'
import FooterInfo from './FooterInfo.vue'
import TodoCard from '@/components/card/TodoCard.vue'
const $Toast = inject('Toast')
const router = useRouter()
const data = reactive({
  todoList: [
    // {
    //   id: '1',
    //   name: '这是一条已完成的代办事项!',
    //   type: 'TEXT',
    //   status: 'FINISH'
    // },
    // {
    //   id: '2',
    //   name: '这是一条未完成的代办事项!',
    //   type: 'TEXT',
    //   status: 'TODO'
    // },
    // {
    //   id: '3',
    //   name: '这是一条已完成的文章代办事项!',
    //   type: 'ARTICLE',
    //   status: 'TODO'
    // },
    // {
    //   id: '4',
    //   name: '这是一条未完成的文章代办事项!',
    //   type: 'ARTICLE',
    //   status: 'TODO'
    // },
    // {
    //   id: '5',
    //   name: '这是一条未完成的代办事项!',
    //   type: 'TEXT',
    //   status: 'TODO'
    // }
  ]
})
const addTodo = () => {
  $Toast.info('功能紧急开发中...')
}
const search = (searchKey) => {
  if (searchKey !== '') {
    const route = router.resolve({
      path: '/search',
      query: {
        key: searchKey
      }
    })
    window.open(route.href, '_self')
  }
}
</script>

<template>
  <div class="base-layout">
    <HeaderNav v-on:search="search" />
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
          <div class="no-todo" v-if="data.todoList.length === 0" @click="addTodo">
            <EivIcon icon-class="add-todo" :icon-size="35" isPointer />
            <div>新建代办事项</div>
          </div>
          <div class="todo-list" v-else>
            <div class="todo-card" v-for="todo in data.todoList" :key="todo.id">
              <TodoCard />
            </div>
          </div>
        </div>
      </aside>
    </div>
   <FooterInfo />
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

.base-layout>.middle>.main {
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  width: 670px;
  margin: 0px 10px;
  box-sizing: border-box;
  /* box-shadow: var(--main-card--shadow);
  border: 1px solid var(--main-split-line--color);
  background: var(--main-card-bg--color); */
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
.base-layout>.middle>.aside>.user-todo>.no-todo {
  display: flex;
  align-items: center;
  justify-content: center;
  width: calc(100% - 20px);
  height: 40px;
  margin: 5px 10px;
  box-sizing: border-box;
  color: var(--main-tips-font--color);
  background-color: var(--main-tips-bg--color);
  border: 1px solid var(--main-tips-border--color);
  border-radius: 5px;
  transition: all 0.3s;
  cursor: pointer;
}
.base-layout>.middle>.aside>.user-todo>.no-todo>div {
  margin-left: 5px;
}
.base-layout>.middle>.aside>.user-todo>.no-todo:hover {
  border: 1px solid var(--main-primary--color);
  color: var(--main-primary--color);
  box-shadow: var(--main-card--shadow);
}
.base-layout>.middle>.aside>.user-todo>.todo-list>.todo-card {
  width: 100%;
  height: 50px;
  box-sizing: border-box;
  border-bottom: 1px solid var(--main-split-line--color);
}
</style>
