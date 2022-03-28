<template>
    <head>
        <meta charset="UTF-8">
        <title>用户管理</title>
    </head>

    <body>

    <Nav></Nav>

    <div class="void">
        <span></span>
    </div>

    <div class="showlist">
        <el-form :inline="true" >
            <el-form-item>
                <el-button type="primary" @click="dialogVisible=true">添加</el-button>
            </el-form-item>
        </el-form>
        <el-table :data="tableData" >
            <el-table-column fixed prop="id" label="学号/工号" width="120px"/>
            <el-table-column prop="role" label="角色" width="120px" />
            <el-table-column prop="name" label="姓名" width="120px" />
            <el-table-column prop="" label="学院" width="120px"/>
            <el-table-column prop="" label="专业" width="120px"/>
            <el-table-column prop="idNumber" label="身份证号" width="180px"/>
            <el-table-column prop="phoneNumber" label="手机号" width="120px"/>
            <el-table-column prop="email" label="邮箱" width="180px"/>
            <el-table-column prop="state" label="状态" width="120px"/>
            <el-table-column fixed="right" prop="icon" label="操作" width="120px">
            <div>
                <el-button type="text" @click="editHandle(row.id)">编辑</el-button>
                <el-popconfirm title="确认删除吗？">
                    <template #reference>
                        <el-button type="text">删除</el-button>
                    </template>
                </el-popconfirm>
            </div>
            </el-table-column>
        </el-table>


        <el-dialog
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
                    :size="formSize"
            >
                <el-form-item label="角色" prop="role">
                    <el-radio-group v-model="ruleForm.role">
                        <el-radio label="教师" model-value="1"></el-radio>
                        <el-radio label="学生" model-value="2"></el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="学院">
                    <el-select >
                        <el-option label="学院1" value="shanghai" />
                        <el-option label="学院2" value="beijing" />
                    </el-select>
                </el-form-item>
                <el-form-item label="专业">
                    <el-select  >
                        <el-option label="专业1" value="shanghai" />
                        <el-option label="专业2" value="beijing" />
                    </el-select>
                </el-form-item>
                <el-form-item label="姓名" prop="name">
                    <el-input v-model="ruleForm.name"></el-input>
                </el-form-item>
                <el-form-item label="学号/工号" prop="id" ref="id">
                    <el-input v-model.number="ruleForm.id"></el-input>
                </el-form-item>
                <el-form-item label="身份证号" prop="idNumber" ref="idNumber">
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
        </el-dialog>

    </div>
    </body>
</template>

<script src="../../../assets/js/usermanagement.js" type="text/javascript">

</script>

<style>
    @import "../../../assets/css/userManage.css";
</style>
