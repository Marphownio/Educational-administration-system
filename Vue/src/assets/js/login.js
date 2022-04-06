import request from "@/utils/request";
import router from "@/router";

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

                    request.post("/login",formData)
                        .then(function (response) {
                            console.log(response)
                            if(response.role===0){
                                //管理员登录
                                return that.$router.push({path: '/index_admin'});
                            }
                            if(response.role===1){
                                //教师登录
                                if(response.password.equals("fudan123456")){
                                    //重置密码
                                    return that.$router.push({path: '/resetpassword'});
                                }
                                else return that.$router.push({path: '/index_teacher'})
                            }
                            if(response.role===2){
                                //学生登录
                                if(response.password.equals("fudan123456")){
                                    //重置密码
                                    return that.$router.push({path: '/resetpassword'});
                                }
                                else return that.$router.push({path: '/index_stu'});
                            }

                        })
                        .catch(function (error) {
                            // 处理错误情况
                            alert("用户名或密码错误! 请重新输入");
                            return that.$router.push({path: '/'});
                        })
                }
                else {
                    return false;
                }
            })
        },
    }
}
