import Nav from "@/views/inc/Nav.vue";
import request from "@/utils/request";

export default {
    name: "classselection",
    components:{
        Nav
    },
    data(){
        return{
            selectableData : []
        }
    },
    created(){
        this.getclassinfo();
    },
    methods:{
        getclassinfo(){
            const that=this;
                request.get("/course/selectable")
                    .then(function(res){
                            that.selectableData=res;
                            console.log(res);
                    },function (err) {
                    alert("课程信息获取失败！");
                    return false;
                })
        },
        stu_select(courseId){
            console.log(courseId);
            let xuankeform = new FormData();
            xuankeform.append('courseId', courseId);
            request.post("/course/select",xuankeform)
                .then(function (response) {
                    console.log(response);
                    if (response === "FAILED") {
                        alert("选课失败！请重新尝试");
                        return false;
                    } else if (response === "NOT_OPEN") {
                        alert("当前选课未开放！");
                        return false;
                    } else if (response === "SUCCESS") {
                        alert("选课成功！");
                    }
                },function (err) {
                        alert("选课失败！请重新尝试！");
                        return false;
                    }
        )
    }
    }
}
