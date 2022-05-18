<template>
  <head>
    <meta charset="UTF-8">
    <title>已修课程</title>
  </head>
  <body>
  <Nav></Nav>
  <div id="welc">这是您已修的课程</div>
  <div id="finidhedclassbody">
    <el-table
        :data="finishedClassData"
        id="finishedclass"
        border=true>
      <el-table-column align="center"
                       prop="academicYear"
                       label="学年"
                       sortable
                       width="100px"
                       column-key="date"
                       :filters="years"
                       :filter-method="filterHandlerYear"
      >

      </el-table-column>
      <el-table-column align="center"
                       prop="term"
                       label="学期"
                       sortable
                       column-key="date"
                       :filters="terms"
                       :filter-method="filterHandlerTerm"
      >

      </el-table-column>
      <el-table-column align="center" prop="courseNumber,courseCategory.courseCategoryNumber" label="课程编号" width="100px">
        <template v-slot="scope">
          {{scope.row.courseCategory.courseCategoryNumber}}.{{scope.row.courseNumber}}
        </template>
      </el-table-column>
      <el-table-column align="center" prop="courseCategory.courseName" label="课程名称" width="100px" />
      <el-table-column align="center" prop="courseCategory.school.schoolName" label="开课院系" width="150px" />
      <el-table-column align="center" prop="courseCategory.major.majorName" label="开课专业" width="100px" />
      <el-table-column align="center" prop="courseCategory.classHour" label="学时" width="60px"/>
      <el-table-column align="center" prop="courseCategory.credit" label="学分" width="60px"/>
      <el-table-column align="center" prop="teacher.username" label="任课教师" width="100px"/>
      <el-table-column align="center" prop="introduction" label="课程介绍" width="110px"/>
      <el-table-column align="center" prop="courseTime" label="时间地点安排" width="290px">
        <template  v-slot="scope">
          <div v-for="item in scope.row.classArrangements">
            <span v-if="item.dayOfWeek=='MONDAY'">周一</span>
            <span v-if="item.dayOfWeek=='TUESDAY'">周二</span>
            <span v-if="item.dayOfWeek=='WEDNESDAY'">周三</span>
            <span v-if="item.dayOfWeek=='THURSDAY'">周四</span>
            <span v-if="item.dayOfWeek=='FRIDAY'">周五</span>
            <span v-if="item.dayOfWeek=='SATURDAY'">周六</span>
            <span v-if="item.dayOfWeek=='SUNDAY'">周日</span>/
            {{item.building.buildingName}}-{{item.classroom.classroomId}}/
            <span v-for="item2 in item.classTimes">
              {{item2.classTimeId}}&nbsp;
            </span>
            节课
          </div>
        </template>
      </el-table-column>
    </el-table>

  </div>
  </body>
</template>


<script src="../../../assets/js/finishedclass.js" type="text/javascript">
</script>


<style src="../../../assets/css/finishedclass.css" scoped>
</style>