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
            studentData:[],
            majorListData:[],
        }
    },
    mounted() {
        this.getMajorList();
        this.getcourse();
    },
    methods:{
        getMajorList(){
            request.get("/major/list").then(res=>{
                this.majorListData=res;
            })
        },
        getcourse(){
            request.get("/course/list")
                .then(res=>{
                    this.courseData=res;
                    this.filltabledata();
                })
        },
        getstudent(id,i){
            request.get("/course/students",{
                params:{
                    courseId:id
                }
            }).then(res=>{
                this.studentData[id]= res;
                console.log(res);
                let coursetypestr='';
                if(Object.keys(this.courseData[i].openToMajors).length===Object.keys(this.majorListData).length)
                    coursetypestr="通选";
                else if(Object.keys(this.courseData[i].openToMajors).length===1)
                    coursetypestr="专业";
                else
                    coursetypestr="面向部分专业";
                this.tableData[i]= {
                    'courseCategoryNumbershow':this.courseData[i].courseCategory.courseCategoryNumber+'.'+this.courseData[i].courseNumber,
                    'courseName': this.courseData[i].courseCategory.courseName,
                    'courseschoolName': this.courseData[i].courseCategory.school.schoolName,
                    'coursemajorName': this.courseData[i].courseCategory.major.majorName,
                    'coursetype':coursetypestr,
                    children: this.studentData[this.courseData[i].courseId],
                }
            })
        },
        filltabledata(){
            for(let i=0;i<Object.keys(this.courseData).length;i++)
            {
                this.getstudent(this.courseData[i].courseId,i);
            }
        }
    }
}
