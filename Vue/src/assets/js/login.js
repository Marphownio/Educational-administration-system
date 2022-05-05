import request from "@/utils/request";
import tokenmanage from "@/utils/Tokenmanage";
import ALERTMSG from "@/assets/js/alert";

export default {
    name: "login",
    data(){
        return{
            loginruleForm:{
                loginid:'',
                loginpw:''
            },
            logineditRules :({
                loginid: [
                    {required: true, message: '请输入学/工号', trigger: 'blur' },
                ],
                loginpw:[
                    {required: true, message: '请输入密码', trigger: 'blur',},
                ],
            }),
        }
    },
    created(){
    },
    methods: {
        loginfunc() {
            const that=this;
            this.$refs.loginruleForm.validate((valid) => {
                if (valid) {
                    let formData = new FormData();
                    for(let key in this.loginruleForm) {
                        formData.append(key,this.loginruleForm[key]);
                    }
                    request.post("/user/login",formData)
                        .then(function (response) {
                            if(response==="FAILED"){
                                //登录失败
                                ALERTMSG.show(that,true,"用户名或密码错误! 请重新输入!","error")
                                return that.$router.push({path: '/'});
                            }
                            else if(response==="SUCCESS_LOGIN_ADMIN"){
                                //管理员登录
                                tokenmanage.set("token","1");
                                return that.$router.push({path: '/index_admin'});
                            }
                            else if(response==="SUCCESS_LOGIN_TEACHER_RESETPASSWORD") {
                                //教师登录首次
                                tokenmanage.set("token","2");
                                return that.$router.push({path: '/resetpassword'});
                            }
                            else if(response==="SUCCESS_LOGIN_TEACHER") {
                                //教师登录
                                tokenmanage.set("token","2");
                                return that.$router.push({path: '/index_teacher'});
                            }
                            else if(response==="SUCCESS_LOGIN_STUDENT_RESETPASSWORD") {
                                //学生登录首次
                                tokenmanage.set("token","3");
                                return that.$router.push({path: '/resetpassword'});
                            }
                            else if(response==="SUCCESS_LOGIN_STUDENT") {
                                //学生登录
                                tokenmanage.set("token","3");
                                return that.$router.push({path: '/index_stu'});
                            }
                        })
                }
                else {
                    return false;
                }
            })
        },
    }
}
