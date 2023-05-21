<script setup>
import { ref, inject } from 'vue'
import EivInput from '@/components/custom/EivInput.vue'
import EivIcon from '@/components/custom/EivIcon.vue'
const $Toast = inject('Toast')

const searchKey = ref('')
const searchTips = ref('请输入关键字')

const sendBug = () => {
  $Toast.info('功能紧急开发中...')
}
const search = () => {
  emit('search', searchKey.value)
}
const emit = defineEmits([
  'search'
])
</script>

<template>
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
</template>

<style scoped>
.header {
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
.header>.web-title {
  display: flex;
  align-items: center;
  justify-content: center;
  max-width: 760px;
  padding: 0px 380px 0px 60px;
  height: 50px;
}
.header>.web-title>img {
  display: block;
  height: 40px;
}
.header>.commit-bug-btn {
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
.header>.commit-bug-btn:hover {
  border: 1px solid var(--main-primary--color);
}
</style>
