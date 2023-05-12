import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from '@/App.vue'
import router from '@/router'

import '@/assets/style/global.css'

import { useToast } from 'vue-toast-notification'
import 'vue-toast-notification/dist/theme-sugar.css'

const app = createApp(App)
const Toast = useToast({
  position: 'top-right',
  duration: 3000
})

app.use(createPinia())
app.use(router)
app.provide('Toast', Toast)
app.mount('#app')

export { Toast }
