import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/Login'

const routes = [
  {
    path: '/',
    name: 'login',
    component:HomeView
  },
  {
    path: '/adduser',
    name: 'Adduser',
    component: () => import('../views/sys/admin/addUser.vue')
  },
  {
    path: '/index',
    name: 'Index',
    component: () => import('../views/Index.vue')
  },
  {
    path: '/usermanage',
    name: 'usermanage',
    component: () => import('../views/userManage.vue')
  },
  {
    path: '/resetpassword',
    name: 'resetpassword',
    component: () => import('../views/passwordreset.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
