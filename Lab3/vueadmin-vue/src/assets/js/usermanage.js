import Nav from "@/views/inc/Nav.vue";

export default {
    name: "userManage",
    components:{
        Nav
    },
    data(){
        let namecheck=(rule,value,callback)=>{
            const relu = "^[a-zA-Z\u4e00-\u9fa5]+$";
            const re = new RegExp(relu);
            if (value.search(re) === -1){
                callback(new Error('姓名输入只能为中英文'));
                return false;
            } else {
                if(this.length>32){
                    callback(new Error('仅支持32字符以内输入'));
                    return false;
                }
                else {
                    return true;
                    }
                }

        };
        let idcheck=(rule,value,callback)=>{
            console.log(this.ruleForm.role);
            if(value[0]!=2||value[1]!=2){           //这里改成！==的话貌似会报错
                callback(new Error('工号/学号前两位需为22'));
                return false;
            }
            else if(isNaN(value))
            {
                callback(new Error('输入只能为数字'));
                return false;
            }
            else if(this.ruleForm.role==='1')
            {
                if(value.length!==8)
                {
                    callback(new Error('请输入8位工号'))
                    return false;
                }
                else
                    return true;
            }
            else if(this.ruleForm.role==='2')
            {
                if(value.length!==6)
                {
                    callback(new Error('请输入6位学号'))
                    return false;
                }
                else
                    return true;
            }
        };
        return{
            dialogVisible:false,
            ruleForm:{
            },
            editRules :({
                name: [
                    {
                        required: true,
                        message: '请输入姓名',
                        trigger: 'blur'
                    },
                    { validator: namecheck,trigger: 'blur'}
                ],
                id: [
                    {
                        required: true,
                        message: '请输入学号/工号',
                        trigger: 'blur',
                    },
                    { validator: idcheck,trigger: 'blur'}
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
                    {pattern:'^[\\w\\-.]+@[a-z0-9]+(-[a-z0-9]+)?(\\.[a-z0-9]+(-[a-z0-9]+)?)*\\.[a-z]{2,4}$',message:'邮箱格式不正确',trigger: 'blur'}
                ],
                role: [
                    {
                        required: true,
                        message: '请选择角色',
                        trigger: 'change',
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
                    email:'12345678901@test.cn',
                    state: 1,
                    major:'专业1',
                    college:'学院1'
                },
                {
                    role: '1',
                    name: 'test',
                    id:'000000',
                    idNumber:'123456789012345678',
                    phoneNumber:'12345678901',
                    email:'12345678901@test.cn',
                    state:3
                }
            ]
        }
    },
    created(){
        this.getUserForm()
    },

    methods:{
        getUserForm(){
            this.$axios.get("/admin/user/list").then(res=>{
                this.tableData=res.data.data;
            })
        },
        submitForm(){
            this.$refs.ruleForm.validate(valid=>{
                if(valid){
                    this.$axios.post('/admin/user/'+(this.ruleForm.id?'update':'save'),this.ruleForm)
                    .then(res=>{
                        this.$message({
                            showClose: true,
                            message: '操作成功',
                            type: 'success',
                            onClose:()=>{
                                this.getUserForm()
                            }
                            });
                        this.dialogVisible=false;
                        }
                    )
                }
                else{
                    this.$nextTick(() => {
                        this.scrollToTop(this.$refs.ruleForm.$el)
                    })
                }
            })
        },
        editHandle(id){
            this.$axios.get('/sys/menu/info/'+id).then(res=>{
                this.ruleForm=res.data.data
                this.dialogVisible=true
            })
        },
        scrollToTop (node) {
            const ChildHasError = Array.from(node.querySelectorAll('.is-error'))
            if (!ChildHasError.length) throw new Error('有错误，但是找不到错误位置')
        }
    }
}
