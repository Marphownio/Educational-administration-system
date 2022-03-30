<template>
  <Nav></Nav>
  <p class="Huanyin">欢迎使用教务系统！</p>
  <table class="Dashboard">
    <tr>
      <td class="table_head" colspan="4">
        服务功能:
      </td>
    </tr>
    <tr>
      <td style="text-align:center" class="Choose">
        <router-link to="" >个人信息维护</router-link>
      </td>
      <td style="text-align:center" class="Choose">
        <router-link to="">已有课程维护</router-link>
      </td>
      <td style="text-align:center" class="Choose">
        <router-link to="/index_teacher" > 敬请期待 </router-link>
      </td>
      <td style="text-align:center" class="Choose">
        <router-link to="/index_teacher" > 敬请期待 </router-link>
      </td>
    </tr>
  </table>
</template>

<script>
import Nav from "@/views/inc/Nav";
export default {
  name: "index_teacher",
  components:{
    Nav
  }
}
</script>

<style scoped>
@import "../../../assets/css/Index.css";
</style>
