<template>
  <Nav></Nav>
  <p class="huanyin">欢迎使用教务系统！</p>
  <table class="dashboard">
    <tr>
      <td class="tablehead" colspan="4">
&nbsp;&nbsp;&nbsp;&nbsp;服务功能:
      </td>
    </tr>
    <tr>
      <td style="text-align:center" class="choose">
        <router-link to="/personalinfo" >个人信息</router-link>
      </td>
      <td style="text-align:center" class="choose">
        <div id="xx" @click="getState()">选课</div>
      </td>
      <td style="text-align:center" class="choose">
        <router-link to="/myclass" > 我的课表 </router-link>
      </td>
      <td style="text-align:center" class="choose">
        <router-link to="/resetpassword" > 重置密码 </router-link>
      </td>
    </tr>
    <tr>
      <td style="text-align:center" class="choose">
        <router-link to="/finishedclass">已修课程</router-link>
      </td>
      <td style="text-align:center" class="choose">
        <router-link to="/stuApplication">我的选课申请</router-link>
      </td>
      <td style="text-align:center" class="choose">
        <router-link to="" > 敬请期待 </router-link>
      </td>
      <td style="text-align:center" class="choose">
        <router-link to="" > 敬请期待 </router-link>
      </td>
    </tr>
  </table>
</template>

<script>
import Nav from "@/views/inc/Nav";
import request from "@/utils/request";
import ALERTMSG from "@/assets/js/alert";
export default {
  name: "index_stu",
  components:{
    Nav
  },
  methods:{
    getState(){
      const that = this;
      request.get("/admin/courseSelect/status").then(res=>{
        if(res!=="START_FIRST"&&res!=="START_SECOND"){
          ALERTMSG.show(that,true,"当前选课未开放!","warning");
        }
        else{
          this.$router.push('/classselection')
        }
      })
    }
  }
}
</script>

<style src="../../../assets/css/Index.css" scoped>
</style>
