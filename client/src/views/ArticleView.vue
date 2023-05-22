<script setup>
import { onMounted, reactive, computed, inject } from 'vue'
import { getOneByIdApi } from '@/http/api/article'
import { useRouter } from 'vue-router'
import { MdPreview } from 'md-editor-v3'
import EivIcon from '@/components/custom/EivIcon.vue'
import 'md-editor-v3/lib/style.css'
import { useArticleStore } from '@/stores/article'
import { useUserStore } from '@/stores/user'
const { isLogin } = useUserStore()
const $Toast = inject('Toast')
const { getCreationType } = useArticleStore()
const { getStatus } = useArticleStore()
const router = useRouter()
const pageData = reactive({
  article: {},
  author: {}
})
onMounted(() => {
  flushData()
})
const flushData = () => {
  const id = router.currentRoute.value.params.id
  if (id && id !== '') {
    getOneByIdApi(id).then(res => {
      pageData.article = res
      pageData.article.gmtCreate = pageData.article.gmtCreate.slice(0, 10)
      pageData.article.gmtModified = pageData.article.gmtModified.slice(0, 10)
      pageData.article.status = getStatus(pageData.article.status)
      pageData.article.creationType = getCreationType(pageData.article.creationType)
      pageData.article.copyright = '**版权:** ' + pageData.article.copyright
      pageData.author = res.author
    })
  }
}
const followAuthor = () => {
  isLogin().then(res => {
    if (res) {
      $Toast.info('功能紧急开发中...')
    } else {
      $Toast.warning('请先登录账号!')
    }
  })
}
const commentArticle = () => {
  isLogin().then(res => {
    if (res) {
      $Toast.info('功能紧急开发中...')
    } else {
      $Toast.warning('请先登录账号!')
    }
  })
}
const collectArticle = () => {
  isLogin().then(res => {
    if (res) {
      $Toast.info('功能紧急开发中...')
    } else {
      $Toast.warning('请先登录账号!')
    }
  })
}
const stepOnTheArticle = () => {
  isLogin().then(res => {
    if (res) {
      $Toast.info('功能紧急开发中...')
    } else {
      $Toast.warning('请先登录账号!')
    }
  })
}
const likeTheArticle = () => {
  isLogin().then(res => {
    if (res) {
      $Toast.info('功能紧急开发中...')
    } else {
      $Toast.warning('请先登录账号!')
    }
  })
}
const gotoAuthorHome = () => {
  isLogin().then(res => {
    if (res) {
      $Toast.info('功能紧急开发中...')
    } else {
      $Toast.warning('请先登录账号!')
    }
  })
}

const creationTypeStyleVar = computed(() => {
  let bgColor = '#3b7d90'
  if (pageData.article.creationType === '原创') {
    bgColor = '#c91f37'
  }
  if (pageData.article.creationType === '翻译') {
    bgColor = '#21a675'
  }
  if (pageData.article.creationType === '转载') {
    bgColor = '#106898'
  }
  return {
    '--creation-type--bg-color': bgColor
  }
})
const statusStyleVar = computed(() => {
  let bgColor = '#3b7d90'
  if (pageData.article.status === '草稿') {
    bgColor = '#900021'
  }
  if (pageData.article.status === '公开') {
    bgColor = '#01847F'
  }
  if (pageData.article.status === '私密') {
    bgColor = '#003152'
  }
  return {
    '--status--bg-color': bgColor
  }
})
</script>

<template>
  <div class="article-container">
    <header class="header">
      <img v-if="pageData.article.cover !== ''" class="cover-image" :src="pageData.article.cover" alt="文章封面">
      <div v-else class="cover-image"></div>
      <div class="article-info">
        <div class="title singe-line">{{ pageData.article.title }}</div>
        <div class="time">
          <EivIcon icon-class="create-time" :icon-size="20" />
          <div>发表于: {{ pageData.article.gmtCreate }}</div>
          <span>&nbsp;|&nbsp;</span>
          <EivIcon icon-class="modified-time" :icon-size="20" />
          <div>更新于: {{ pageData.article.gmtModified }}</div>
        </div>
      </div>
    </header>
    <article class="article">
      <div class="status" :style="statusStyleVar">{{ pageData.article.status }}</div>
      <MdPreview v-model="pageData.article.body" showCodeRowNumber readOnly />
      <div class="author">
        <img :src="pageData.author.avatar" alt="作者头像" class="avatar" @click="gotoAuthorHome" />
        <div class="nickname singe-line">{{ pageData.author.nickname }}</div>
        <div class="author-btn">
          <EivIcon icon-class="follow-author" :icon-size="30" is-pointer is-hover v-on:click="followAuthor" />
          <EivIcon icon-class="comment-article" :icon-size="30" is-pointer is-hover v-on:click="commentArticle" />
          <EivIcon icon-class="collect-article" :icon-size="30" is-pointer is-hover v-on:click="collectArticle" />
          <EivIcon icon-class="low-article" :icon-size="30" is-pointer is-hover v-on:click="stepOnTheArticle" />
          <EivIcon icon-class="advanced-article" :icon-size="30" is-pointer is-hover v-on:click="likeTheArticle" />
        </div>
      </div>
    </article>
    <footer class="copyright">
      <div class="creation-type" :style="creationTypeStyleVar">{{ pageData.article.creationType }}</div>
      <div class="info">
        <MdPreview v-model="pageData.article.copyright" showCodeRowNumber readOnly />
      </div>
    </footer>
    <div class="no-data">-- 已经到底部咯 --</div>
  </div>
