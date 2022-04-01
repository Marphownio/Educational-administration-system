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
                <el-button type="primary" @click="dialogVisible=true">添加课程</el-button><!--这个写法无法写入两个弹窗，需将课程申请换成方法（待完成）-->
                <el-upload
                        class="upload-demo"
                        action=""
                        :on-change="handleChange"
                        accept=".csv"
                        :auto-upload="false">
                    <el-button type="primary" style="margin-left: 20px;margin-right: 20px;margin-top: 10px">通过csv文件添加</el-button>
                </el-upload>
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
                id="apply"
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

       <!-- <el-dialog
                id="add"
                v-model="dialogVisible"
                title="添加用户"
                width="600px"
        >
            <el-form
                    ref="ruleForm"
                    :model="ruleForm"
                    :rules="editRules"
                    label-width="120px"
                    class="demo-ruleForm"
            >
                <el-form-item label="课程名称" prop="name">
                    <el-input v-model="ruleForm.name"></el-input>
                </el-form-item>
                <el-form-item label="开课院系" prop="college" ref="college">
                    <el-input v-model="ruleForm.id"></el-input>
                </el-form-item>
                <el-form-item label="学时" prop="time" ref="time">
                    <el-input v-model="ruleForm.idNumber"></el-input>
                </el-form-item>
                <el-form-item label="手机号" prop="phoneNumber">
                    <el-input v-model="ruleForm.phoneNumber"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="ruleForm.email"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="submitForm" >添加</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>-->

    </div>
    </body>
</template>

<script src="../../../assets/js/lessonManage.js" type="text/javascript">

</script>

<style scoped>
    @import "../../../assets/css/lessonManage.css";
</style>
