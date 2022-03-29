import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/Index'

const routes = [
  {
    path: '/',
    name:'Index',
    component:HomeView
  },
  {
    path: '/login',
    name:'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/majormanage',
    name: 'Majormanage',
    component: () => import('../views/sys/admin/majorManage.vue')
  },
  {
    path: '/usermanage',
    name: 'Usermanage',
    component: () => import('../views/sys/admin/userManage.vue')
  },
  {
    path: '/lessonmanage',
    name: 'Lessonmanage',
    component: () => import('../views/sys/admin/lessonManage.vue')
  },
  {
    path: '/selectmanage',
    name: 'Selectmanage',
    component: () => import('../views/sys/admin/selectManage.vue')
  },
  {
    path: '/timemanage',
    name: 'Timemanage',
    component: () => import('../views/sys/admin/timeManage.vue')
  },
  {
    path: '/classroommanage',
    name: 'Classroommanage',
    component: () => import('../views/sys/admin/classroomManage.vue')
  },
  {
    path: '/resetpassword',
    name: 'Resetpassword',
    component: () => import('../views/passwordreset.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
