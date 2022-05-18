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
           currentStatue:'',
           dialogVisible0:false,
           isDisabled1:true,
           isDisabled2:false,
           currentTime:[{
               academicYear:'',
               term:'',
           },
           ],
           formInLine:{
               academicYear2:'',
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
        showCurrentStatues(statueNow){
            const that=this;
            let flag;
            if(statueNow=='START_TERM') flag=0;
            if(statueNow=='START_FIRST') flag=1;
            if(statueNow=='END_FIRST') flag=2;
            if(statueNow=='START_SECOND') flag=3;
            if(statueNow=='END_SECOND') flag=4;
            if(statueNow=='END_TERM') flag=5;
            for(let i=0;i<that.activities.length;i++){
                if(i<flag){
                    that.activities[i].color='#3581E1'
                    that.activities[i].size='normal';
                }
                else if(i==flag){
                    that.activities[i].color='#0bbd87'
                    that.activities[i].size='large';
                }
                else{
                    that.activities[i].color='#D3D3D3'
                    that.activities[i].size='normal';
                }
            }
            that.currentStatue=flag;
        },
        showdialog(){
            const that=this;
            that.dialogVisible0=true;
            let temp=that.currentTime[0].academicYear;
            that.formInLine.academicYear=temp[0]+temp[1]+temp[2]+temp[3];
            that.formInLine.academicYear2=Number(that.formInLine.academicYear)+1;
            that.formInLine.term=that.currentTime[0].term;
        },
        getYearAndTerm(){
            const that = this;
            request.get("/admin/academicYearAndTerm").then(function(res){
                let temp=String(res);
                that.currentTime[0].academicYear=temp[0]+temp[1]+temp[2]+temp[3]+temp[4]+temp[5]+temp[6]+temp[7]+temp[8];
                that.currentTime[0].term=temp[9];
            },function (err){
                ALERTMSG.show(that,true,"获取学年学期失败!","error");
            })
        },
        nextStatue(){
            const that = this;
            request.post("/admin/courseSelect/next").then(function(res){
                if(res==="SUCCESS"){
                    ALERTMSG.show(that,true,"阶段更改成功!","success");
                    if(that.currentStatue=='5'){
                        let tem=that.currentTime[0].academicYear;
                        that.formInLine.academicYear=tem[0]+tem[1]+tem[2]+tem[3];
                        that.formInLine.academicYear2=Number(that.formInLine.academicYear)+1;
                        that.formInLine.term=that.currentTime[0].term;
                        if(that.formInLine.term=='2'){
                            that.formInLine.term='1';
                            that.formInLine.academicYear=that.formInLine.academicYear2;
                            that.formInLine.academicYear2=Number(that.formInLine.academicYear)+1;
                        }
                        else{
                            that.formInLine.term='2';
                        }
                        let formData = new FormData();
                        that.formInLine.academicYear=that.formInLine.academicYear+'-'+that.formInLine.academicYear2;
                        formData.append('academicYear',that.formInLine.academicYear);
                        formData.append('term',that.formInLine.term);
                        request.put("/admin/academicYearAndTerm/set",formData).then(function(res){
                            if(res =='SUCCESS'){
                                that.getYearAndTerm();
                                ALERTMSG.show(that,true,"学年学期设置成功!","success");
                            }
                        },function (err){
                            ALERTMSG.show(that,true,"学年学期设置失败!","error");
                        })
                    }
                    that.getStatus();
                }
                else if(res==="FAILED"){
                    ALERTMSG.show(that,true,"阶段更改失败!","error");
                }

            },function (err){
                ALERTMSG.show(that,true,"阶段更改失败!","error");
            })
        },
        setYearAndTerm(){
            const that = this;
            this.$refs.editRuleForm.validate((valid) =>{
                if(valid){
                    let formData = new FormData();
                    that.formInLine.academicYear=that.formInLine.academicYear+'-'+that.formInLine.academicYear2;
                    formData.append('academicYear',that.formInLine.academicYear);
                    formData.append('term',that.formInLine.term);
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
                that.showCurrentStatues(String(res));
            },err=>{
                ALERTMSG.show(that,true,"选课状态获取失败!","error");
            })
        },
    }
}
