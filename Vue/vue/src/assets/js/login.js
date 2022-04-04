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
    methods:{
        loginfunc() {
            this.$refs.loginruleForm.validate((valid) => {
                if (valid) {
                    request.post("/login",this.loginruleForm).then(res=>{
                        console.log(res);
                    })
                }
                else {
                    return false;
                }
            })
        },
    }
}
