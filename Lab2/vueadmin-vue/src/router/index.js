import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/userManage.vue'

const routes = [
  {
    path: '/',
    name: 'usermanage',
    component:HomeView
  },
  {
    path: '/adduser',
    name: 'Adduser',
    component: () => import('../views/addUser.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
