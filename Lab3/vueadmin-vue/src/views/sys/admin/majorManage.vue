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
        <el-table :data="tableData" style="width: 100%; margin-bottom: 20px" row-key="number" border default-expand-all>
            <el-table-column prop="number" label="编号" sortable width="180" />
            <el-table-column prop="college" label="学院" sortable width="180" />
            <el-table-column prop="major" label="专业" sortable width="180" />
            <el-table-column prop="data" label="描述信息" sortable width="180" />
            <el-table-column align="right">
                <template #header>
                    <el-input v-model="search" size="small" placeholder="输入学院/专业名称" />
                </template>
                <template #default="scope">
                    <el-button>编辑</el-button> <!--@click="handleEdit(scope.$index, scope.row)"-->
                    <el-button type="danger" >删除</el-button> <!--@click="handleDelete(scope.$index, scope.row)"-->
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
            >
                <el-form-item label="学院名" prop="college">
                    <el-input v-model="ruleForm.college"></el-input>
                </el-form-item>
                <el-form-item label="专业名" prop="major">
                    <el-input v-model="ruleForm.major"></el-input>
                </el-form-item>
                <el-form-item label="描述信息" prop="message">
                    <el-input v-model="ruleForm.message"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm" >添加</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </body>
</template>
<script  src="../../../assets/js/majorManage.js"></script>

<style  scoped >
    @import "../../../assets/css/majorManage.css";
</style>
