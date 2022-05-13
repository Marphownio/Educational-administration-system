<template>
    <Nav></Nav>
  <p class="huanyin">欢迎使用教务系统！</p>
  <table class="dashboard">
    <tr>
      <td class="tablehead" colspan="4">
        服务功能:
      </td>
    </tr>
    <tr>
      <td style="text-align:center" class="choose">
        <router-link to="/usermanage" > 用户管理 </router-link>
      </td>
      <td style="text-align:center" class="choose">
        <router-link to="/majormanage">学院/专业管理</router-link>
      </td>
      <td style="text-align:center" class="choose">
          <router-link to="/coursemanage" > 课程管理 </router-link>
      </td>
      <td style="text-align:center" class="choose">
          <router-link to="/selectmanage" > 学期管理 </router-link>
      </td>
    </tr>
    <tr>
      <td style="text-align:center" class="choose">
          <router-link to="/timemanage" > 上课时间管理 </router-link>
      </td>
      <td style="text-align:center" class="choose">
          <router-link to="/classroommanage" > 教室管理 </router-link>
      </td>
      <td style="text-align:center" class="choose">
        <router-link to="/applicationmanage" >选课申请管理</router-link>
      </td>
      <td style="text-align:center" class="choose">
          <router-link to="/courseselectionmanage" >查看选课名单</router-link>
      </td>
    </tr>
  </table>

</template>

<script>
    import Nav from "@/views/inc/Nav";
    export default {
        name: "Index",
        components:{
            Nav
        }
    }
</script>

<style src="../../../assets/css/Index.css" scoped>
</style>
