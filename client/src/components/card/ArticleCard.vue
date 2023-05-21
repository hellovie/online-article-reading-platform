<script setup>
import { computed } from 'vue'
import EivIcon from '@/components/custom/EivIcon.vue'

const props = defineProps({
  index: {
    type: Number,
    required: true
  },
  id: {
    type: String,
    required: true
  },
  title: {
    type: String,
    required: true
  },
  abstract: {
    type: String,
    required: true
  },
  cover: {
    type: String,
    require: false
  },
  modified: {
    type: String,
    require: true
  },
  author: {
    type: Object,
    require: true
  }
})
const modifiedDate = computed(() => {
  return props.modified.slice(0, 10)
})
const contentStyleVar = computed(() => {
  const coverWidth = (props.cover !== '') ? 270 : 0
  return {
    '--cover--width': coverWidth + 'px'
  }
})
const emit = defineEmits(['handle-click'])
const handleClick = () => {
  emit('handle-click')
}
</script>

<template>
  <div class="article-card" @click="handleClick">
    <div v-if="cover !== '' && index % 2 === 1" class="cover">
      <img :src="cover" alt="文章封面" />
    </div>
    <div class="content" :style="contentStyleVar">
      <div class="title singe-line">{{ title }}</div>
      <div class="info">
        <EivIcon icon-class="time" :icon-size="20" />
        <div class="modified-time">更新于: {{ modifiedDate }}</div>
        <span> | </span>
        <img class="author-avatar" :src="author.avatar" alt="">
        <div class="author singe-line">{{ author.nickname }}</div>
      </div>
      <div class="abstract">{{ abstract }}</div>
    </div>
    <div v-if="cover !== '' && index % 2 === 0" class="cover">
      <img :src="cover" alt="文章封面" />
    </div>
  </div>
</template>
<style scoped>
.article-card {
  display: flex;
  align-items: center;
  width: 100%;
  max-width: fit-content;
  height: 180px;
  box-sizing: border-box;
  box-shadow: var(--main-card--shadow);
  border-radius: 10px;
  transition: all 0.3s;
  overflow: auto;
  cursor: pointer;
  background: var(--main-card-bg--color);
}
.article-card:hover {
  box-shadow: var(--main-card-hover--shadow);
}
.article-card:hover .cover > img{
  transform: scale(1.2);
}
.article-card > .cover {
  display: flex;
  flex-shrink: 0;
  width: 270px;
  height: 180px;
  box-sizing: border-box;
  overflow: hidden;
}

.article-card > .cover > img {
  display: block;
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: all 0.6s;
}

.article-card > .content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  height: 180px;
  width: calc(100% - var(--cover--width));
  padding: 0 20px;
  box-sizing: border-box;
}
.article-card > .content > .title {
  height: 40px;
  line-height: 40px;
  padding: 0 10px;
  box-sizing: border-box;
  font-size: x-large;
  font-weight: normal;
  color: var(--main-font-active--color);
}
.article-card > .content > .info {
  display: flex;
  align-items: center;
  height: 30px;
  padding: 0 10px;
}
.article-card > .content > .info > div {
  height: 30px;
  line-height: 30px;
  padding: 0 0  0 3px;
  box-sizing: border-box;
  font-size: small;
  color: var(--user-card--username-color);
}
.article-card > .content > .info > .modified-time {
  width: 140px;
}
.article-card > .content > .info > .author {
  width: calc(100% - 200px);
}
.article-card > .content > .info > span {
  color: var(--user-card--username-color);
}
.article-card > .content > .info > img {
  display: block;
  width: 20px;
  height: 20px;
  border-radius: 10px;
}
.article-card > .content > .info > .author-avatar {
  margin-left: 5px;
}
.article-card > .content > .abstract {
  height: 90px;
  width: 100%;
  line-height: 30px;
  padding: 0 10px;
  box-sizing: border-box;
  font-size: small;
  color: var(--main-font-active--color);

  display: -webkit-box;
  -webkit-box-orient: vertical;
  /* 显示几行 */
  -webkit-line-clamp: 3;
  overflow: hidden;
}
</style>
