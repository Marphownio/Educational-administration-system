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
                <el-button type="primary" @click="addschool=true">添加学院</el-button>
                <el-button type="primary" @click="addmajor=true">添加专业</el-button>
            </el-form-item>
        </el-form>
        <el-table :data="tableData1" style="width: 100%; margin-bottom: 20px" row-key="number" border default-expand-all>
            <el-table-column prop="schoolId" label="学院代码" sortable width="140px" />
            <el-table-column prop="schoolName" label="学院名" sortable width="140px" />
            <el-table-column prop="introduction" label="描述信息" sortable  />
            <el-table-column  align="right" width="420px">
                <template #header>
                    <el-input v-model="search" size="small" placeholder="输入学院名称" width="100px"/>
                </template>
                <template v-slot="scope">
                    <el-button @click="editHandle1(scope.row)">编辑</el-button>
                    <el-button type="danger" @click="delHandle1(scope.row.schoolId)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-table :data="tableData2" style="width: 100%; margin-bottom: 20px" row-key="number" border default-expand-all>
            <el-table-column prop="majorId" label="专业代码" sortable width="140px" />
            <el-table-column prop="majorName" label="专业名" sortable width="140px" />
            <el-table-column prop="schoolName" label="所属学院" sortable width="140px" />
            <el-table-column prop="schoolId" label="学院代码" sortable width="140px" />
            <el-table-column prop="introduction" label="描述信息" sortable  />
            <el-table-column  align="right" width="420px">
                <template #header>
                    <el-input v-model="search" size="small" placeholder="输入学院名称" width="100px"/>
                </template>
                <template v-slot="scope">
                    <el-button @click="editHandle2(scope.row)">编辑</el-button>
                    <el-button type="danger" @click="delHandle2(scope.row.majorId)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>

    <el-dialog v-model="addschool" title="添加学院" width="600px" @close="refresh">
            <el-form
                    ref="ruleForm1"
                    :model="ruleForm1"
                    :rules="editRules1"
                    label-width="120px"
                    class="demo-ruleForm"
            >
                <el-form-item label="学院代码" prop="schoolId">
                    <el-input v-model="ruleForm1.schoolId"></el-input>
                </el-form-item>
                <el-form-item label="学院名" prop="schoolName">
                    <el-input v-model="ruleForm1.schoolName"></el-input>
                </el-form-item>
                <el-form-item label="描述信息" prop="introduction">
                    <el-input v-model="ruleForm1.introduction"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitaddschool" >添加</el-button>
                </el-form-item>
            </el-form>
    </el-dialog>
    <el-dialog v-model="updateschool" title="编辑学院信息" width="600px" @close="refresh">
        <el-form
                ref="ruleForm1"
                :model="ruleForm1"
                :rules="editRules1"
                label-width="120px"
                class="demo-ruleForm"
        >
            <el-form-item label="学院代码" prop="schoolId">
                <el-input v-model="ruleForm1.schoolId"></el-input>
            </el-form-item>
            <el-form-item label="学院名" prop="schoolName">
                <el-input v-model="ruleForm1.schoolName"></el-input>
            </el-form-item>
            <el-form-item label="描述信息" prop="introduction">
                <el-input v-model="ruleForm1.introduction"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitupdateschool" >修改</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>

    <el-dialog v-model="addmajor" title="添加专业" width="600px" @close="refresh">
        <el-form
                ref="ruleForm2"
                :model="ruleForm2"
                :rules="editRules2"
                label-width="120px"
                class="demo-ruleForm"
        >
            <el-form-item label="专业代码" prop="majorId">
                <el-input v-model="ruleForm2.majorId"></el-input>
            </el-form-item>
            <el-form-item label="所属学院" prop="schoolId">
                <el-select v-model="ruleForm2.schoolId" placeholder="所属学院" >
                    <el-option v-for="item in depss" :key="item.schoolName" :label="item.schoolName" :value="item.schoolId" />
                </el-select>
            </el-form-item>
            <el-form-item label="专业名" prop="majorName">
                <el-input v-model="ruleForm2.majorName"></el-input>
            </el-form-item>
            <el-form-item label="描述信息" prop="introduction">
                <el-input v-model="ruleForm2.introduction"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitaddmajor" >添加</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
    <el-dialog v-model="updatemajor" title="编辑专业信息" width="600px" @close="refresh">
        <el-form
                ref="ruleForm2"
                :model="ruleForm2"
                :rules="editRules2"
                label-width="120px"
                class="demo-ruleForm"
        >
            <el-form-item label="专业代码" prop="majorId">
                <el-input v-model="ruleForm2.majorId"></el-input>
            </el-form-item>
            <el-form-item label="所属学院" prop="schoolId">
                <el-select v-model="ruleForm2.schoolId" placeholder="所属学院" >
                    <el-option v-for="item in depss" :key="item.schoolName" :label="item.schoolName" :value="item.schoolId" />
                </el-select>
            </el-form-item>
            <el-form-item label="专业名" prop="majorName">
                <el-input v-model="ruleForm2.majorName"></el-input>
            </el-form-item>
            <el-form-item label="描述信息" prop="introduction">
                <el-input v-model="ruleForm2.introduction"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitupdatemajor" >修改</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
    </body>
</template>
<script  src="../../../assets/js/majorManage.js"></script>

<style  scoped >
    @import "../../../assets/css/majorManage.css";
</style>
