<template>
<head>
    <meta charset="UTF-8">
    <title>选课申请管理</title>
</head>

    <body>
    <Nav></Nav>

    <div class="void">
        <span></span>
    </div>

    <div class="showlist">
        <el-form :inline="true" >
        </el-form>
        <el-table :data="applicationData" >
            <el-table-column prop="applicationId" v-if="false" />
            <el-table-column prop="course.courseCategory.courseName" label="课程名称" width="120px" />
            <el-table-column prop="courseNumbershow" label="课程编号" width="120px"/>
            <el-table-column prop="course.teacher.username" label="任课教师" width="120px"/>
            <el-table-column prop="course.teacher.userId" label="教师工号" width="120px"/>
            <el-table-column prop="student.username" label="学生姓名" width="120px"/>
            <el-table-column prop="student.userId" label="学生学号" width="120px"/>
            <el-table-column prop="reason" label="申请理由" width="500px"/>
            <el-table-column v-slot="scope" fixed="right" prop="icon" label="操作" width="170px">
                <div>
                    <el-button @click="passApplication(scope.row.applicationId)">同意</el-button>
                    <el-button type="danger" @click="rejectApplication(scope.row.applicationId)">拒绝</el-button>
                </div>
            </el-table-column>
        </el-table>
    </div>

</body>
</template>

<script src="../../../assets/js/applicationManage.js" type="text/javascript">

</script>

<style src="../../../assets/css/applicationManage.css" scoped>
</style>
