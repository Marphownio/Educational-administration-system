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
            PersonalData :{
                    name:'',
                    id:'',
                    role:'',
                    college:'',
                    major:'',
                    idNumber:'',
                    phoneNumber: '',
                    email:'',
                    status:'',
                },
            ruleForm2:{
                userId:'',
                role:'',
                school:'',
                major:'',
                idNumber:'',
                username:'',
                phoneNumber:'',
                newemailaddress:'',
                status:''
            },
            editRules2:({
                newemailaddress:[
                    {validator: emailcheck,trigger: 'blur'},
                ],
            }),
            dialogVisible2:false,
            ruleForm1:{
                    userId:'',
                    role:'',
                    school:'',
                    major:'',
                    idNumber:'',
                    username:'',
                    newphonenumber:'',
                    email:'',
                    status:''
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
                this.PersonalData=res;
            })
        },
        submit_check1() {
            this.$refs.ruleForm1.validate((valid) => {
                if (valid) {
                    this.ruleForm1.userId=this.PersonalData.id;
                    this.ruleForm1.role=this.PersonalData.role;
                    this.ruleForm1.school=this.PersonalData.college;
                    this.ruleForm1.major=this.PersonalData.major;
                    this.ruleForm1.idNumber=this.PersonalData.idNumber;
                    this.ruleForm1.username=this.PersonalData.name;
                    this.ruleForm1.email=this.PersonalData.email;
                    this.ruleForm1.status=this.PersonalData.status;
                    request.put("/user/update",this.ruleForm1)
                      .then(function (response) {
                          if(response==="FAILED_UPDATE"){
                              alert("手机号码修改失败! 请再次尝试");
                              return false;
                          }
                          else if(response==="SUCCESS_UPDATE"){
                              this.dialogVisible1=false;
                              alert('手机号码修改成功!');
                              return true;
                          }
                      },function (err) {
                          console.log(err);
                          alert("手机号码修改失败! 请再次尝试");
                      })
                }
                else {
                    return false;
                }
            })
        },
        submit_check2() {
            this.$refs.ruleForm2.validate((valid) => {
                if (valid) {
                    this.ruleForm2.userId=this.PersonalData.id;
                    this.ruleForm2.role=this.PersonalData.role;
                    this.ruleForm2.school=this.PersonalData.college;
                    this.ruleForm2.major=this.PersonalData.major;
                    this.ruleForm2.idNumber=this.PersonalData.idNumber;
                    this.ruleForm2.username=this.PersonalData.name;
                    this.ruleForm2.phoneNumber=this.PersonalData.phoneNumber;
                    this.ruleForm2.status=this.PersonalData.status;
                    console.log(this.ruleForm2);
                    request.put("/user/update",this.ruleForm2)
                        .then(function (response) {
                            if(response==="FAILED_UPDATE"){
                                alert("邮箱地址修改失败! 请再次尝试");
                                return false;
                            }
                            else if(response==="SUCCESS_UPDATE"){
                                this.dialogVisible1=false;
                                alert('邮箱地址修改成功!');
                                return true;
                            }
                        }, function (err) {
                                alert("邮箱地址修改失败! 请再次尝试");
                        })
                }
                else {
                    return false;
                }
            })
        },
    }
}