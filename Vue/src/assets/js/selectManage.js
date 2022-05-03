import Nav from "@/views/inc/Nav.vue";
import request from "@/utils/request";
import ALERTMSG from "@/assets/js/alert";

export default {
    name: "selectManage",
    components:{
        Nav
    },
    data(){
        let yearCheck=(rule, value, callback)=>{
            if(1904<value&&value<2023){
                callback();
                return true;
            }
            else {
                callback(new Error('请输入正确年份！'));
                return false;
            }
        };
       return{
           dialogVisible0:false,
           isDisabled1:true,
           isDisabled2:false,
           currentTime:[{
               academicYear:'',
               term:'',
           },
           ],
           formInLine:{
               academicYear:'',
               term:'',
           },
           editFormInLine:({
               academicYear: [
                   {required: true, message: '请输入学年', trigger: 'blur',},
                   {validator: yearCheck, trigger: 'blur',required: true },
               ],
               term:[
                   {required: true, message: '请选择学期', trigger: 'blur',},
               ],
           }),
           activities:[
               {
                   content: '学期开始',
                   size: 'normal ',
                   type: 'primary',
                   color: '#3581E1',
               },
               {
                   content: '一轮选课开始',
                   size: 'normal ',
                   type: 'primary',
                   color: '#3581E1',
               },
               {
                   content: '一轮选课结束',
                   size: 'normal ',
                   type: 'primary',
                   color: '#3581E1',
               },
               {
                   content: '二轮选课开始',
                   size: 'large',
                   type: 'primary',
                   color: '#0bbd87',
               },
               {
                   content: '二轮选课结束',
                   size: 'normal ',
                   type: 'primary',
                   color: '#D3D3D3',
               },
               {
                   content: '学期结束',
                   size: 'normal ',
                   type: 'primary',
                   color: '#D3D3D3',
               },
           ]
       }

    },
    created() {
        this.getYearAndTerm();
        this.getStatus();
    },
    methods:{
        showdialog(){
            const that=this;
            that.dialogVisible0=true;
            that.formInLine.academicYear=that.currentTime[0].academicYear;
            that.formInLine.term=that.currentTime[0].term;
        },
        getYearAndTerm(){
            const that = this;
            request.get("/admin/academicYearAndTerm").then(function(res){
                let temp=String(res);
                that.currentTime[0].academicYear=temp[0]+temp[1]+temp[2]+temp[3];
                that.currentTime[0].term=temp[4];
            },function (err){
                ALERTMSG.show(that,true,"获取学年学期失败!","error");
            })
        },
        nextStatue(){
            const that = this;
            request.post("/admin/courseSelect/next").then(function(res){
                ALERTMSG.show(that,true,"阶段更改成功!","success");
            },function (err){
                ALERTMSG.show(that,true,"阶段更改失败!","error");
            })
        },
        setYearAndTerm(){
            const that = this;
            this.$refs.editRuleForm.validate((valid) =>{
                if(valid){
                    let formData = new FormData();
                    for(let key in that.formInLine) {
                        formData.append(key,that.formInLine[key]);
                    }
                    request.put("/admin/academicYearAndTerm/set",formData).then(function(res){
                        if(res =='SUCCESS'){
                            that.getYearAndTerm();
                            ALERTMSG.show(that,true,"学年学期设置成功!","success");
                        }
                    },function (err){
                        ALERTMSG.show(that,true,"学年学期设置失败!","error");
                    })
                }
                else{
                    return false;
                }
            });

        },
        getStatus(){
            const that=this;
            request.get("/admin/courseSelect/status").then(res=>{
                console.log(res)
            },err=>{
                ALERTMSG.show(that,true,"选课状态获取失败!","error");
            })
        },
        openSelection(){
            const that=this;
            let CHANGE=true;
            let openform = new FormData();
            openform.append('change', CHANGE);
            request.post("/admin/courseSelect/change",openform)
                .then(res=> {
                if(res==='SUCCESS') {
                    ALERTMSG.show(that,true,"操作成功!","success");
                    that.getStatus();
                }
                else{
                    ALERTMSG.show(that,true,"选课失败!请再次尝试","error");
                    that.getStatus();
                }
            })
        },
        closeSelection(){
            const that=this;
            let CHANGE=false;
            let closeform = new FormData();
            closeform.append('change', CHANGE);
            request.post("/admin/courseSelect/change",closeform)
                .then(res=> {
                if(res==='SUCCESS') {
                    ALERTMSG.show(that,true,"操作成功!","success");
                    that.getStatus();
                    }
                else{
                    ALERTMSG.show(that,true,"选课失败!请再次尝试","error");
                    that.getStatus();
                }
            })
        }
    }
}
