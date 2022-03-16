<template>
    <head>
        <meta charset="UTF-8">
        <title>教师/学生信息录入</title>
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
        <el-table :data="tableData" border stripe style="width: 100%">
            <el-table-column prop="role" label="角色" width="100px" />
            <el-table-column prop="name" label="姓名" width="100px" />
            <el-table-column prop="id" label="学号/工号" width="120px"/>
            <el-table-column prop="idNumber" label="身份证号" />
            <el-table-column prop="phoneNumber" label="手机号" width="120px"/>
            <el-table-column prop="email" label="邮箱" />
            <el-table-column prop="icon" label="操作" >
            <div>
                <el-button type="text">编辑</el-button>
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
                    ref="ruleFormRef"
                    :model="ruleForm"
                    :rules="editRules"
                    label-width="120px"
                    class="demo-ruleForm"
                    :size="formSize"
            >
                <el-form-item label="角色" prop="role">
                    <el-radio-group v-model="ruleForm.role">
                        <el-radio label="教师"></el-radio>
                        <el-radio label="学生"></el-radio>
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
                    <el-input v-model.number="ruleForm.phoneNumber"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                <el-input v-model="ruleForm.email"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="submitForm(ruleFormRef)" >添加</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

    </div>
    </body>
</template>

<script>
    import Nav from "@/views/inc/Nav.vue";
    export default {
        name: "userManage",
        components:{
            Nav
        },
        data(){
            return{
                dialogVisible:false,
                ruleForm:{

                },
                editRules :({
                    name: [
                        { required: true, message: '请输入姓名', trigger: 'blur' },
                    ],
                    id: [
                        {
                            required: true,
                            message: '请输入学号/工号',
                            trigger: 'change',
                        },
                        { type:'number',message:'输入只能为数字'}
                    ],
                    idNumber: [
                        {
                            required: true,
                            message: '请输入身份证号',
                            trigger: 'change',
                        },
                    ],
                    role: [
                        {
                            required: true,
                            message: '请选择角色',
                            trigger: 'blur',
                        },
                    ]
                }),
                tableData : [
                    {
                        role: '管理员',
                        name: 'admin',
                        id:'12345678901',
                        idNumber:'123456789012345678',
                        phoneNumber:'12345678901',
                        email:'12345678901@fudan.edu.cn'
                    }
                ]
            }
        },
        created(){

        },

        methods:{
            getUserForm(){
                this.$axios.get("/sys/menu/list").then(res=>{
                    this.tableData=res.data.data;
                })
            },
            submitForm(formName){
                this.$refs[formName].validate((valid)=>{
                    if(valid){
                        this.$axios.post('/sys/menu/'+(this.ruleForm.id?'update':'save'),this.ruleForm)
                        .then(res=>{
                            this.$message({
                                showClose: true,
                                message: '添加成功',
                                type: 'success',
                                onClose:()=>{
                                    this.getUserForm()
                                }
                            });
                            }
                        )
                    }
                })
            }
        }
    }

</script>

<style scoped>
    @import "../assets/css/userManage.css";


</style>
