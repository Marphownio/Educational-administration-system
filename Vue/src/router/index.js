import {createRouter, createWebHashHistory} from 'vue-router'
import HomeView from '../views/Login'
import tokenmanage from "@/utils/Tokenmanage";
import { ElMessage } from 'element-plus'
const routes = [
  {
    path: '/',
    name:'login',
    component:HomeView
  },
  {
    path: '/index_admin',
    name:'index_admin',
    component: () => import('../views/sys/admin/index_admin.vue')
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
  // {
  //   path: '/login',
  //   name:'Login',
  //   component: () => import('../views/Login.vue')
  // },
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
    path: '/applicationmanage',
    name: 'Applicationmanage',
    component: () => import('../views/sys/admin/applicationManage.vue')
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
  {
    path: '/myclass',
    name: 'mycalss',
    component: () => import('../views/sys/stu/myclass.vue')
  },
  {
    path: '/finishedclass',
    name: 'finishedclass',
    component: () => import('../views/sys/stu/finishedClass.vue')
  },
  {
    path: '/stuApplication',
    name: 'stuApplication',
    component: () => import('../views/sys/stu/stuApplication.vue')
  },
  // {
  //   path: '*',
  //   name: 'NotFound',
  //   component: () => import('../views/NotFoundComponent.vue')
  // }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})



router.beforeEach((to,from,next)=>{
    const that=this;
    let token=tokenmanage.get("token");
    if(to.path==="/"){
      next();
    }
    else if(token){
      //token过期
      if(token==="0"){
        ElMessage({
          dangerouslyUseHTMLString: true,
          type:"warning",
          message: '<strong>登录过期！请重新登录！</strong>',
        })
        next("/");
      }
      //管理员身份
      if(token==="1"){
        if(to.path==="/index_admin"||to.path==="/majormanage"||to.path==="/usermanage"||to.path==="/lessonmanage"||to.path==="/timemanage"||to.path==="/classroommanage"||to.path==="/selectmanage"||to.path==="/applicationmanage"){
          next();
        }
        else{
          ElMessage({
            dangerouslyUseHTMLString: true,
            type:"error",
            message: '<strong>没有权限访问目标页面！</strong>',
          })
          next(from);
        }
      }
      //教师身份
      if(token==="2"){
        if(to.path==="/index_teacher"||to.path==="/resetpassword"||to.path==="/teaclassmanage"||to.path==="/personalinfo"){
          next();
        }
        else{
          ElMessage({
            dangerouslyUseHTMLString: true,
            type:"error",
            message: '<strong>没有权限访问目标页面！</strong>',
          })
          next(from);
        }
      }
      //学生身份
      if(token==="3"){
        if(to.path==="/index_stu"||to.path==="//stuApplication"||to.path==="/finishedclass"||to.path==="/classselection"||to.path==="/resetpassword"||to.path==="/personalinfo"||to.path==="/myclass"){
          next();
        }
        else{
          ElMessage({
            dangerouslyUseHTMLString: true,
            type:"error",
            message: '<strong>没有权限访问目标页面！</strong>',
          })
          next(from);
        }
      }
    }
    //未登录情况
    else{
      ElMessage({
        dangerouslyUseHTMLString: true,
        type:"error",
        message: '<strong>没有权限访问！请先登录！</strong>',
      })
      next("/");
    }
})

export default router
