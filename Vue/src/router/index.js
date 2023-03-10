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
    path: '/coursemanage',
    name: 'Coursemanage',
    component: () => import('../views/sys/admin/courseManage.vue')
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
  {
    path: '/teaApplication',
    name: 'teaApplication',
    component: () => import('../views/sys/teacher/teaApplication.vue')
  },
  {
    path:'/courseselectionmanage',
    name:'Courseselectionmanage',
    component: ()=>import('../views/sys/admin/courseselectionManage')
  },
  {
      path:'/myStudent',
      name:'myStudent',
      component: ()=>import('../views/sys/teacher/myStudents.vue')
    },
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
      //token??????
      if(token==="0"){
        ElMessage({
          dangerouslyUseHTMLString: true,
          type:"warning",
          message: '<strong>?????????????????????????????????</strong>',
        })
        next("/");
      }
      //???????????????
      if(token==="1"){
        if(to.path==="/index_admin"||to.path==="/majormanage"||to.path==="/usermanage"||to.path==="/coursemanage"||to.path==="/timemanage"||to.path==="/classroommanage"||to.path==="/selectmanage"||to.path==="/applicationmanage"||to.path==="/courseselectionmanage"){
          next();
        }
        else{
          ElMessage({
            dangerouslyUseHTMLString: true,
            type:"error",
            message: '<strong>?????????????????????????????????</strong>',
          })
          next(from);
        }
      }
      //????????????
      if(token==="2"){
        if(to.path==="/myStudent"||to.path==="/teaApplication"||to.path==="/index_teacher"||to.path==="/resetpassword"||to.path==="/teaclassmanage"||to.path==="/personalinfo"){
          next();
        }
        else{
          ElMessage({
            dangerouslyUseHTMLString: true,
            type:"error",
            message: '<strong>?????????????????????????????????</strong>',
          })
          next(from);
        }
      }
      //????????????
      if(token==="3"){
        if(to.path==="/index_stu"||to.path==="/stuApplication"||to.path==="/finishedclass"||to.path==="/classselection"||to.path==="/resetpassword"||to.path==="/personalinfo"||to.path==="/myclass"){
          next();
        }
        else{
          ElMessage({
            dangerouslyUseHTMLString: true,
            type:"error",
            message: '<strong>?????????????????????????????????</strong>',
          })
          next(from);
        }
      }
    }
    //???????????????
    else{
      ElMessage({
        dangerouslyUseHTMLString: true,
        type:"error",
        message: '<strong>????????????????????????????????????</strong>',
      })
      next("/");
    }
})

export default router
