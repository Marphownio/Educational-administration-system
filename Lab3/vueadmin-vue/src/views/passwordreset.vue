<template>
  <html lang="zh-CN" xmlns:th="http://www.thymeleaf.org">
  <form class="Preset" th:action="@{/resetPassword}" >
    <h1 style="text-align:center">首次登录请重置密码</h1>
    <h4 style="text-align:center">密码需包含大小写字母、数字及字符</h4>

    <!--如果msg不为空，提示错误-->
    <p style="color:red" th:text="${msg}" th:if="${not #strings.isEmpty(msg)}"></p>

    <input onblur="testpassword()" type="password" id = "newPassword1" name="newPassword1" placeholder="请输入新密码">
    <span id="warn_msg" style="align-content: center;" >&nbsp;</span>
    <input onblur="WhetherSame()" type="password" id = "newPassword2" name="newPassword2" placeholder="请再次输入密码">
    <input type="submit" value="确认" onclick="return submit_check()" class="verify">
  </form>
  </html>

</template>
<script src="../assets/js/resetpasswords.js"></script>
<style scoped>
@import "../assets/css/resetPassword.css";
</style>