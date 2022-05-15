<template>
  <head>
    <meta charset="UTF-8">
    <title>我的课程申请</title>
  </head>
  <body>
  <Nav></Nav>
  <div id="teawelc">这是您的课程申请列表</div>
  <div id="teaAppliationTable">
    <el-table
        :data="teaApplicationData"
        id="teaApplicationForm"
        border=true>
      <el-table-column align="center" label="课程编号" width="120px">
        <template v-slot="scope">
            {{scope.row.courseCategory.courseCategoryNumber}}.{{scope.row.courseNumber}}
          </template>
      </el-table-column>
      <el-table-column align="center" prop="courseCategory.courseName" label="课程名称" width="100px"/>
      <el-table-column align="center"  prop="courseCategory.school.schoolName" label="开课院系" width="120px"/>
      <el-table-column align="center" prop="courseCategory.major.majorName" label="开课专业" width="90px"/>
      <el-table-column align="center" prop="courseCategory.classHour" label="学时" width="40px" />
      <el-table-column align="center" prop="courseCategory.credit" label="学分" width="40px" />
      <el-table-column align="center" prop="teacher.username" label="任课教师" width="90px"/>
      <el-table-column align="center" prop="capacity" label="课程容量" width="60px"/>
      <el-table-column align="center" label="课程安排">
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
      <el-table-column align="center" prop="introduction" label="课程介绍" width="180px"/>
      <el-table-column align="center" v-slot="scope" width="80px" label="申请类型">
        <el-tag v-if="scope.row.type=='DELETE'" type="danger" size="small">删除</el-tag>
        <el-tag v-if="scope.row.type=='ADD'" type="success" size="small">新增</el-tag>
        <el-tag v-if="scope.row.type=='UPDATE'" size="small">更新</el-tag>
      </el-table-column>
      <el-table-column align="center" v-slot="scope" width="80px" label="审核状态">
        <el-tag v-if="scope.row.status=='IN_REVIEW'" size="small">审核中</el-tag>
        <el-tag v-if="scope.row.status=='PASS'" type="success" size="small">通过</el-tag>
        <el-tag v-if="scope.row.status=='NOT_PASS'" type="danger" size="small">未通过</el-tag>
      </el-table-column>
      <el-table-column align="center" v-slot="scope" fixed="right" prop="icon" label="操作" width="100px">
        <el-popconfirm @confirm="teaCancleApplication(scope.row)" title="确认取消申请吗？">
          <template #reference>
            <el-button v-if="scope.row.status=='NOT_PASS'||scope.row.status=='PASS'" type="danger" size="small" plain disabled>取消申请</el-button>
            <el-button v-else type="danger" size="small" plain>取消申请</el-button>
          </template>
        </el-popconfirm>
      </el-table-column>
    </el-table>

  </div>
  </body>
</template>


<script src="../../../assets/js/teaApplication.js" type="text/javascript">
</script>


<style src="../../../assets/css/teaApplication.css" scoped>
</style>