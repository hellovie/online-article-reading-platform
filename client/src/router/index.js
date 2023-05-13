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
        }
      ]
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

export default router
