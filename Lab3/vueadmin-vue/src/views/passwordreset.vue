<template>
  <head>
    <meta charset="UTF-8">
    <title>重置密码</title>
  </head>
  <body>
  <div>
  <el-form
      id="Preset"
      ref="ruleForm"
      :model="ruleForm"
      :rules="editRules"
      label-width="120px"
      @submit.prevent="submit_check"
      class="demo-ruleForm"
      :size="formSize"
  >
    <h1 style="text-align:center">首次登录请重置密码</h1>
    <h4 style="text-align:center">密码需包含大小写字母、数字及字符</h4>
    <el-form-item label="请输入新密码:" prop="pw1" ref="pw1">
      <el-input v-model="ruleForm.pw1"></el-input>
    </el-form-item>
    <el-form-item label="请确认密码:" prop="pw2" ref="pw2">
      <el-input  v-model="ruleForm.pw2"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button  type="primary" @click="submit_check">确认修改</el-button>
    </el-form-item>
  </el-form>
  </div>
  </body>
</template>


<script src="../assets/js/resetpasswords.js" type="text/javascript">
</script>


<style scoped>
@import "../assets/css/resetPassword.css";
</style>