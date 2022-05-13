import Nav from "@/views/inc/Nav.vue";
import request from "@/utils/request";
import ALERTMSG from "@/assets/js/alert";

export default {
    name: "userManage",
    components:{
        Nav
    },
    data() {
        return{
            tableData:[],
            courseData:[],
            studentData:[]
        }
    },
    mounted() {
        this.getcourse();

    },
    methods:{
        getcourse(){
            request.get("/course/list")
                .then(res=>{
                    this.courseData=res;
                    console.log(res);
                    this.filltabledata();
                })
        },
        getstudent(id){
            request.get("/course/students",{
                params:{
                    courseId:id
                }
            }).then(res=>{
                this.studentData[id]= res;
            })
        },
        filltabledata(){
            for(let i=0;i<Object.keys(this.courseData).length;i++)
            {
                this.getstudent(this.courseData[i].courseId);
                this.tableData[i]= {
                    'courseCategoryNumbershow':this.courseData[i].courseCategory.courseCategoryNumber+'.'+this.courseData[i].courseNumber,
                    'courseName': this.courseData[i].courseCategory.courseName,
                    'courseschoolName': this.courseData[i].courseCategory.school.schoolName,
                    'coursemajorName': this.courseData[i].courseCategory.major.majorName,
                    children: this.studentData[this.courseData[i].courseId],
                }
            }
        }
    }
}
