<template>
  <head>
    <meta charset="UTF-8">
    <title>重置密码</title>
  </head>
  <body>
  <Nav></Nav>
  <div id="formbiao">
  <el-form
      id="presetform"
      ref="rpwruleForm"
      :model="rpwruleForm"
      :rules="rpweditRules"
      label-width="90px"
      @submit.prevent="submit_check"
      class="demo-ruleForm"
      :size="formSize"
  >
    <h1 style="text-align:center">请重置密码</h1>
    <h4 style="text-align:center">密码需包含大小写字母、数字及字符</h4>
    <el-form-item class="typein2" label="新密码:" prop="newpw1" ref="newpw1">
      <el-input type="password"  v-model="rpwruleForm.newpw1"></el-input>
    </el-form-item>
    <el-form-item class="typein2" label="再次确认:" prop="newpw2" ref="newpw2">
      <el-input type="password"   v-model="rpwruleForm.newpw2"></el-input>
    </el-form-item>
    <el-form-item class="typein3">
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