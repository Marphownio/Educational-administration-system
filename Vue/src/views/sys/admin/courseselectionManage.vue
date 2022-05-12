<template>
    <head>
        <meta charset="UTF-8">
        <title>课程管理</title>
    </head>

    <body>

    <Nav></Nav>

    <div class="void">
        <span></span>
    </div>

    <div class="showlist">
        <el-table :data="tableData" row-key="courseId" :tree-props="{children: 'children', hasChildren: 'hasChildren'}" default-expand-all>
            <el-table-column prop="courseCategoryNumbershow" label="课程代码" width="120px" sortable/>
            <el-table-column prop="courseName" label="课程名称" width="120px" sortable/>
            <el-table-column prop="courseschoolName" label="开课院系" width="120px" sortable/>
            <el-table-column prop="coursemajorName" label="所属专业" width="120px" sortable/>
            <el-table-column prop="username" label="学生姓名" width="120px"  sortable/>
            <el-table-column v-slot="scope" prop="status" label="学生状态" width="120px" sortable>
                <el-tag v-if="scope.row.status===true&&scope.row.role==='STUDENT'" type="success">在读</el-tag>
                <el-tag v-if="scope.row.status===true&&scope.row.role==='TEACHER'" type="success">在岗</el-tag>
                <el-tag v-if="scope.row.status===false&&scope.row.role==='STUDENT'" type="info" >已毕业</el-tag>
                <el-tag v-if="scope.row.status===false&&scope.row.role==='TEACHER'" type="info">已离职</el-tag>
            </el-table-column>
            <el-table-column prop="schoolName" label="学生院系" width="120px" sortable/>
            <el-table-column prop="majorName" label="学生专业" width="120px" sortable/>
            <el-table-column prop="idNumber" label="身份证号" width="180px" sortable/>
            <el-table-column prop="phoneNumber" label="手机号" width="120px" sortable/>
            <el-table-column prop="email" label="邮箱" width="180px" sortable/>
        </el-table>
    </div>
    </body>
</template>

<script src="../../../assets/js/courseselectionmanage.js" type="text/javascript">

</script>

<style src="../../../assets/css/courseselectionmanage.css" scoped>
</style>
