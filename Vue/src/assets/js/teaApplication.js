import Nav from "@/views/inc/Nav";
import request from "@/utils/request";
import alert from "@/assets/js/alert";
import ALERTMSG from "@/assets/js/alert";

export default {
    name: "teaApplication",
    components:{
        Nav
    },
    data(){
        return{
            teaApplicationData:[],
        }
    },
    created() {
        this.getTeaApplication();
    },
    methods:{
        teaCancleApplication(row){
            const that = this;
            request.delete("/teacher/application/cancel/"+row.applicationId)
                .then(function (res){
                    ALERTMSG.show(that,true,"课程申请删除成功!","success");
                    that.getTeaApplication();
                },function(err){
                    ALERTMSG.show(that,true,"申请删除失败!请再次尝试！","error");
                });
        },
        getTeaApplication(){
            const that = this;
            request.get("/teacher/application")
                .then(function (res){
                    that.teaApplicationData=res;
                })
        }
    }
}