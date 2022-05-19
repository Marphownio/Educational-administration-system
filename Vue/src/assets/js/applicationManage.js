import Nav from "@/views/inc/Nav.vue";
import request from "@/utils/request";

export default {
    name: "userManage",
    components:{
        Nav
    },
    data(){
        return{
            schooldata:[],
            teacherdata:[],
            majordata:[],
            ruleForm1:{
                introduction:''
            },
            ruleForm2:{

            },
            editRules1 :({
            }),
            tableData:[

            ],
            applicationData:[
                {},
            ]
        }
    },
    mounted() {
        this.getapplication();
    },
    created(){
    },
    methods:{
        getapplication(){
            request.get("/student/application/list").then(res=>{
                let flag = 0;
                for(let i=0;i<Object.keys(res).length;i++)
                {

                    if(res[i].applicationStatus==="IN_REVIEW")
                    {
                        flag=1;
                        this.applicationData[i]=res[i];
                        this.applicationData[i].courseNumbershow=this.applicationData[i].course.courseCategory.courseCategoryNumber+'.'
                        +this.applicationData[i].course.courseNumber;
                    }
                }
                if(flag===0){
                    this.applicationData="";
                }
            })
        },
        passApplication(id){
            request.delete("/admin/application/student",{
                params:{
                    applicationId:id,
                    operation:true
                }
            }).then(res=> {
                if(res==="SUCCESS")
                    this.$message({
                        showClose: true,
                        message: '操作成功',
                        type: 'success',
                    });
                else if(res==="OUTOFRANGE")
                    this.$message({
                        showClose: true,
                        message: '选课容量超出教室容量，无法通过该请求',
                        type: 'error',
                    });
            });
        },
        rejectApplication(id){
            request.delete("/admin/application/student",{
                params:{
                    applicationId:id,
                    operation:false
                }
            })
            this.$message({
                showClose: true,
                message: '操作成功',
                type: 'success',
            });
            this.$router.go(0);
        },
    }
}
