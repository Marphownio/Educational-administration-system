import Nav from "@/views/inc/Nav.vue";
import request from "@/utils/request";

export default {
    name: "classselection",
    components:{
        Nav
    },
    data(){
        return{
            tableData : [
                {
                    classid: '',
                    classname: '',
                    faculty:'',
                    classhours:'',
                    credit:'',
                    teacher:'',
                    introduction: '',
                    time:'',
                    classroom: '',
                    capacity:'',
                },
            ]
        }
    },
    created(){
        this.getclassinfo();
    },
    methods:{
        getclassinfo(){
                // request.get("/user/info").then(res=>{
                //     this.PersonalData=res;
                // })
        },
    }
}
