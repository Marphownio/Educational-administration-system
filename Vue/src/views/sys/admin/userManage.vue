<template xmlns="http://www.w3.org/1999/html">
    <head>
        <meta charset="UTF-8">
        <title>用户管理</title>
    </head>

    <body>

    <Nav></Nav>

    <div class="void">
        <span></span>
    </div>

    <div class="showlistw">
        <el-form :inline="true" >
            <el-form-item>
                <el-button type="primary" @click="dialogVisible1=true">添加用户</el-button>
                <el-upload
                        class="upload-demo"
                        action="http://localhost/api/user/batchimport"
                        :on-success="showwrongmessage"
                        method="post"
                        enctype=“multipart/form-data”
                        accept=".csv"
                        >
                    <el-button type="primary" style="margin-left: 20px;margin-right: 20px;margin-top: 10px">通过csv文件添加</el-button>
                </el-upload>
            </el-form-item>
        </el-form>
        <el-table id="eltable" :data="tableData" >
            <el-table-column fixed prop="userId" label="学号/工号" width="120px" sortable/>
            <el-table-column prop="role" label="身份" width="120px" sortable/>
            <el-table-column prop="username" label="姓名" width="120px" sortable/>
            <el-table-column v-slot="scope" prop="status" label="状态" width="120px" sortable>
                <el-tag v-if="scope.row.status===true&&scope.row.role==='STUDENT'" type="success">在读</el-tag>
                <el-tag v-if="scope.row.status===true&&scope.row.role==='TEACHER'" type="success">在岗</el-tag>
                <el-tag v-if="scope.row.status===false&&scope.row.role==='STUDENT'" type="info" >已毕业</el-tag>
                <el-tag v-if="scope.row.status===false&&scope.row.role==='TEACHER'" type="info">已离职</el-tag>
            </el-table-column>
            <el-table-column prop="schoolName" label="学院" width="120px" sortable/>
            <el-table-column prop="schoolId" label="学院代码" sortable/>
            <el-table-column prop="majorName" label="专业" width="120px" sortable/>
            <el-table-column prop="majorId" label="专业代码" sortable/>
            <el-table-column prop="idNumber" label="身份证号" width="180px" sortable/>
            <el-table-column prop="phoneNumber" label="手机号" width="120px" sortable/>
            <el-table-column prop="email" label="邮箱" width="180px" sortable/>
            <el-table-column v-slot="scope"  fixed="right" prop="icon" label="操作" width="170px">
            <div>
                <el-button @click="editHandle(scope.row)">编辑</el-button>
                <el-button type="danger" @click="delHandle(scope.row.userId)">删除</el-button>
            </div>
            </el-table-column>
        </el-table>


        <el-dialog
                v-model="dialogVisible1"
                title="添加用户"
                width="600px"
                @close="refresh()"
        >
            <el-form
                    ref="ruleForm"
                    :model="ruleForm"
                    :rules="editRules"
                    label-width="120px"
                    class="demo-ruleForm"
            >
                <el-form-item label="角色" prop="role">
                    <el-radio-group v-model="ruleForm.role">
                        <el-radio label=TEACHER model-value="TEACHER">教师</el-radio>
                        <el-radio label=STUDENT model-value="STUDENT">学生</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="姓名" prop="username">
                    <el-input v-model="ruleForm.username"></el-input>
                </el-form-item>
                <el-form-item label="学号/工号" prop="userId" ref="userId">
                    <el-input v-model="ruleForm.userId"></el-input>
                </el-form-item>
                <el-form-item label="身份证号" prop="idNumber" ref="idNumber">
                    <el-input v-model="ruleForm.idNumber"></el-input>
                </el-form-item>
                <el-form-item label="学院" prop="schoolId" >
                    <el-select v-model="ruleForm.schoolId" placeholder="选择学院" @change="getMajor">
                        <el-option v-for="item in schooldata" :key="item.schoolName" :label="item.schoolName" :value="item.schoolId" />
                    </el-select>
                </el-form-item>
                <el-form-item label="专业" prop="majorId">
                    <el-select  v-model="ruleForm.majorId" placeholder="选择专业">
                        <el-option v-for="item in majordata" :key="item.majorName" :label="item.majorName" :value="item.majorId" />
                    </el-select>
                </el-form-item>
                <el-form-item label="是否在校/在岗" prop="status">
                    <el-radio-group v-model="ruleForm.status">
                        <el-radio :label=true :model-value=true>是</el-radio>
                        <el-radio :label=false :model-value=false>否</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="手机号" prop="phoneNumber">
                    <el-input v-model="ruleForm.phoneNumber"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                <el-input v-model="ruleForm.email"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="submitForm1" >添加</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog
                v-model="dialogVisible2"
                title="编辑用户"
                width="600px"
                @close="refresh()"
        >
            <el-form
                    ref="ruleForm"
                    :model="ruleForm"
                    :rules="editRules"
                    label-width="120px"
                    class="demo-ruleForm"
            >
                <el-form-item label="角色" prop="role" >
                    <el-radio-group v-model="ruleForm.role" style="pointer-events: none">
                        <el-radio label=TEACHER model-value="TEACHER" >教师</el-radio>
                        <el-radio label=STUDENT model-value="STUDENT" >学生</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="姓名" prop="username">
                    <el-input v-model="ruleForm.username"></el-input>
                </el-form-item>
                <el-form-item label="学号/工号" prop="userId" ref="userId">
                    <el-input v-model="ruleForm.userId" readonly></el-input>
                </el-form-item>
                <el-form-item label="身份证号" prop="idNumber" ref="idNumber">
                    <el-input v-model="ruleForm.idNumber"></el-input>
                </el-form-item>
                <el-form-item label="学院" prop="schoolId" >
                    <el-select v-model="ruleForm.schoolId" placeholder="选择学院" @change="getMajor">
                        <el-option v-for="item in schooldata" :key="item.schoolName" :label="item.schoolName" :value="item.schoolId" />
                    </el-select>
                </el-form-item>
                <el-form-item label="专业" prop="majorId" >
                    <el-select  v-model="ruleForm.majorId" placeholder="选择专业">
                        <el-option v-for="item in majordata" :key="item.majorName" :label="item.majorName" :value="item.majorId" />
                    </el-select>
                </el-form-item>
                <el-form-item label="是否在校/在岗" prop="status">
                    <el-radio-group v-model="ruleForm.status">
                        <el-radio :label=true :model-value=true>是</el-radio>
                        <el-radio :label=false :model-value=false>否</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="手机号" prop="phoneNumber">
                    <el-input v-model="ruleForm.phoneNumber"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="ruleForm.email"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="submitForm2" >修改</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog
                v-model="dialogVisible3"
                title="错误信息显示"
                width="1000px"
                @close="fresh()"
                id="dialog3"
        >
            <textarea v-model="wrongmessage" readonly style="font-size: 15px"></textarea>
            <el-button type="primary" @click="exporttxt" >导出为txt</el-button>
        </el-dialog>

    </div>
    </body>
</template>

<script src="../../../assets/js/usermanage.js" type="text/javascript">

</script>

<style src="../../../assets/css/userManage.css" scoped>
</style>
