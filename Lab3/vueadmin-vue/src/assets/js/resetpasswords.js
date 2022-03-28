export default {
    name: "resetpasswords",
    data(){
        let testpassword = (rule, value, callback) =>{
            const pat0=/^[\w-_]{6,32}$/;//密码的规则0
            const pat1=/^[0-9]{6,32}$/;//密码的规则1
            const pat2=/^[-_]{6,32}$/;//密码的规则2
            const pat3=/^[a-zA-Z]{6,32}$/;//密码的规则3
            if(pat1.test(value)||pat2.test(value)||pat3.test(value)){
               callback(new Error('*密码需长度为6-32位，且字母，数字或者特殊字符（-_）至少包含两种'));
               return false;
            }
            else {
                if(pat0.test(value)){
                    callback();
                    return true;
                }
                else {
                    callback(new Error('*密码需长度为6-32位，且字母，数字或者特殊字符（-_）至少包含两种'));
                    return false;
                }
            }
        };
        let WhetherSame = (rule, value, callback) =>{
            if(value!==this.ruleForm.pw1){
                callback(new Error('*请两次输入的密码保持一致'));
                return false;
            }
            else{
                callback();
                return true;
            }
        };
        return{
            ruleForm:{
                pw1:'',
                pw2:''
            },
            editRules :({
                pw1: [
                    {required: true, message: '请输入新密码', trigger: 'blur' },
                    {validator: testpassword,trigger: 'blur'}
                ],
                pw2:[
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
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    alert('submit!');
                }
                else {
                    return false;
                }
            })
        }
    }
}
