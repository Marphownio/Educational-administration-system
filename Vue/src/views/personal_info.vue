<template>
  <head>
    <meta charset="UTF-8">
    <title>学生选课</title>
  </head>

  <body>
  <Nav></Nav>
  <div id="personal_infor">
  <el-descriptions
      :data="tableData"
      class="descriptions"
      title="您的信息"
      direction="vertical"
      :column="2"
      :align="center"
      border
  >
    <el-descriptions-item prop="name" class="item" label="姓名"></el-descriptions-item>
    <el-descriptions-item prop="id" class="item" label="学/工号"></el-descriptions-item>
    <el-descriptions-item prop="role" class="item" label="身份"></el-descriptions-item>
    <el-descriptions-item prop="college" class="item" label="学院"></el-descriptions-item>
    <el-descriptions-item prop="major" class="item" label="专业"></el-descriptions-item>
    <el-descriptions-item prop="idNumber" class="item" label="身份证号"></el-descriptions-item>
    <el-descriptions-item prop="phoneNumber" class="item" label="手机号">
      <el-button class="verify" type="primary" @click="dialogVisible1=true">修改</el-button>
    </el-descriptions-item>
    <el-descriptions-item prop="email" class="item" label="邮箱">
      <el-button class="verify" type="primary" @click="dialogVisible2=true">修改</el-button>
    </el-descriptions-item>
    <el-descriptions-item prop="status" class="item" label="状态">
        <el-tag size="small">在读</el-tag>
    </el-descriptions-item>
  </el-descriptions>
  </div>

  <el-dialog
      v-model="dialogVisible1"
      title="修改手机号"
      width="600px"
  >
    <el-form
        ref="ruleForm1"
        @submit.prevent="submit_check1"
        :model="ruleForm1"
        :rules="editRules1"
        label-width="100px"
        class="demo-ruleForm"
    >
      <el-form-item label="新手机号" prop="newphonenumber">
        <el-input v-model="ruleForm1.newphonenumber"></el-input>
      </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submit_check1" >确认修改</el-button>
        </el-form-item>
    </el-form>
  </el-dialog>

  <el-dialog
      v-model="dialogVisible2"
      title="修改邮箱地址"
      width="600px"
  >
    <el-form
        ref="ruleForm2"
        @submit.prevent="submit_check2"
        :model="ruleForm2"
        :rules="editRules2"
        label-width="100px"
        class="demo-ruleForm"
    >
      <el-form-item label="新邮箱地址" prop="newemailaddress">
        <el-input v-model="ruleForm2.newemailaddress"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submit_check2" >确认修改</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
  </body>
</template>

<script src="../assets/js/personal_infor.js" type="text/javascript">

</script>

<style scoped>
@import "../assets/css/personal_infor.css";
</style>
