<template>
  <head>
    <meta charset="UTF-8">
    <title>学生选课</title>
  </head>

  <body>

  <Nav></Nav>
  <span id="welcomeinform">可选课程信息：</span>
  <div id="classinform">
    <el-table id="classinformtable" :data="selectableData" >
      <el-table-column prop="courseId" label="课程编号" width="90px"/>
      <el-table-column prop="courseName" label="课程名称" width="90px" />
      <el-table-column prop="school.schoolName" label="开课院系" width="90px" />
      <el-table-column prop="major.majorName" label="开课专业" width="90px" />
      <el-table-column prop="classHour" label="学时" width="60px"/>
      <el-table-column prop="credit" label="学分" width="60px"/>
      <el-table-column prop="teacher.username" label="任课教师" width="90px"/>
      <el-table-column prop="introduction" label="课程介绍" width="150px"/>
      <el-table-column prop="classTime" label="上课时间" width="150px"/>
      <el-table-column prop="classPlace" label="上课地点" width="90px"/>
      <el-table-column prop="capacity" label="选课容量" width="60px"/>
      <el-table-column v-slot="scope" fixed="right" prop="icon" label="操作" width="100px">
        <div >
          <el-popconfirm @confirm="stu_select(scope.row.courseId)"  title="确认选课吗？">
            <template #reference>
              <el-button type="danger">选课</el-button>
            </template>
          </el-popconfirm>
        </div>
      </el-table-column>
    </el-table>

  </div>
  </body>
</template>

<script src="../../../assets/js/stu_class_selection.js" type="text/javascript">

</script>

<style>
@import "../../../assets/css/stu_class_selection.css";
</style>
