<script setup>
import { reactive, inject, ref } from 'vue'
import { MdEditor } from 'md-editor-v3'
import 'md-editor-v3/lib/style.css'
import EivInput from '@/components/custom/EivInput.vue'
import EivIcon from '@/components/custom/EivIcon.vue'
import { uploadImageApi, uploadArticleCoverApi } from '@/http/api/file'
import { createApi, publishArticleApi } from '@/http/api/article'
import { useRouter } from 'vue-router'
const router = useRouter()
const $Toast = inject('Toast')
const isPost = ref(false)
const coverUrl = ref('')
const article = reactive({
  title: '',
  body: '',
  coverId: '',
  articleAbstract: '',
  creationType: 'ORIGINAL',
  quote: ''
})
const postNew = () => {
  article.coverId = ''
  article.quote = ''
  article.creationType = 'ORIGINAL'
  coverUrl.value = ''
  isPost.value = true
}
const clearQuote = () => {
  article.quote = ''
}
const handleUploadImage = async (files, callback) => {
  if (files.length === 1) {
    const formData = new FormData()
    formData.append('image', files[0])
    const res = await uploadImageApi(formData)
    const urls = [1]
    urls[0] = res.url
    callback(urls)
  } else {
    $Toast.info('暂时不支持多文件上传...')
    // 多文件上传不支持
    // for (const i in files) {
    //   const formData = new FormData()
    //   formData.append('file', files[i])
    //   console.log(files[i])
    // }
  }
}
const uploadCover = async () => {
  const file = document.getElementById('upload').files[0]
  const formData = new FormData()
  formData.append('cover', file)
  const res = await uploadArticleCoverApi(formData)
  article.coverId = res.id
  coverUrl.value = res.url
}
const clearCover = () => {
  coverUrl.value = ''
  article.coverId = ''
}
const commit = () => {
  createApi(article).then((res) => {
    publishArticleApi(res.id).then(() => {
      $Toast.success('文章发布成功!')
      router.push('/')
    }).catch(() => {
      $Toast.error('文章发布失败!')
    })
  }).catch(() => {
    $Toast.error('文章发布失败!')
  })
  closePostDialog()
}
const closePostDialog = () => {
  isPost.value = false
}
const save = () => {
  $Toast.info('功能紧急开发中...')
}
</script>

<template>
  <div class="write-container">
    <header class="write-header">
      <EivInput placeholder="请输入文章标题 (5 ~ 50 个字符)" input-type="text" :input-box-width="600" :input-box-height="40"
        v-model:value="article.title" />
      <button class="publish" @click="postNew">发布文章</button>
    </header>
    <main class="body">
      <MdEditor v-model="article.body" showCodeRowNumber @on-upload-img="handleUploadImage" @on-save="save"
        style="width: 100%; height: 100%; margin: 0" />
    </main>
    <div v-show="isPost" class="dialog">
      <div class="dialog-main">
        <div class="dialog-header">
          <span>发布文章</span>
          <div>
            <EivIcon icon-class="close" :icon-size="30" is-pointer is-hover v-on:click="closePostDialog" />
          </div>
        </div>
        <div class="dialog-content">
          <div class="upload">
            <div class="upload-img-file">
              <img v-if="coverUrl !== ''" :src="coverUrl" />
              <label v-else for="upload">
                <div class="cover-placeholder">
                  <EivIcon icon-class="upload" :icon-size="25" is-pointer />
                  <span>上传文章封面</span>
                  <input type="file" id="upload" style="display: none;" @change="uploadCover"/>
                </div>
              </label>
              <div class="upload-cover">
                <EivIcon icon-class="close" :icon-size="25" is-pointer is-hover v-on:click="clearCover" />
              </div>
            </div>
          </div>
          <textarea class="abstract" name="articleAbstract" v-model="article.articleAbstract"
            placeholder="摘要: 字符长度在150到255之间"></textarea>
          <div class="creation-type">
            <div class="radio-box">
              <label><input type="radio" v-model="article.creationType" value="ORIGINAL" @click="clearQuote" />原创</label>
              <label><input type="radio" v-model="article.creationType" value="REPRODUCE" @click="clearQuote" />转载</label>
              <label><input type="radio" v-model="article.creationType" value="TRANSLATE" @click="clearQuote" />翻译</label>
            </div>
            <div class="extra-info">
              <EivInput v-show="article.creationType !== 'ORIGINAL'" placeholder="请输入「转载」或「翻译」的版权来源" input-type="text"
                :input-box-width="420" :input-box-height="35" v-model:value="article.quote" />
              <div v-show="article.creationType === 'ORIGINAL'" class="original-info">
                平台鼓励和保护原创，申请原创将启用
                <a href="https://creativecommons.org/licenses/by-sa/4.0/">Creative Commons</a>
                版权模板
              </div>
            </div>
          </div>
          <button class="commit" @click="commit">发布</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.write-container {
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  min-width: 800px;
  min-height: 550px;
}

