import Nav from "@/views/inc/Nav.vue";
import request from "@/utils/request";
export default {
    name: "personal_infor",
    components:{
        Nav
    },
    data(){
        let phonecheck =(rule,value,callback)=>{
            const pat0=/^1[0-9]{10}$/;
            if (value===""){
                callback(new Error('请输入手机号码'));
                return false;
            }
            else {
                if(!pat0.test(value)){
                    callback(new Error('请输入以1开头的11位数字'));
                    return false;
                }
                else {
                    callback();
                    return true;
                }
            }
        };
        let emailcheck =(rule,value,callback)=>{
            const pat0=/^[\w\._-]+@(([\w_-]+)\.+)+\w+$/;
            if (value===""){
                callback(new Error('请输入新邮箱地址'));
                return false;
            }
            else {
                if(!pat0.test(value)){
                    callback(new Error('邮箱格式不正确'));
                    return false;
                }
                else {
                    callback();
                    return true;
                }
            }
        };
        return{
            tableData : [
                {
                    name:'',
                    id:'',
                    role:'',
                    college:'',
                    major:'',
                    idNumber:'',
                    phoneNumber: '',
                    email:'',
                    status:'',
                }
            ],
            ruleForm2:{
                newemailaddress:''
            },
            editRules2:({
                newemailaddress:[
                    {validator: emailcheck,trigger: 'blur'},
                ],
            }),
            dialogVisible2:false,
            ruleForm1:{
                newphonenumber:''
            },
            editRules1:({
                newphonenumber:[
                    {validator: phonecheck,trigger: 'blur'},
                ],
            }),
            dialogVisible1:false,
        };
    },
    created(){
        this.getUserinfo()
    },
    methods:{
        getUserinfo(){
            request.get("/user/info").then(res=>{
                console.log(res);
                // this.tableData.name=res.username;
                // this.tableData.id=res.userId;
                // this.tableData.role=res.role;
                // this.tableData.college=res.school;
                // this.tableData.major=res.major;
                // this.tableData.idNumber=res.idNumber;
                // this.tableData.phoneNumber=res.phoneNumber;
                // this.tableData.email=res.email;
                // this.tableData.status=res.status;
            })
        },
        submit_check1() {
            this.$refs.ruleForm1.validate((valid) => {
                if (valid) {
                    this.dialogVisible1=false;
                    alert('手机号码修改成功!');
                }
                else {
                    return false;
                }
            })
        },
        submit_check2() {
            this.$refs.ruleForm2.validate((valid) => {
                if (valid) {
                    this.dialogVisible2=false;
                    alert('邮箱地址修改成功!');
                }
                else {
                    return false;
                }
            })
        },
    }
}