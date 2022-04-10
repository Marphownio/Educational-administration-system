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
      <el-table-column prop="courseId" label="课程编号" width="80px"/>
      <el-table-column prop="courseName" label="课程名称" width="80px" />
      <el-table-column prop="school.schoolName" label="开课院系" width="80px" />
      <el-table-column prop="school.schoolId" label="院系Id" v-if="false" width="0"/>
      <el-table-column prop="school" label="院系类" v-if="false" width="0"/>
      <el-table-column prop="major.majorName" label="开课专业" width="80px" />
      <el-table-column prop="major.majorId" label="专业Id" v-if="false" width="0"/>
      <el-table-column prop="major" label="专业类" v-if="false" width="0"/>
      <el-table-column prop="classHour" label="学时" width="80px"/>
      <el-table-column prop="credit" label="学分" width="70px"/>
      <el-table-column prop="teacher.username" label="任课教师" width="80px"/>
      <el-table-column prop="teacher.userId" label="教师Id" v-if="false" width="0"/>
      <el-table-column prop="teacher" label="教师类" v-if="false" width="0"/><el-table-column prop="teacher.userId" label="教师Id" v-if="false" width="0"/>
      <el-table-column prop="introduction" label="课程介绍" width="150px"/>
      <el-table-column prop="classTime" label="上课时间" width="120px"/>
      <el-table-column prop="classPlace" label="上课地点" width="80px"/>
      <el-table-column prop="capacity" label="选课容量" width="80px"/>
      <el-table-column v-slot="scope" fixed="right" prop="icon" label="操作" width="170px">
        <el-button type="primary" @click="edit_class_inform(scope.row)">编辑</el-button>
        <el-popconfirm @confirm="tea_delete(scope.row)" title="确认删除课程吗？">
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
        @submit.prevent="submit_edit()"
        :model="ruleForm"
        :rules="editRules"
        label-width="100px"
        class="demo-ruleForm"
    >
      <el-form-item label="课程名称:" prop="courseName">
        <el-input v-model="ruleForm.courseName"></el-input>
      </el-form-item>
      <el-form-item label="上课时间:" prop="classTime">
        <el-input v-model="ruleForm.classTime"></el-input>
      </el-form-item>
      <el-form-item label="上课教室:" prop="classPlace">
        <el-input v-model="ruleForm.classPlace"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submit_edit()" >确认修改</el-button>
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
        @submit.prevent="apply_new_class()"
        :model="ruleForm1"
        :rules="editRules1"
        label-width="100px"
        class="demo-ruleForm"
    >
      <el-form-item label="课程编号:" prop="applicationId">
        <el-input v-model="ruleForm1.applicationId"></el-input>
      </el-form-item>
      <el-form-item label="课程名称:" prop="courseName">
        <el-input v-model="ruleForm1.courseName"></el-input>
      </el-form-item>
      <el-form-item label="开课院系:" prop="schoolId">
        <el-select v-model="ruleForm1.schoolId" placeholder="选择学院" @change="getMajor">
          <el-option v-for="item in schooldata" :key="item.schoolName" :label="item.schoolName" :value="item.schoolId" />
        </el-select>
      </el-form-item>
      <el-form-item label="开课专业:" prop="majorId">
        <el-select v-model="ruleForm1.majorId" placeholder="选择专业">
          <el-option v-for="item in majordata" :key="item.majorName" :label="item.majorName" :value="item.majorId" />
        </el-select>
      </el-form-item>
      <el-form-item label="任课教师:" prop="teacherId">
        <el-select v-model="ruleForm1.teacherId" placeholder="选择教师">
          <el-option v-for="item in teacherdata" :key="item.username" :label="item.username" :value="item.userId" />
        </el-select>
      </el-form-item>
      <el-form-item label="学时:" prop="classHour">
        <el-input v-model="ruleForm1.classHour"></el-input>
      </el-form-item>
      <el-form-item label="学分:" prop="credit">
        <el-input v-model="ruleForm1.credit"></el-input>
      </el-form-item>
      <el-form-item label="上课时间:" prop="classTime">
        <el-input v-model="ruleForm1.classTime"></el-input>
      </el-form-item>
      <el-form-item label="上课地点:" prop="classPlace">
        <el-input v-model="ruleForm1.classPlace"></el-input>
      </el-form-item>
      <el-form-item label="选课容量:" prop="capacity">
        <el-input v-model="ruleForm1.capacity"></el-input>
      </el-form-item>
      <el-form-item label="课程介绍:" prop="introduction">
        <el-input v-model="ruleForm1.introduction"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="apply_new_class()" >确认申请</el-button>
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
