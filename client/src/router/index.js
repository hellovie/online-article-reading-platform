import { createRouter, createWebHistory } from 'vue-router'
import BaseLayout from '@/components/layout/BaseLayout.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      component: BaseLayout,
      path: '/',
      children: [
        {
          name: 'home',
          path: '',
          component: () => import('@/views/HomeView.vue')
        },
        {
          name: 'search',
          path: 'search',
          component: () => import('@/views/SearchView.vue')
        },
        {
          name: 'fans',
          path: 'fans',
          component: () => import('@/views/FansView.vue')
        },
        {
          name: 'collect',
          path: 'collect',
          component: () => import('@/views/CollectView.vue')
        },
        {
          name: 'creation',
          path: 'creation',
          component: () => import('@/views/CreationView.vue')
        },
        {
          name: 'hot',
          path: 'hot',
          component: () => import('@/views/HotView.vue')
        },
        {
          name: 'follow',
          path: 'follow',
          component: () => import('@/views/FollowView.vue')
        },
        {
          name: 'article',
          path: '/article/:id',
          component: () => import('@/views/ArticleView.vue')
        }
      ]
    },
    {
      name: 'write',
      path: '/write',
      component: () => import('@/views/WriteView.vue')
    },
    {
      name: 'login',
      path: '/login',
      component: () => import('@/views/LoginView.vue')
    },
    {
      name: 'error',
      path: '/:pathMatch(.*)',
      component: () => import('@/views/NotFound.vue')
    }
  ]
})

router.beforeEach((to, from, next) => {
  // 如果是非登录页面, 直接跳转
  if (to.name === 'login' || to.name === 'article' || to.name === 'hot' || to.name === 'search' || to.name === 'home' || to.name === 'error') {
    next()
  } else {
    if (sessionStorage.getItem('token')) {
      next()
    } else {
      router.push('/login')
    }
  }
})

export default router
