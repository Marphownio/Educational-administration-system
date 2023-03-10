import Nav from "@/views/inc/Nav.vue";
import request from "@/utils/request";
import ALERTMSG from "@/assets/js/alert";
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
            Userform:{
                userId:'',
                password:'',
                role:'',
                username:'',
                idNumber:'',
                phoneNumber:'',
                email:'',
                status:'',
                school:{},
                major:{},
            },
            PersonalData :{
                    User:'',
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
            ruleForm1:{
                newphonenumber:''
            },
            editRules1:({
                newphonenumber:[
                    {validator: phonecheck,trigger: 'blur'},
                ],
            }),
            dialogVisible1:false,
            ruleForm2:{
                newemailaddress:'',
            },
            editRules2:({
                newemailaddress:[
                    {validator: emailcheck,trigger: 'blur'},
                ],
            }),
            dialogVisible2:false,
        };
    },
    created(){
        this.getUserinfo()
    },
    methods:{
        shownewphone(){
            this.dialogVisible1=true;
            if(this.PersonalData.phoneNumber==="暂无电话号码"){
                this.ruleForm1.newphonenumber="";
            }
            else{
                this.ruleForm1.newphonenumber=this.PersonalData.phoneNumber;
            }
        },
        showemail(){
            this.dialogVisible2=true;
            if(this.PersonalData.email==="暂无邮箱地址"){
                this.ruleForm2.newemailaddress="";
            }
            else{
                this.ruleForm2.newemailaddress=this.PersonalData.email;
            }
        },
        getUserinfo(){
            request.get("/user/info").then(res=>{
                // console.log(res);
                this.PersonalData.User=res;
                this.PersonalData.name=res.username;
                this.PersonalData.id=res.userId;
                if(res.role==="STUDENT"){
                    this.PersonalData.role="学生";
                }
                if(res.role==="TEACHER"){
                    this.PersonalData.role="教师";
                }
                this.PersonalData.college=res.school.schoolName;
                this.PersonalData.major=res.major.majorName;
                this.PersonalData.idNumber=res.idNumber;
                if(res.phoneNumber==="NULL"||res.phoneNumber==="null"||res.phoneNumber===""||res.phoneNumber===null){
                    this.PersonalData.phoneNumber="暂无电话号码"
                }
                else {
                    this.PersonalData.phoneNumber=res.phoneNumber;
                }
                if(res.email==="NULL"||res.email==="null"||res.email===""||res.email===null){
                    this.PersonalData.email="暂无邮箱地址"
                }
                else {
                    this.PersonalData.email=res.email;
                }
                if(res.status===true){
                    if(res.role==="STUDENT"){
                        this.PersonalData.status="在读";
                    }
                    else{
                        this.PersonalData.status="在职";
                    }
                }
                if(res.status===false){
                    if(res.role==="STUDENT"){
                        this.PersonalData.status="正常毕业";
                    }
                    else{
                        this.PersonalData.status="离职";
                    }
                }
            })
        },
        submit_check1() {
            this.$refs.ruleForm1.validate((valid) => {
                if (valid) {
                    const that=this;
                    this.Userform.phoneNumber=this.ruleForm1.newphonenumber;
                    this.Userform.userId=this.PersonalData.User.userId;
                    this.Userform.password=this.PersonalData.User.password;
                    this.Userform.role=this.PersonalData.User.role;
                    this.Userform.username=this.PersonalData.User.username;
                    this.Userform.idNumber=this.PersonalData.User.idNumber;
                    this.Userform.status=this.PersonalData.User.status;
                    this.Userform.email=this.PersonalData.User.email;
                    this.Userform.school.schoolId=String(this.PersonalData.User.school.schoolId);
                    this.Userform.major.majorId=String(this.PersonalData.User.major.majorId);
                    request.put("/user/update",this.Userform)
                      .then(function (response) {
                          ALERTMSG.show(that,true,"手机号码修改成功!","success");
                          that.getUserinfo();
                              return true;
                      },function (err) {
                          ALERTMSG.show(that,true,"手机号码修改失败! 请再次尝试","error");
                          that.getUserinfo();
                      });
                }
                else {
                    return false;
                }
            })
        },
        submit_check2() {
            this.$refs.ruleForm2.validate((valid) => {
                if (valid) {
                    const that =this;
                    this.Userform.email=this.ruleForm2.newemailaddress;
                    this.Userform.userId=this.PersonalData.User.userId;
                    this.Userform.password=this.PersonalData.User.password;
                    this.Userform.role=this.PersonalData.User.role;
                    this.Userform.username=this.PersonalData.User.username;
                    this.Userform.idNumber=this.PersonalData.User.idNumber;
                    this.Userform.status=this.PersonalData.User.status;
                    this.Userform.phoneNumber=this.PersonalData.User.phoneNumber;
                    this.Userform.school.schoolId=String(this.PersonalData.User.school.schoolId);
                    this.Userform.major.majorId=String(this.PersonalData.User.major.majorId);
                    let newemailformData = new FormData();
                    request.put("/user/update",this.Userform)
                        .then(function (response) {
                            ALERTMSG.show(that,true,"邮箱地址修改成功!","success");
                            that.getUserinfo();
                            return true;
                        },function (err) {
                            ALERTMSG.show(that,true,"邮箱地址修改失败! 请再次尝试!","error");
                            that.getUserinfo();
                        })

                }
                else {
                    return false;
                }
            })
        },
    }
}