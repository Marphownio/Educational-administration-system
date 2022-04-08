import request from "@/utils/request";
import Nav from "@/views/inc/Nav";

export default {
    name: "resetpasswords",
    components:{
        Nav
    },
    data(){
        let testpassword = (rule, value, callback) =>{
            const pat0=/^[\w-_]{6,32}$/;//密码的规则0
            const pat1=/^[0-9]{6,32}$/;//密码的规则1
            const pat2=/^[-_]{6,32}$/;//密码的规则2
            const pat3=/^[a-zA-Z]{6,32}$/;//密码的规则3
            if(pat1.test(value)||pat2.test(value)||pat3.test(value)){
               callback(new Error('*密码长度为6-32位，且字母，数字或者特殊字符（-_）至少包含两种'));
               return false;
            }
            else if(value==='fudan123456'){
                callback(new Error('*为安全性考虑，请勿将新密码设置为系统初始密码'));
                return false;
            }
            else {
                if(pat0.test(value)){
                    callback();
                    return true;
                }
                else {
                    callback(new Error('*密码长度为6-32位，且字母，数字或者特殊字符（-_）至少包含两种'));
                    return false;
                }
            }
        };
        let WhetherSame = (rule, value, callback) =>{
            if(value!==this.rpwruleForm.newpw1){
                callback(new Error('*请两次输入的密码保持一致'));
                return false;
            }
            else{
                callback();
                return true;
            }
        };
        return{
            rpwruleForm:{
                newpw1:'',
                newpw2:''
            },
            rpweditRules :({
                newpw1: [
                    {required: true, message: '请输入新密码', trigger: 'blur' },
                    {validator: testpassword,trigger: 'blur'}
                ],
                newpw2:[
                    {required: true, message: '请再次确认密码', trigger: 'blur',},
                    {validator: WhetherSame,trigger: 'blur', required: true}
                ],
            }),
        }
    },
    created(){
    },
    methods:{
        submit_check() {
            this.$refs.rpwruleForm.validate((valid) => {
                if (valid) {
                    let formData = new FormData();
                    for(let key in this.loginruleForm) {
                        formData.append(key,this.loginruleForm[key]);
                    }
                    request.post("/resetPassword",formData)
                        .then(function (response) {
                            if(response==="FAILED"){
                                //密码重置失败
                                alert("密码重置失败！请重新尝试");
                                return that.$router.push({path: '/resetpassword'});
                            }
                            else if(response==="SUCCESS"){
                                //密码重置成功
                                alert("密码重置成功！");
                                return that.$router.push({path: '/index_admin'});
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
