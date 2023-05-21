<script setup>
import { onMounted, reactive, computed } from 'vue'
import { getOneByIdApi } from '@/http/api/article'
import { useRouter } from 'vue-router'
import { MdPreview } from 'md-editor-v3'
import EivIcon from '@/components/custom/EivIcon.vue'
import 'md-editor-v3/lib/style.css'
import { useArticleStore } from '@/stores/article'
const { getCreationType } = useArticleStore()
const { getStatus } = useArticleStore()
const router = useRouter()
const pageData = reactive({
  article: {}
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
      console.log(res)
    })
  }
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
        <div class="author">
          <!-- TODO: 作者昵称, 作者头像, 关注作者 -->
        </div>
      </div>
    </header>
    <article class="article">
      <MdPreview v-model="pageData.article.body" showCodeRowNumber readOnly />
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
  box-shadow: var(--main-card--shadow);
  background: var(--main-card-bg--color);
  padding: 5px;
  box-sizing: border-box;
  border-radius: 10px;
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
