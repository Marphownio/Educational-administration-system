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
        <el-form :inline="true" >
            <el-form-item>
                <el-button type="primary" @click="dialogVisible=true">审核课程申请</el-button>
            </el-form-item>
        </el-form>
        <el-table :data="tableData" >
            <el-table-column fixed prop="id" label="课程编号" width="120px"/>
            <el-table-column prop="name" label="课程名称" width="120px" />
            <el-table-column prop="college" label="开课院系" width="120px"/>
            <el-table-column prop="major" label="学时" width="120px"/>
            <el-table-column prop="idNumber" label="学分" width="120px"/>
            <el-table-column prop="phoneNumber" label="任课教师" width="120px"/>
            <el-table-column prop="email" label="课程介绍" width="120px"/>
            <el-table-column prop="state" label="上课时间" width="120px"/>
            <el-table-column prop="email" label="上课地点" width="120px"/>
            <el-table-column prop="state" label="选课容量" width="120px"/>
            <el-table-column fixed="right" prop="icon" label="操作" width="170px">
                <div>
                    <el-button @click="editHandle(scope.row.id)">编辑</el-button>
                    <el-popconfirm title="确认删除吗？">
                        <template #reference>
                            <el-button type="danger">删除</el-button>
                        </template>
                    </el-popconfirm>
                </div>
            </el-table-column>
        </el-table>


        <el-dialog
                v-model="dialogVisible"
                title="课程申请"
                width="80%"
        >
            <el-table :data="applyData" >
                <el-table-column fixed prop="request" label="请求" width="120px"><!--指的是“增加”、“删除”、“修改”之类的要求-->
                <template v-slot="scope">
                    <el-tag v-if="scope.row.request===1" type="success" >添加</el-tag>
                    <el-tag v-if="scope.row.request===2" >修改</el-tag>
                    <el-tag v-if="scope.row.request===3" type="danger" >删除</el-tag>
                </template>
                </el-table-column>
                <el-table-column prop="id" label="课程编号" width="120px"/>
                <el-table-column prop="name" label="课程名称" width="120px" />
                <el-table-column prop="college" label="开课院系" width="120px"/>
                <el-table-column prop="major" label="学时" width="120px"/>
                <el-table-column prop="idNumber" label="学分" width="120px"/>
                <el-table-column prop="phoneNumber" label="任课教师" width="120px"/>
                <el-table-column prop="email" label="课程介绍" width="120px"/>
                <el-table-column prop="state" label="上课时间" width="120px"/>
                <el-table-column prop="email" label="上课地点" width="120px"/>
                <el-table-column prop="state" label="选课容量" width="120px"/>
                <el-table-column fixed="right" prop="icon" label="操作" width="170px">
                    <div>
                        <el-button @click="editHandle(scope.row.id)">同意</el-button>
                        <el-button type="danger">拒绝</el-button>
                    </div>
                </el-table-column>
            </el-table>
        </el-dialog>

    </div>
    </body>
</template>

<script src="../../../assets/js/lessonManage.js" type="text/javascript">

</script>

<style scoped>
    @import "../../../assets/css/lessonManage.css";
</style>
