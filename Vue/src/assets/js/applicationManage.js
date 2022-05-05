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
            })
            console.log(this.applicationData);
        },
    }
}
