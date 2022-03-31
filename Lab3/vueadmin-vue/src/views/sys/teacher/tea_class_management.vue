<template>
  <head>
    <meta charset="UTF-8">
    <title>课程管理</title>
  </head>

  <body>

  <Nav></Nav>
  <span id="teawelcomeinform">当前课程信息：</span>
  <div id="teaclassinform">
      <el-form id="requirebtn" :inline="true" >
        <el-form-item>
          <el-button type="primary" @click="dialogVisible1=true">申请新课程</el-button>
        </el-form-item>
      </el-form>
    <el-table id="teaclassinformtable" :data="tableData" >
      <el-table-column prop="classid" label="课程编号" width="90px"/>
      <el-table-column prop="classname" label="课程名称" width="90px" />
      <el-table-column prop="faculty" label="开课院系" width="90px" />
      <el-table-column prop="classhours" label="学时" width="90px"/>
      <el-table-column prop="credit" label="学分" width="80px"/>
      <el-table-column prop="teacher" label="任课教师" width="90px"/>
      <el-table-column prop="introduction" label="课程介绍" width="150px"/>
      <el-table-column prop="time" label="上课时间" width="150px"/>
      <el-table-column prop="classroom" label="上课地点" width="90px"/>
      <el-table-column prop="capacity" label="选课容量" width="90px"/>
      <el-table-column fixed="right" prop="icon" label="操作" width="170px">
        <el-button type="primary" @click="dialogVisible=true">编辑</el-button>
        <el-popconfirm title="确认删除课程吗？">
          <template #reference>
            <el-button type="danger">删除</el-button>
          </template>
        </el-popconfirm>
      </el-table-column>
    </el-table>

  </div>


  <el-dialog
      v-model="dialogVisible"
      title="修改当前课程信息"
      width="600px"
  >
    <el-form
        ref="ruleForm"
        @submit.prevent="submit_check"
        :model="ruleForm"
        :rules="editRules"
        label-width="100px"
        class="demo-ruleForm"
    >
      <el-form-item label="新课程名称:" prop="changeclassname">
        <el-input v-model="ruleForm.changeclassname"></el-input>
      </el-form-item>
      <el-form-item label="新上课时间:" prop="changeclasstime">
        <el-input v-model="ruleForm.changeclasstime"></el-input>
      </el-form-item>
      <el-form-item label="新上课教室:" prop="changeclassroom">
        <el-input v-model="ruleForm.changeclassroom"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submit_check" >确认修改</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>


  <el-dialog
      v-model="dialogVisible1"
      title="申请新增课程"
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
      <el-form-item label="课程编号:" prop="newclassid">
        <el-input v-model="ruleForm1.newclassid"></el-input>
      </el-form-item>
      <el-form-item label="课程名称:" prop="newclassname">
        <el-input v-model="ruleForm1.newclassname"></el-input>
      </el-form-item>
      <el-form-item label="开课院系:" prop="newclassfaculty">
        <el-input v-model="ruleForm1.newclassfaculty"></el-input>
      </el-form-item>
      <el-form-item label="学时:" prop="newclasshours">
        <el-input v-model="ruleForm1.newclasshours"></el-input>
      </el-form-item>
      <el-form-item label="学分:" prop="newclasscredits">
        <el-input v-model="ruleForm1.newclasscredits"></el-input>
      </el-form-item>
      <el-form-item label="任课教师:" prop="newclassteacher">
        <el-input v-model="ruleForm1.newclassteacher"></el-input>
      </el-form-item>
      <el-form-item label="课程介绍:" prop="newclassintroduction">
        <el-input v-model="ruleForm1.newclassintroduction"></el-input>
      </el-form-item>
      <el-form-item label="上课时间:" prop="newclasstime">
        <el-input v-model="ruleForm1.newclasstime"></el-input>
      </el-form-item>
      <el-form-item label="上课地点:" prop="newclassroom">
        <el-input v-model="ruleForm1.newclassroom"></el-input>
      </el-form-item>
      <el-form-item label="选课容量:" prop="newclasscapacity">
        <el-input v-model="ruleForm1.newclasscapacity"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submit_check1" >确认申请</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>



  </body>
</template>

<script src="../../../assets/js/tea_class_management.js" type="text/javascript">

</script>

<style>
@import "../../../assets/css/tea_class_management.css";
</style>