.write-container>.dialog {
  position: absolute;
  left: 0;
  top: 0;
  z-index: 999;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.2);
}

.write-container>.dialog>.dialog-main {
  position: relative;
  width: 500px;
  height: 420px;
  background: var(--main-tips-bg--color);
  box-sizing: border-box;
  border-radius: 15px;
}

.write-container>.dialog>.dialog-main>.dialog-header {
  display: flex;
  align-items: center;
  position: absolute;
  top: 10px;
  left: 0;
  width: 100%;
  height: 40px;
}

.write-container>.dialog>.dialog-main>.dialog-content {
  display: flex;
  align-items: center;
  position: absolute;
  flex-direction: column;
  gap: 10px;
  top: 50px;
  left: 0;
  width: 100%;
  height: 360px;
  padding: 20px 40px;
  box-sizing: border-box;
}

.write-container>.dialog>.dialog-main>.dialog-content>.upload {
  display: flex;
  align-items: center;
  width: 100%;
  height: 100px;
}

.write-container>.dialog>.dialog-main>.dialog-content>.upload>.upload-img-file {
  position: relative;
  width: 150px;
  height: 100px;
  overflow: hidden;
  border: 1px dashed var(--main-tips-border--color);
  border-radius: 10px;
}

.write-container>.dialog>.dialog-main>.dialog-content>.upload>.upload-img-file>img {
  width: 100%;
  height: 100%;
}

.write-container>.dialog>.dialog-main>.dialog-content>.upload>.upload-img-file .cover-placeholder {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  width: 100%;
  height: 100%;
  font-size: small;
  color: var(--main-font-inactive--color);
  cursor: pointer;
  overflow: hidden;
}

.write-container>.dialog>.dialog-main>.dialog-content>.upload>.upload-img-file>.upload-cover {
  display: flex;
  align-items: center;
  justify-content: center;
  position: absolute;
  right: 0;
  top: 0;
  width: 30px;
  height: 30px;
}

.write-container>.dialog>.dialog-main>.dialog-content>.abstract {
  width: 100%;
  height: 110px;
  box-sizing: border-box;
  border: 1px solid var(--main-split-line--color);
  border-radius: 10px;
  padding: 5px;
  font-size: small;
  resize: none;
}

.write-container>.dialog>.dialog-main>.dialog-content>.creation-type {
  display: flex;
  align-items: center;
  flex-direction: column;
  width: 100%;
  height: 80px;
}

.write-container>.dialog>.dialog-main>.dialog-content>.creation-type>.radio-box {
  display: flex;
  align-items: center;
  gap: 20px;
  width: 100%;
  height: 30px;
  color: var(--main-font-active--color);
}

.write-container>.dialog>.dialog-main>.dialog-content>.creation-type>.extra-info {
  display: flex;
  align-items: center;
  width: 100%;
  height: 50px;
  padding-top: 10px;
  box-sizing: border-box;
}

.write-container>.dialog>.dialog-main>.dialog-content>.creation-type>.extra-info>.original-info {
  font-size: small;
  font-weight: bold;
  height: 30px;
  width: 100%;
  line-height: 30px;
  text-align: center;
  border: 2px dashed var(--main-split-line--color);
  box-sizing: border-box;
  border-radius: 3px;
  color: var(--user-card--username-color);
}

.write-container>.dialog>.dialog-main>.dialog-content>.commit {
  width: 100%;
  height: 40px;
  font-size: large;
  background-color: var(--main-primary--color);
  color: #fff;
  box-sizing: border-box;
  border-radius: 5px;
  transition: all 0.3s;
}

.write-container>.dialog>.dialog-main>.dialog-content>.commit:hover {
  background-color: var(--main-primary-hover--color);
}

.write-container>.dialog>.dialog-main>.dialog-header>span {
  margin: 0 20px;
  font-size: larger;
  color: var(--main-font-active--color);
}

.write-container>.dialog>.dialog-main>.dialog-header>div {
  margin: 0 20px 0 auto;
}

.write-container>.write-header {
  display: flex;
  align-items: center;
  justify-content: center;
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 50px;
  box-sizing: border-box;
  border-bottom: 2px dashed var(--main-split-line--color);
}

.write-container>.write-header>.publish {
  margin: 40px;
  width: 100px;
  height: 35px;
  font-size: large;
  background-color: var(--main-primary--color);
  color: #fff;
  box-sizing: border-box;
  border-radius: 5px;
  transition: all 0.3s;
}

.write-container>.write-header>.publish:hover {
  background-color: var(--main-primary-hover--color);
}

.write-container>.body {
  position: absolute;
  top: 60px;
  left: 0;
  width: 100%;
  height: calc(100% - 60px);
}
</style>
