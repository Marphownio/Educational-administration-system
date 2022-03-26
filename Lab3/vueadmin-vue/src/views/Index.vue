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
        <router-link to="/usermanage" > 用户管理 </router-link>
      </td>
      <td style="text-align:center" class="Choose">
        <router-link to="/majormanage">学院/专业管理</router-link>
      </td>
      <td style="text-align:center" class="Choose">
        <a href="" >评教</a>
      </td>
      <td style="text-align:center" class="Choose">
        <a href="">查看课表</a>
      </td>
    </tr>
    <tr>
      <td style="text-align:center" class="Choose">
        <a href="" >教师/学生管理</a>
      </td>
      <td style="text-align:center" class="Choose">
        <a href="">选课</a>
      </td>
      <td style="text-align:center" class="Choose">
        <a href="" >评教</a>
      </td>
      <td style="text-align:center" class="Choose">
        <a href="">查看课表</a>
      </td>
    </tr>
    <tr>
      <td style="text-align:center" class="Choose">
        <a href="" >教师/学生管理</a>
      </td>
      <td style="text-align:center" class="Choose">
        <a href="">选课</a>
      </td>
      <td style="text-align:center" class="Choose">
        <a href="" >评教</a>
      </td>
      <td style="text-align:center" class="Choose">
        <a href="">查看课表</a>
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

<style scoped>
@import "../assets/css/Index.css";
</style>
