import Nav from "@/views/inc/Nav.vue";
import Papa from "papaparse";
import request from "@/utils/request";

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
            if(this.dialogVisible1===true)
            {
                if(value[0]!=='2'||value[1]!=='2')
            {
                callback(new Error('工号/学号前两位需为22'));
                return false;
            }
            else if(isNaN(value))
            {
                callback(new Error('输入只能为数字'));
                return false;
            }
            else if(this.ruleForm.role==='TEACHER')
            {
                if(value.length!==8)
                {
                    callback(new Error('请输入8位工号'))
                    return false;
                }
                else
                    return true;
            }
            else if(this.ruleForm.role==='STUDENT')
            {
                if(value.length!==6)
                {
                    callback(new Error('请输入6位学号'))
                    return false;
                }
                else
                    return true;
            }
        }
            else return true;
        };
        return{
            dialogVisible1:false,
            dialogVisible2:false,
            schooldata:[],
            majordata:[],
            ruleForm:{
                userId:'',
                role:'',
                school:'',
                major:'',
                username:'',
                idNumber:'',
                phoneNumber: '',
                email:'',
                status:'',
            },
            editRules :({
                username: [
                    {
                        required: true,
                        message: '请输入姓名',
                        trigger: 'blur'
                    },
                    { validator: namecheck,trigger: 'blur'}
                ],
                userId: [
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
                ],
                status: [
                    {
                        required: true,
                        message: '请选择状态',
                        trigger: 'change',
                    },
                ],
            }),
            tableData : [
                {
                    userId:'',
                    role:'',
                    school:'',
                    major:'',
                    username:'',
                    idNumber:'',
                    phoneNumber: '',
                    email:'',
                    status:'',
                }
            ]
        }
    },
    mounted() {
        this.getSchool();
        this.getMajor();
    },
    created(){
        this.getUserForm();
    },
    methods:{
        refresh(){
          this.ruleForm= {};
        },
        getSchool:function(){
            request.get("/school/list").then(res=>{
                this.schooldata= res;
                this.ruleForm.school=res[0];
            })
            this.ruleForm.school=this.schooldata[0];
        },
        getMajor:function(){
            request.get("/major/list").then(res=>{
                this.majordata= res;
                this.ruleForm.major=res[0];
            })

        },
        getUserForm(){
            request.get("/user/list").then(res=>{
                this.tableData=res;
                for(let i=0;i<Object.keys(res).length;i++)
                {
                    if(res[i].school!==null)
                    {
                        this.tableData[i].school=res[i].school.schoolName;
                        this.tableData[i].major=res[i].major.majorName;
                    }
                }
            })
        },
        submitForm1(){
            console.log(this.ruleForm);
            this.$refs.ruleForm.validate(valid=>{
                if(valid){
                    let params = new URLSearchParams();
                    params.append('userId', this.ruleForm.userId);
                    params.append('role', this.ruleForm.role);
                    params.append('school', JSON.parse(this.ruleForm.school));
                    params.append('major', JSON.parse(this.ruleForm.major));
                    params.append('idNumber', this.ruleForm.idNumber);
                    params.append('username', this.ruleForm.username);
                    params.append('phoneNumber', this.ruleForm.phoneNumber);
                    params.append('email', this.ruleForm.email);
                    params.append('status', this.ruleForm.status);
                    this.$axios({
                        method: 'post',
                        url:'/api/user/add',
                        data:params,

                    }).then(res=>{
                        if(res.data==='EXIST')
                        {
                            this.$message({
                            showClose: true,
                            message: '该用户已存在',
                            type: 'fail',
                            onClose:()=>{
                                this.getUserForm()
                            }
                            });
                        this.dialogVisible1=false;
                        }
                        if(res.data==='SUCCESS')
                        {
                            this.$message({
                                showClose: true,
                                message: '操作成功',
                                type: 'success',
                                onClose:()=>{
                                    this.getUserForm()
                                }
                            });
                            this.dialogVisible1=false;
                        }
                    })
                }
                else{
                    this.$nextTick(() => {
                       this.scrollToTop(this.$refs.ruleForm.$el)
                    })
                }
            })
        },
        submitForm2(){
            this.$refs.ruleForm.validate(valid=>{
                if(valid){
                    let params = new URLSearchParams();
                    params.append('userId', this.ruleForm.userId);
                    params.append('role', this.ruleForm.role);
                    params.append('school', JSON.parse(this.ruleForm.school));
                    params.append('major', JSON.parse(this.ruleForm.major));
                    params.append('idNumber', this.ruleForm.idNumber);
                    params.append('username', this.ruleForm.username);
                    params.append('phoneNumber', this.ruleForm.phoneNumber);
                    params.append('email', this.ruleForm.email);
                    params.append('status', this.ruleForm.status);
                    this.$axios({
                        method: 'post',
                        url:'/api/user/update',
                        data:params,

                    }).then(res=>{
                        if(res.data==='EXIST')
                        {
                            this.$message({
                                showClose: true,
                                message: '该用户已存在',
                                type: 'fail',
                                onClose:()=>{
                                    this.getUserForm()
                                }
                            });
                            this.dialogVisible2=false;
                        }
                        if(res.data==='SUCCESS')
                        {
                            this.$message({
                                showClose: true,
                                message: '操作成功',
                                type: 'success',
                                onClose:()=>{
                                    this.getUserForm()
                                }
                            });
                            this.dialogVisible2=false;
                        }
                    })
                }
                else{
                    this.$nextTick(() => {
                        this.scrollToTop(this.$refs.ruleForm.$el)
                    })
                }
            })
        },
        handleChange(file) {
            this.fileTemp = file.raw
            if (this.fileTemp) {
                if ((this.fileTemp.type === '.csv') || (this.fileTemp.type === 'application/vnd.ms-excel')) {
                    this.importcsv(file.raw)
                } else {
                    this.$message({
                        type: 'warning',
                        message: '附件格式错误，请删除后重新上传！'
                    })
                }
            } else {
                this.$message({
                    type: 'warning',
                    message: '请上传附件！'
                })
            }
        },
        importcsv (obj) {
            Papa.parse(obj, {
                complete (results) {
                    console.log(results)//调试查看csv文件的数据
                    let data = []
                    //遍历csv文件中的数据，存放到data中
                    for (let i = 0; i < results.data.length; i++) {
                        let obj = {}
                        obj.number = results.data[i][0]
                        obj.name = results.data[i][1]
                        obj.nameRemark = results.data[i][2]
                        obj.index = results.data[i][3]
                        data.push(obj)
                        this.$axios.post("/user/add",this.obj).then(res=>{
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
                    data.splice(0, 1)//将数组第一位的表格名称去除
                    console.log('data', data)
                    this.tableData = data//将数据放入要展示的表格中
                }
            })
        },
        editHandle(obj){
            this.dialogVisible2=true;
            this.ruleForm=obj;
            console.log(this.ruleForm);
            console.log(this.ruleForm.status);
        },
        delHandle(id){
            this.$axios.post("/user/"+id).then(res=> {
                this.$message({
                    showClose: true,
                    message: '操作成功',
                    type: 'success',
                    onClose: () => {
                        this.getUserForm()
                    }
                })
            })
        },
        scrollToTop (node) {
            const ChildHasError = Array.from(node.querySelectorAll('.is-error'))
            if (!ChildHasError.length) throw new Error('有错误，但是找不到错误位置')
        }
    }
}
