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
                this.applicationData=res;
                for(let i=0;i<Object.keys(this.applicationData).length;i++)
                {
                    this.applicationData[i].courseNumbershow=this.applicationData[i].course.courseCategory.courseCategoryNumber+'.'
                    +this.applicationData[i].course.courseNumber;
                }
            })
        },
        passApplication(id){
            request.delete("/admin/application/student",{
                params:{
                    applicationId:id,
                    operation:true
                }
            })
            this.$message({
                showClose: true,
                message: '操作成功',
                type: 'success',
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
