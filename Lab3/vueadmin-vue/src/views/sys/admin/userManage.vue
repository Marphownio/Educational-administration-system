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
                        {pattern:'^[a-zA-Z\u4e00-\u9fa5]+$',message: '输入只能为中英文',trigger: 'change'}
                    ],
                    id: [
                        {
                            required: true,
                            message: '请输入学号/工号',
                            trigger: 'blur',
                        },
                        { type:'number',message:'输入只能为数字',trigger: 'change'}
                    ],
                    idNumber: [
                        {
                            required: true,
                            message: '请输入身份证号',
                            trigger: 'blur',
                        },
                        {pattern:'/(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)/',message:'输入格式不正确，请检查输入',trigger: 'blur'}
                    ],
                    phoneNumber:[
                        {pattern:'^1[0-9]{10}$',message:'请输入以1开头的11位数字',trigger:'blur',}
                    ],
                    email:[
                        {pattern:'/^([a-zA-Z0-9]+[-_\\.]?)+@[a-zA-Z0-9]+\\.[a-z]+$/',message:'邮箱格式不正确，请重新输入',trigger:'blur',}
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
                        role: '0',
                        name: 'admin',
                        id:'000000',
                        idNumber:'123456789012345678',
                        phoneNumber:'12345678901',
                        email:'12345678901@test.cn'
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
            submitForm(){
                this.$refs.ruleForm.validate(valid=>{
                    if(valid){
                        //this.$axios.post('/sys/menu/'+(this.ruleForm.id?'update':'save'),this.ruleForm)
                        //.then(res=>{
                            this.$message({
                                showClose: true,
                                message: '添加成功',
                                type: 'success',
                                onClose:()=>{
                                    this.getUserForm()
                                }
                            //})
                            }
                        )
                    }
                    else{
                        this.$nextTick(() => {
                            this.scrollToTop(this.$refs[formName].$el)
                        })
                    }
                })
            },
            nameCheck () {
                const relu = "^[a-zA-Z\u4e00-\u9fa5]+$";
                const re = new RegExp(relu);
                if (this.value.search(re) !== -1){
                } else {
                    message:'输入只能为中英文！'
                }
            },
            editHandle(id){
                this.$axios.get('/sys/menu/info/'+id).then(res=>{
                    this.ruleForm=res.data.data;
                    this.dialogVisible=true;
                })
            },
            scrollToTop (node) {
                const ChildHasError = Array.from(node.querySelectorAll('.is-error'))
                if (!ChildHasError.length) throw new Error('有错误，但是找不到错误位置')
            }
        }
    }

</script>

<style>
    @import "../../../assets/css/userManage.css";
</style>
