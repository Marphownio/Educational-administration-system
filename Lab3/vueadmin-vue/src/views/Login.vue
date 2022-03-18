<template>

  <form class="login">
    <h1 style="text-align:center">欢迎使用教务系统</h1>
    <label>
      <input class="IDinput" type="text" id="id" name="id" placeholder="学号/工号"><br>
      <input class="Passwordinput" type="password" id="password" name="password" placeholder="密码"><br>
    </label>
    <input type="submit" value="登录" class="btn" >
  </form>

</template>

<script>
export default {
  name: "Login"
}
</script>

<style scoped>
@import "../assets/css/login.css"
</style>
