import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/sys/admin/index_admin'

const routes = [
  {
    path: '/',
    name:'Index',
    component:HomeView
  },
  {
    path: '/index_stu',
    name:'index_stu',
    component: () => import('../views/sys/stu/index_stu.vue')
  },
  {
    path: '/index_teacher',
    name:'index_teacher',
    component: () => import('../views/sys/teacher/index_teacher.vue')
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
    path: '/personalinfo',
    name: 'personalinfo',
    component: () => import('../views/personal_info.vue')
  },
  {
    path: '/classselection',
    name: 'classselection',
    component: () => import('../views/sys/stu/stu_class_selection.vue')
  },
  {
    path: '/teaclassmanage',
    name: 'teaclassmanage',
    component: () => import('../views/sys/teacher/tea_class_management.vue')
  },
  {
    path: '/resetpassword',
    name: 'Resetpassword',
    component: () => import('../views/passwordreset.vue')
  },
  /*{
    path: '/test',//测试效果用页面，可删可改
    name: 'Test',
    component: () => import('../views/test.vue')
  }*/
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
