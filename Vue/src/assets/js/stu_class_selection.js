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
                    classid: '1',
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
                {
                    classid: '2',
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
            const that=this;
                request.get("/course/selectable")
                    .then(res=>{
                            if(res.HttpStatus=="NO_CONTENT"){
                                alert("当前无可选课程！");
                                this.tableData.classid="NULL";
                                this.tableData.classname="NULL";
                                this.tableData.faculty="NULL";
                                this.tableData.classhours="NULL";
                                this.tableData.credit="NULL";
                                this.tableData.teacher="NULL";
                                this.tableData.introduction="NULL";
                                this.tableData.time="NULL";
                                this.tableData.classroom="NULL";
                                this.tableData.capacity="NULL";
                            }
                            else if(res.HttpStatus=="NOT_ACCEPTABLE"){
                                alert("当前选课未开放！");
                                return that.$router.push({path: '/index_stu'});
                            }
                            else if(res.HttpStatus=="OK"){
                                // this.tableData.classid=res.courseId;
                                // this.tableData.classid=res.courseId;
                                // this.tableData.classid=res.courseId;
                                // this.tableData.classid=res.courseId;
                                // this.tableData.classid=res.courseId;
                                // this.tableData.classid=res.courseId;
                                // this.tableData.classid=res.courseId;
                                // this.tableData.classid=res.courseId;
                                // this.tableData.classid=res.courseId;
                                // this.tableData.classid=res.courseId;
                            }
                    },function (err) {
                    alert("课程信息获取失败！");
                    return false;
                })
        },
        stu_select(classid){
            alert(classid);
        }
    }
}
