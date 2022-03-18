import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/Index'

const routes = [
  {
    path: '/',
    name:'index',
    component:HomeView
  },
  {
    path: '/login',
    name:'login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/adduser',
    name: 'Adduser',
    component: () => import('../views/sys/admin/addUser.vue')
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
