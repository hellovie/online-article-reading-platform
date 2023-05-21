<script setup>
import { onMounted, ref, reactive } from 'vue'
import ArticleCard from '@/components/card/ArticleCard.vue'
import { searchPagesApi } from '@/http/api/article'
import { useRouter } from 'vue-router'
const router = useRouter()
const pageIndex = ref(1)
const pageSize = ref(10)
const pageData = reactive({
  total: 0,
  totalPages: 0,
  pageNumber: 0,
  pageSize: 0,
  articles: {}
})
onMounted(() => {
  flushData()
})
const flushData = () => {
  const search = router.currentRoute.value.query.key ? router.currentRoute.value.query.key : ''
  searchPagesApi(pageIndex.value, pageSize.value, search).then(res => {
    pageData.articles = res.content
    pageData.total = res.totalElements
    pageData.totalPages = res.totalPages
    pageData.pageNumber = res.pageNumber + 1
    pageData.pageSize = res.pageSize
  })
}
const gotoArticle = (id) => {
  router.push(`/article/${id}`)
}
</script>

<template>
  <div class="home-container">
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
      <div v-if="pageData.total.length === index + 1" class="no-data" style="margin: 20px 0 0 0;">-- 已经到底部咯 --</div>
    </div>
    <div v-if="pageData.articles.length === 0" class="no-data">-- 已经到底部咯 --</div>
  </div>
</template>

<style scoped>
.home-container {
  width: 100%;
  height: 100%;
}
.home-container > .article {
  display: flex;
  flex-direction: column;
  padding: 10px;
}
.home-container .no-data {
  text-align: center;
  line-height: 30px;
  height: 50px;
  width: 100%;
  padding: 10px;
  box-sizing: border-box;
  box-shadow: var(--main-card--shadow);
  border-radius: 10px;
  font-size: small;
  color: var(--user-card--username-color);
  background: var(--main-card-bg--color);
}
</style>
