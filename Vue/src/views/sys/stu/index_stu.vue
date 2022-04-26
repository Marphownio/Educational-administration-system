<template>
  <Nav></Nav>
  <p class="huanyin">欢迎使用教务系统！</p>
  <table class="dashboard">
    <tr>
      <td class="table_head" colspan="4">
        服务功能:
      </td>
    </tr>
    <tr>
      <td style="text-align:center" class="choose">
        <router-link to="/personalinfo" >个人信息</router-link>
      </td>
      <td style="text-align:center" class="choose">
        <router-link to="/classselection">选课</router-link>
      </td>
      <td style="text-align:center" class="choose">
        <router-link to="/myclass" > 我的课表 </router-link>
      </td>
      <td style="text-align:center" class="choose">
        <router-link to="/resetpassword" > 重置密码 </router-link>
      </td>
    </tr>
  </table>
</template>

<script>
import Nav from "@/views/inc/Nav";
export default {
  name: "index_stu",
  components:{
    Nav
  }
}
</script>

<style scoped>
@import "../../../assets/css/Index.css";
</style>
