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
            Userform:{
                userId:'',
                password:'',
                role:'',
                username:'',
                idNumber:'',
                phoneNumber:'',
                email:'',
                status:'',
                school:'',
                major:'',
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
        getUserinfo(){
            request.get("/user/info").then(res=>{
                console.log(res);
                this.PersonalData.User=res;
                this.PersonalData.name=res.username;
                this.PersonalData.id=res.userId;
                if(res.role=="STUDENT"){
                    this.PersonalData.role="学生";
                }
                if(res.role=="TEACHER"){
                    this.PersonalData.role="教师";
                }
                this.PersonalData.college=res.school.schoolName;
                this.PersonalData.major=res.major.majorName;
                this.PersonalData.idNumber=res.idNumber;
                this.PersonalData.phoneNumber=res.phoneNumber;
                this.PersonalData.email=res.email;
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
                    this.Userform.phoneNumber=this.ruleForm1.newphonenumber;
                    this.Userform.userId=this.PersonalData.User.userId;
                    this.Userform.password=this.PersonalData.User.password;
                    this.Userform.role=this.PersonalData.User.role;
                    this.Userform.username=this.PersonalData.User.username;
                    this.Userform.idNumber=this.PersonalData.User.idNumber;
                    this.Userform.status=this.PersonalData.User.status;
                    this.Userform.email=this.PersonalData.User.email;
                    this.Userform.school=JSON.parse(this.PersonalData.User.school.schoolId);
                    this.Userform.major=JSON.parse(this.PersonalData.User.major.majorId);
                    let newPhoneformData = new FormData();
                    for(let key in this.Userform) {
                        newPhoneformData.append(key,this.Userform[key]);
                    }
                    // console.log(newPhoneformData);
                    request.put("/user/update",newPhoneformData)
                      .then(function (response) {
                              alert('手机号码修改成功!');
                              return true;
                          // }
                      },function (err) {
                          // console.log(err);
                          alert("手机号码修改失败! 请再次尝试");
                      })
                    this.getUserinfo();
                }
                else {
                    return false;
                }
            })
        },
        submit_check2() {
            this.$refs.ruleForm2.validate((valid) => {
                if (valid) {
                    this.Userform.email=this.ruleForm2.newemailaddress;
                    this.Userform.userId=this.PersonalData.User.userId;
                    this.Userform.password=this.PersonalData.User.password;
                    this.Userform.role=this.PersonalData.User.role;
                    this.Userform.username=this.PersonalData.User.username;
                    this.Userform.idNumber=this.PersonalData.User.idNumber;
                    this.Userform.status=this.PersonalData.User.status;
                    this.Userform.phoneNumber=this.PersonalData.User.phoneNumber;
                    this.Userform.school=JSON.parse(this.PersonalData.User.school.schoolId);
                    this.Userform.major=JSON.parse(this.PersonalData.User.major.majorId);
                    let newemailformData = new FormData();
                    for(let key in this.Userform) {
                        newemailformData.append(key,this.Userform[key]);
                    }
                    console.log(newemailformData);
                    request.put("/user/update",newemailformData)
                        .then(function (response) {
                            alert('邮箱地址修改成功!');
                            return true;
                            // }
                        },function (err) {
                            console.log(err);
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