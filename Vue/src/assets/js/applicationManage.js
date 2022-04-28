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
    },
    created(){

    }
}
