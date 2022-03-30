<template>
  <head>
    <meta charset="UTF-8">
    <title>学生选课</title>
  </head>

  <body>

  <Nav></Nav>
  <span id="welcomeinform">可选课程信息：</span>
  <div id="classinform">
    <el-form :inline="true" >
    </el-form>
    <el-table id="classinformtable" :data="tableData" >
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
      <el-table-column fixed="right" prop="icon" label="操作" width="100px">
        <div>
          <el-popconfirm title="确认选课吗？">
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
