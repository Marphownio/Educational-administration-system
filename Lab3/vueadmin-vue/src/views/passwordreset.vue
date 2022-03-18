<template>
  <form th:action="@{/resetPassword.html}" class="resetpassword">
    <h1 style="text-align:center">首次登录请重置密码</h1>
    <h4 style="text-align:center">密码需包含大小写字母、数字及字符</h4>
    <input type="password" name="newPassword1" placeholder="请输入新密码">
    <input type="password" name="newPassword2" placeholder="请再次输入密码">
    <input type="submit" value="确认" class="verify">
  </form>
</template>

<script>
export default {
  name: "passwordreset"
}
</script>

<style scoped>
@import "../assets/css/resetPassword.css"
</style>