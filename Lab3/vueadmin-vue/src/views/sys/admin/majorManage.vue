<template>
    <head>
        <meta charset="UTF-8">
        <title>学院/专业管理</title>
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
        <el-table :data="tableData" style="width: 100%; margin-bottom: 20px" row-key="id" border default-expand-all>
            <el-table-column prop="name" label="Name" sortable width="180" />
            <el-table-column prop="data" label="Data" sortable width="180" />
            <el-table-column align="right">
                <template #header>
                    <el-input v-model="search" size="small" placeholder="Type to search" />
                </template>
                <template #default="scope">
                    <el-button size="small"
                    >Edit</el-button
                    ><!--@click="handleEdit(scope.$index, scope.row)"-->
                    <el-button size="small" type="danger" >Delete</el-button> <!--@click="handleDelete(scope.$index, scope.row)"-->
                </template>
            </el-table-column>
        </el-table>
    </div>

    <el-dialog v-model="dialogVisible" title="添加" width="600px">
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
    </body>
</template>
<script src="../../../assets/js/majorManage.js"></script>

<style scoped>
    @import "../../../assets/css/majorManage.css";
</style>
