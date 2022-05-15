import Nav from "@/views/inc/Nav";
import request from "@/utils/request";
import alert from "@/assets/js/alert";
import ALERTMSG from "@/assets/js/alert";

export default {
    name: "stuApplication",
    components:{
        Nav
    },
    data(){
        return{
            stuApplicationData:[],
        }
    },
    created() {
        this.getStuApplication();
    },
    methods:{
        deleteMyApplication(row){
            const that = this;
            if(row.applicationStatus=='PASS'||row.applicationStatus=='NOT_PASS'){
                ALERTMSG.show(that,true,"申请已被审核、无法删除！","warning");
                return false;
            }
            let form = new FormData();
            form.append('applicationId',row.applicationId);
            request.post("/student/application/cancel",form)
                .then(function (res){
                    ALERTMSG.show(that,true,"选课申请删除成功!","success");
                    that.getStuApplication();
                },function(err){
                    ALERTMSG.show(that,true,"申请删除失败!请再次尝试！","error");
            });
        },
        getStuApplication(){
            const that = this;
            request.get("/student/application/personallist")
                .then(function (res){
                    console.log(res);
                    that.stuApplicationData=res;
                })
        }
    }
}