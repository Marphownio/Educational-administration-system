<template>
  <head>
    <meta charset="UTF-8">
    <title>学生选课</title>
  </head>

  <body>
  <Nav></Nav>
  <div id="personalinfor">
  <el-descriptions
      class="descriptions"
      title="您的信息"
      direction="vertical"
      :column="2"
      :align="center"
      border
  >
    <el-descriptions-item class="item" label="姓名">{{this.PersonalData.name}}</el-descriptions-item>
    <el-descriptions-item class="item" label="学/工号">{{this.PersonalData.id}}</el-descriptions-item>
    <el-descriptions-item class="item" label="身份">
      <el-tag size="small">{{this.PersonalData.role}}</el-tag>
    </el-descriptions-item>
    <el-descriptions-item class="item" label="学院">{{this.PersonalData.college}}</el-descriptions-item>
    <el-descriptions-item class="item" label="专业">{{this.PersonalData.major}}</el-descriptions-item>
    <el-descriptions-item class="item" label="身份证号">{{this.PersonalData.idNumber}}</el-descriptions-item>
    <el-descriptions-item class="item" label="手机号">{{this.PersonalData.phoneNumber}}
      <el-button class="verify" type="primary" @click="shownewphone()">修改</el-button>
    </el-descriptions-item>
    <el-descriptions-item class="item" label="邮箱">{{this.PersonalData.email}}
      <el-button class="verify" type="primary" @click="showemail()">修改</el-button>
    </el-descriptions-item>
    <el-descriptions-item class="item" label="状态">
        <el-tag size="small">{{this.PersonalData.status}}</el-tag>
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

<style src="../assets/css/personal_infor.css" scoped>
</style>
