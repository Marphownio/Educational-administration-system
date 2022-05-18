<template>
  <head>
    <meta charset="UTF-8">
    <title>选课名单</title>
  </head>
  <body>
  <Nav></Nav>
  <div id="mystudentswel">这是您这学期执教课程的学生列表</div>

  <div id="mystudentbody">
    <div id="mystudentform">
      <el-table :data="myStudentTableData"  row-key="courseCategoryNumbershow" :tree-props="{children: 'children', hasChildren: 'hasChildren'}" >
        <el-table-column align="center" prop="courseCategoryNumbershow" label="课程代码" width="150px"/>
        <el-table-column align="center" prop="courseName" label="课程名称" width="90px"/>
        <el-table-column align="center" prop="courseschoolName" label="开课院系" width="150px"/>
        <el-table-column align="center" prop="coursemajorName" label="所属专业" width="80px"/>
        <el-table-column align="center" prop="coursetype" label="课程类型" width="80px"/>
        <el-table-column align="center" prop="username" label="学生姓名" width="80px" />
        <el-table-column align="center" prop="userId" label="学号" width="80px" />
        <el-table-column align="center" v-slot="scope" prop="status" label="学生状态" width="80px">
          <el-tag v-if="scope.row.status===true&&scope.row.role==='STUDENT'" type="success">在读</el-tag>
          <el-tag v-if="scope.row.status===true&&scope.row.role==='TEACHER'" type="success">在岗</el-tag>
          <el-tag v-if="scope.row.status===false&&scope.row.role==='STUDENT'" type="info" >已毕业</el-tag>
          <el-tag v-if="scope.row.status===false&&scope.row.role==='TEACHER'" type="info">已离职</el-tag>
        </el-table-column>
        <el-table-column align="center" prop="school.schoolName" label="学生院系" width="150px"/>
        <el-table-column align="center" prop="major.majorName" label="学生专业" width="80px"/>
        <el-table-column align="center" prop="phoneNumber" label="手机号" />
        <el-table-column align="center" prop="email" label="邮箱"  />
      </el-table>
    </div>
  </div>

  </body>
</template>

<script src="../../../assets/js/myStudents.js" type="text/javascript">

</script>

<style src="../../../assets/css/myStudents.css" scoped>
</style>
