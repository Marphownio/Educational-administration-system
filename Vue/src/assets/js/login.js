import request from "@/utils/request";

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
            this.$refs.loginruleForm.validate((valid) => {
                if (valid) {

                    let formData = new FormData();
                    for(let key in this.loginruleForm) {
                        formData.append(key,this.loginruleForm[key]);
                        console.log(formData.get(key));

                        document.write(key + "=" +  this.loginruleForm[key] + "&nbsp&nbsp&nbsp&nbsp"); // 测试用，可删除

                    }

                    request.post("/login",formData)
                        .then(function (response) {
                            // 处理成功情况

                            console.log(response);

                        })
                        .catch(function (error) {
                            // 处理错误情况

                            console.log(error);

                        })
                        .then(function () {
                            // 总是会执行

                        });

                }
                else {
                    return false;
                }
            })
        },
    }
}