</template>

<style scoped>
.article-container {
  width: 100%;
  height: 100%;
  padding: 10px;
  box-sizing: border-box;
  border-radius: 10px;
  overflow: auto;
}
.article-container > .header {
  position: relative;
  width: 100%;
  height: 300px;
  margin: 0 0 10px 0;
  border-radius: 10px;
  overflow: hidden;
}
.article-container > .header > .cover-image {
  display: block;
  width: 100%;
  height: 100%;
  background: var(--main-primary--color);
  object-fit:cover;
}
.article-container > .header > .article-info {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  position: absolute;
  top: 0;
  left: 0;
  z-index: 999;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
}
.article-container > .header > .article-info > .title {
  width: 100%;
  height: 50px;
  line-height: 50px;
  text-align: center;
  padding: 0 20px;
  box-sizing: border-box;
  font-size: xx-large;
  color: #fff;
  /* background-color: aquamarine; */
}

.article-container > .header > .article-info > .time {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 30px;
  color: #fff;
  font-size: small;
  /* background-color: slateblue; */
}
.article-container > .article {
  position: relative;
  box-shadow: var(--main-card--shadow);
  background: var(--main-card-bg--color);
  padding: 5px;
  box-sizing: border-box;
  border-radius: 10px;
}
.article-container > .article > .author {
  display: flex;
  align-items: center;
  width: calc(100% - 20px);
  height: 50px;
  box-sizing: border-box;
  margin: 10px;
  padding: 0px 10px;
  background: var(--user-card--bg-color);
  border-top: 3px dashed var(--main-tips-border--color);
}
.article-container > .article > .author > .avatar {
  width: 40px;
  height: 40px;
  border-radius: 20px;
  box-sizing: border-box;
  cursor: pointer;
  transition: all 1.0s;
}
.article-container > .article > .author > .avatar:hover {
  transform: rotate(360deg);
}

.article-container > .article > .author > .nickname {
  width: 200px;
  height: 40px;
  line-height: 40px;
  font-size: large;
  font-weight: bold;
  box-sizing: border-box;
  margin: 0px 10px;
  color: var(--user-card--nickname-color);
}

.article-container > .article > .author > .author-btn {
  display: flex;
  align-items: center;
  flex-direction: row-reverse;
  gap: 10px;
  width: calc(100% - 200px - 20px - 40px);
  height: 40px;
  overflow: auto;
}
.article-container > .article > .status {
  position: absolute;
  top: -20px;
  left: 10px;
  z-index: 999;
  height: 30px;
  width: 50px;
  border-radius: 5px;
  text-align: center;
  line-height: 30px;
  font-weight: bold;
  color: #fff;
  background: var(--status--bg-color);
}

.article-container > .copyright {
  position: relative;
  margin: 20px 0;
}
.article-container > .copyright > .creation-type {
  position: absolute;
  top: -15px;
  left: 10px;
  z-index: 999;
  height: 30px;
  width: 50px;
  border-radius: 5px;
  text-align: center;
  line-height: 30px;
  font-weight: bold;
  color: #fff;
  background: var(--creation-type--bg-color);
}
.article-container > .copyright > .info {
  border-radius: 5px;
  border: 5px dashed var(--main-tips-border--color);
}
.article-container > .no-data {
  text-align: center;
  line-height: 30px;
  height: 50px;
  width: 100%;
  padding: 10px;
  box-sizing: border-box;
  border-radius: 10px;
  font-size: small;
  color: var(--user-card--username-color);
}
</style>
