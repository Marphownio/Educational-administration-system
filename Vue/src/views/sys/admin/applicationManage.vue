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
            <el-table-column fixed prop="type" label="请求" width="120px">
                <template v-slot="scope">
                    <el-tag v-if="scope.row.type==='ADD'" type="success" >添加</el-tag>
                    <el-tag v-if="scope.row.type==='UPDATE'" >修改</el-tag>
                    <el-tag v-if="scope.row.type==='DELETE'" type="danger" >删除</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="applicationId" v-if="false" />
            <el-table-column prop="courseName" label="课程名称" width="120px" />
            <el-table-column prop="schoolName" label="开课院系" width="120px"/>
            <el-table-column prop="schoolId" label="学院代码" width="120px"/>
            <el-table-column prop="majorName" label="所属专业" width="120px"/>
            <el-table-column prop="majorId" label="专业代码" width="120px"/>
            <el-table-column prop="classHour" label="学时" width="120px"/>
            <el-table-column prop="credit" label="学分" width="120px"/>
            <el-table-column prop="teacherName" label="任课教师" width="120px"/>
            <el-table-column prop="teacherId" label="教师工号" width="120px"/>
            <el-table-column prop="courseTime" label="上课时间" width="120px"/>
            <el-table-column prop="coursePlace" label="上课地点" width="120px"/>
            <el-table-column prop="capacity" label="选课容量" width="120px"/>
            <el-table-column prop="introduction" label="课程介绍" />
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
