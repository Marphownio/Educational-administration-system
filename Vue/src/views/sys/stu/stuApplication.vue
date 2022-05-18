<template>
  <head>
    <meta charset="UTF-8">
    <title>我的选课申请</title>
  </head>
  <body>
  <Nav></Nav>
  <div id="welc">这是您的选课申请列表</div>
  <div id="stuappliationtable">
    <el-table
        :data="stuApplicationData"
        id="stuapplicationform"
        border=true>
      <el-table-column align="center" prop="course" width="150px" label="申请课程编号">
        <template v-slot="scope">
          {{scope.row.course.courseCategory.courseCategoryNumber}}.{{scope.row.course.courseId}}
        </template>
      </el-table-column>
      <el-table-column align="center" prop="course.courseCategory.courseName" width="180px" label="申请课程名称"/>
      <el-table-column align="center" prop="course.academicYear" width="100px" label="学年"/>
      <el-table-column align="center" prop="course.term" width="60px" label="学期"/>
      <el-table-column align="center" prop="reason" label="申请理由" />
      <el-table-column align="center" v-slot="scope" prop="applicationStatus" width="150px" label="审核状态">
        <el-tag v-if="scope.row.applicationStatus=='IN_REVIEW'" size="small">&nbsp;&nbsp;审&nbsp;&nbsp;核&nbsp;&nbsp;中&nbsp;&nbsp;</el-tag>
        <el-tag v-if="scope.row.applicationStatus=='PASS'" type="success" size="small">&nbsp;&nbsp;审核通过&nbsp;&nbsp;</el-tag>
        <el-tag v-if="scope.row.applicationStatus=='NOT_PASS'" type="danger" size="small">审核未通过</el-tag>
      </el-table-column>
      <el-table-column align="center" v-slot="scope" prop="" label="操作" width="180px">
        <el-popconfirm  @confirm="deleteMyApplication(scope.row)"  title="确认取消申请吗？">
          <template #reference>
            <el-button v-if="scope.row.applicationStatus=='NOT_PASS'||scope.row.applicationStatus=='PASS'" type="danger" size="small" plain disabled>取消申请</el-button>
            <el-button v-else type="danger" size="small" plain>取消申请</el-button>
          </template>
        </el-popconfirm>
      </el-table-column>
    </el-table>

  </div>
  </body>
</template>


<script src="../../../assets/js/stuApplication.js" type="text/javascript">
</script>


<style src="../../../assets/css/stuApplication.css" scoped>
</style>