<template>
  <head>
    <meta charset="UTF-8">
    <title>登录</title>
  </head>
  <body>
  <div>
    <el-form
        id="loginform"
        ref="loginruleForm"
        :model="loginruleForm"
        :rules="logineditRules"
        @submit.prevent="loginfunc"
        class="demo-ruleForm"
        :size="formSize"
    >
      <h1 style="text-align:center">欢迎使用教务系统</h1>
      <h4 style="text-align:center">请先登录</h4>
      <p style="color:red" text="${msg}" if="${not #strings.isEmpty(msg)}"></p>

      <el-form-item class="typein" label="学/工号:" prop="loginid" ref="loginid" id="loginid">
        <el-input v-model="loginruleForm.loginid"></el-input>
      </el-form-item>
      <el-form-item class="typein" label="密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码:" prop="loginpw" ref="loginpw" id="loginpw">
        <el-input  v-model="loginruleForm.loginpw"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button  type="primary" @click="loginfunc">登录</el-button>
      </el-form-item>
    </el-form>
  </div>
  </body>
</template>


<script src="../assets/js/login.js" type="text/javascript">
</script>


<style scoped>
@import "../assets/css/login.css";
</style>
