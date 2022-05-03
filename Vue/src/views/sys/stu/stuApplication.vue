<template>
  <head>
    <meta charset="UTF-8">
    <title>我的选课申请</title>
  </head>
  <body>
  <Nav></Nav>
  <div id="welc">这是您的选课申请列表</div>
  <div id="finidhedclassbody">
    <el-table
        :data="finishedClassData"
        id="finishedClass"
        border=true>
      <el-table-column align="center"
                       prop="year"
                       label="学年"
                       sortable
                       column-key="date"
                       :filters="years"
                       :filter-method="filterHandler"
      >

      </el-table-column>
      <el-table-column align="center"
                       prop="term"
                       label="学期"
                       sortable
                       column-key="date"
                       :filters="terms"
                       :filter-method="filterHandler"
      >

      </el-table-column>
      <el-table-column align="center" prop="courseId" label="课程编号" />
      <el-table-column align="center" prop="courseName" label="课程名称"/>
      <el-table-column align="center" prop="school.schoolName major.majorName" label="开课院系与专业"  />
      <el-table-column align="center" prop="classHour" label="学时" />
      <el-table-column align="center" prop="credit" label="学分" />
      <el-table-column align="center" prop="teacher.username" label="任课教师" />
      <el-table-column align="center" prop="introduction" label="课程介绍" />
      <el-table-column align="center" prop="courseTime coursePlace" label="上课时间与地点" />
      <el-table-column align="center" prop="capacity" label="选课容量" />
    </el-table>

  </div>
  </body>
</template>


<script src="../../../assets/js/finishedclass.js" type="text/javascript">
</script>


<style src="../../../assets/css/finishedclass.css" scoped>
</style>