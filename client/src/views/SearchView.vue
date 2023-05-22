<script setup>
import { onMounted, ref, reactive } from 'vue'
import ArticleCard from '@/components/card/ArticleCard.vue'
import { searchPagesApi } from '@/http/api/article'
import { useRouter } from 'vue-router'
import Loading from 'vue-loading-overlay'
const router = useRouter()
const loading = ref(false)
const pageIndex = ref(1)
const pageSize = ref(10)
const pageData = reactive({
  total: 0,
  totalPages: 0,
  articles: []
})
onMounted(() => {
  flushData(pageIndex.value, pageSize.value)
})
const flushData = (index, size) => {
  loading.value = true
  const search = router.currentRoute.value.query.key ? router.currentRoute.value.query.key : ''
  searchPagesApi(index, size, search).then(res => {
    pageData.articles.push(...res.content)
    pageData.total = res.totalElements
    pageData.totalPages = res.totalPages

    pageIndex.value = res.number + 1
    pageSize.value = res.size
    loading.value = false
  }).catch((e) => {
    console.log(e)
    loading.value = false
  })
}
const gotoArticle = (id) => {
  router.push(`/article/${id}`)
}

const scrollBox = () => {
  const scrollHeight = document.getElementById('article-box').scrollHeight
  const scrollTop = document.getElementById('article-box').scrollTop
  const clientHeight = document.getElementById('article-box').clientHeight
  if (scrollHeight - clientHeight === scrollTop) {
    // 滚动条滚到最底部
    // 数据全部获取完毕就不再请求
    if (pageData.totalPages >= pageIndex.value) {
      flushData(pageIndex.value + 1, pageSize.value)
    }
  }
}
</script>

<template>
  <div class="home-container" id="article-box" @scroll="scrollBox">
    <div class="article" v-for="(article, index) in pageData.articles" :key="article.id">
      <ArticleCard
        :index="index"
        :id="article.id"
        :title="article.title"
        :cover="article.cover"
        :abstract="article.articleAbstract"
        :modified="article.gmtModified"
        :author="article.author"
        v-on:handle-click="gotoArticle(article.id)"
      />
    </div>
    <div class="loading">
      <Loading v-model:active="loading" :width="64" :height="64" loader="dots" :canCancel="true" />
    </div>
    <div v-if="(pageData.totalPages === pageIndex || pageData.articles.length === 0) && !loading" class="no-data">-- 已经到底部咯 --</div>
  </div>
</template>

<style scoped>
.home-container {
  width: 100%;
  height: 100%;
  overflow: auto;
}
.home-container > .article {
  display: flex;
  flex-direction: column;
  padding: 10px;
}
.home-container .loading {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
}
.home-container .no-data {
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
