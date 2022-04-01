<template>
  <head>
    <meta charset="UTF-8">
    <title>学生选课</title>
  </head>

  <body>
  <Nav></Nav>
  <div id="personal_infor">
  <el-descriptions
      class="descriptions"
      title="您的信息"
      direction="vertical"
      :column="2"
      :align="center"
      border
  >
    <el-descriptions-item class="item" label="姓名">张三</el-descriptions-item>
    <el-descriptions-item class="item" label="学/工号">18100000000</el-descriptions-item>
    <el-descriptions-item class="item" label="身份">学生</el-descriptions-item>
    <el-descriptions-item class="item" label="学院">计算机科学技术学院</el-descriptions-item>
    <el-descriptions-item class="item" label="专业">软件工程</el-descriptions-item>
    <el-descriptions-item class="item" label="身份证号">111111111111111111</el-descriptions-item>
    <el-descriptions-item class="item" label="手机号">11111111111
      <el-button class="verify" type="primary" @click="dialogVisible1=true">修改</el-button>
    </el-descriptions-item>
    <el-descriptions-item class="item" label="邮箱">123@fudan.edu.cn
      <el-button class="verify" type="primary" @click="dialogVisible2=true">修改</el-button>
    </el-descriptions-item>
    <el-descriptions-item class="item" label="状态">
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
